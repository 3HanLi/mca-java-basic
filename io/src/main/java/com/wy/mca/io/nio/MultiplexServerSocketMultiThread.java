package com.wy.mca.io.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @Description 多路复用器多线程版本
 * @Author wangyong01
 * @Date 2022/4/7 6:08 下午
 * @Version 1.0
 */
public class MultiplexServerSocketMultiThread {

    private static int waitTimeOut = 100;

    private static Selector selector;

    public static void main(String[] args) throws Exception{

        initServer();

        processClientFds();

    }

    /**
     * 处理client请求
     *
     * 备注：这里都以epoll 这种多路复用器来分析
     *
     * @throws Exception
     */
    private static void processClientFds() throws Exception{
        System.out.println("Prepare process client requests");

        while (true){
            // 查询关注的事件
            // 以关注 read 事件为例，假如client 发送数据，就会被listen状态的 fd4 监听到，然后在内核的回调函数中将其从红黑树转移到链表
            // 那么，只要链表中该fd有数据，那么调用select()方法时（对应到内核就是：epoll_wait），就能够获取到其状态

            while (selector.select(waitTimeOut) > 0){

                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                System.out.println("SelectKeys size:" + selectionKeys.size());

                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()){
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();

                    //1 弊端
                    //1.1 read 和 write依然是串行处理，假如：read/write 很慢，就会影响 其他client的 accept，read，write
                    if (selectionKey.isAcceptable()){
                        acceptHandler(selectionKey);
                    }
                    //1.2 解决方案：抛出线程处理read 和 write

                    //2 弊端
                    //2.1   带来了新的问题：read 和 write会被疯狂调起，这里以read疯狂调起做原因分析：
                    //      抛出线程是异步处理，会立即返回执行while循环，此时还没来得及read完缓冲区buffer中的数据，那么selector.select()依然会获取到该fd
                    //      导致isReadable()为true，重复执行
                    //2.2   解决方案：每次read / write 处理之前将selectKey.cancel()从 红黑树（epoll） 中移除，等read 或者 write完毕后，再次注册
                    else if (selectionKey.isReadable()){

                        //3 弊端
                        //3.1 selectionKey.cancel()底层调用的是epoll_ctl(fd3, epoll_ctl_del, fd7)，也就是进行了一次系统调用

                        //4 总结，
                        //4.1 使用多路复用器解决了 nio重复遍历全量fds的问题，极大的提高了性能
                        //4.2 我们在使用多路复用器的时候才用了单线程处理的方式，对于read 和 write依然是串行处理，因而我们采用了抛出线程的方式，避免read / write阻塞 select()的调用
                        //4.3 但是这种方式会导致 read没有完毕的时候，就继续执行select()方法，导致read 和 write频繁调用
                        //4.4 因而我们在每次抛线程之前，将有状态的fd从红黑树中移除，但是这又导致了频繁的系统调用

                        //5 最终解决方案 --> 创建出多个selector，将不同的fd打散在不同的selector上，这样既支持了多线程，又能避免频繁的系统调用
                        //延伸出netty模型
                        //5.1 我们使用1个或者多个线程用于listen
                        //5.2 将监听到的有事件的fd 抛到 另一个多路复用器处理
                        selectionKey.cancel();
                        readHandler(selectionKey);
                    }else if (selectionKey.isWritable()){
                        selectionKey.cancel();
                        writeHandler(selectionKey);
                    }
                }
            }
        }
    }

    /**
     * 处理来自client的连接
     *
     * @param selectionKey
     * @throws Exception
     */
    private static void acceptHandler(SelectionKey selectionKey) throws Exception{
        //1.1 获取到client的连接请求
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel)selectionKey.channel();

        //1.2 获取到client 的 fd7
        SocketChannel client = serverSocketChannel.accept();
        client.configureBlocking(false);
        System.out.println("------------------------------------");
        System.out.println("Receive client connect :" + client.getRemoteAddress());
        System.out.println("------------------------------------");

        //1.3 将fd7 加入到红黑树中并关注Read事件（内核层面是epoll_in），当client发送数据时，就可以将fd迁移到链表中
        client.register(selector, SelectionKey.OP_READ);
    }

    /**
     * 处理client的Read操作
     *
     * @param selectionKey
     * @throws Exception
     */
    private static void readHandler(SelectionKey selectionKey){
        System.out.println("--------readHandler start-----------");
        new Thread(()->{
            SocketChannel client = (SocketChannel)selectionKey.channel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
            while (true){
                try {
                    //1.1 读取fd数据到bytebuffer
                    int readNum = client.read(byteBuffer);
                    if (readNum > 0){
                        byteBuffer.flip();
                        byte[] bytes = new byte[byteBuffer.limit()];
                        byteBuffer.get(bytes);
                        System.out.println("Read data :" + new String(bytes));

                        //1.2 并将读取到的数据写回client
                        client.register(selector, SelectionKey.OP_WRITE, byteBuffer);
                        byteBuffer.clear();
                    }else if (readNum == 0){
                        System.out.println("Read end..");
                        break;
                    }else {
                        System.out.println("Client closed");
                        client.close();
                        break;
                    }
                } catch (Exception ex){

                }
            }
        }).start();
        System.out.println("--------readHandler End-----------");
    }

    /**
     * 处理write操作
     *
     * @param selectionKey
     * @throws Exception
     */
    private static void writeHandler(SelectionKey selectionKey) {
        System.out.println("----------writeHandler start---------");
        new Thread(()->{
            try {
                SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
                ByteBuffer byteBuffer = (ByteBuffer)selectionKey.attachment();

                //1 将读取到的数据写回client
                System.out.println("Write data ..");
                while (byteBuffer.hasRemaining()){
                    socketChannel.write(byteBuffer);
                }

                //2 重新注册Read事件
                socketChannel.register(selector, SelectionKey.OP_READ);
            } catch (Exception ex){

            }
        }).start();

        System.out.println("----------writeHandler end---------");
    }

    /**
     * 初始化服务器
     *
     * @throws Exception
     */
    private static void initServer() throws Exception{
        //1 创建server ==> 获取到listen状态的fd4
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(9090));
        serverSocketChannel.configureBlocking(false);

        //2 创建selector ==> 生成fd3指向内核空间
        //2.1 在select和poll模型下，相当于在jvm空间维护了一个数组，维护全量的fds
        //2.2 在epoll模型下，相当于在内存空间创建了红黑树，维护全量的fds
        selector = Selector.open();

        //3 将listen状态的fd4 加入到 fd3，当有来自client的连接时，会首先被fd4监听到，并迁移到fd3表示的空间中
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("Server started on 9090");
    }

}
