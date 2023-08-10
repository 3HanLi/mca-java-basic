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
 * @Description 多路复用器单线程版本
 * @Author wangyong01
 * @Date 2022/4/7 6:08 下午
 * @Version 1.0
 */
public class MultiplexServerSocketSingleThread {

    private static int waitTimeOut = 100;

    private static Selector selector;

    public static void main(String[] args) throws Exception{

        initServer();

        processClientFds();

    }

    /**
     * 处理client请求
     *
     * @throws Exception
     */
    private static void processClientFds() throws Exception{
        System.out.println("Prepare process client requests");

        while (true){
            //1.1 询问内核是否有事件，注意：这里才会调用epoll_ctl(fd3, add, fd4)，将fd4加入 jvm数组空间（select和poll）/红黑树上（epoll）
            //有事件才会返回，否则会一直阻塞，这里的事件有：accept read write
            while (selector.select() > 0){

                //1.2 获取到有事件的fds集合
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                System.out.println("SelectKeys size:" + selectionKeys.size());

                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()){
                    SelectionKey selectionKey = iterator.next();
                    //1.3 处理fd后，需要从内核的链表中移除，避免重复处理
                    iterator.remove();

                    //2 弊端
                    //2.1 read 和 write依然是串行处理，假如：read/write 很慢，就会影响 其他client的 accept，read，write
                    //2.2 解决方案：抛出线程处理read 和 write，参考 MultiplexServerSocketMultiThread
                    if (selectionKey.isAcceptable()){
                        acceptHandler(selectionKey);
                    }else if (selectionKey.isReadable()){
                        readHandler(selectionKey);
                    }else if (selectionKey.isWritable()){
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
    private static void readHandler(SelectionKey selectionKey) throws Exception{
        System.out.println("--------readHandler start-----------");
        SocketChannel client = (SocketChannel)selectionKey.channel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
        while (true){
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
        }
        System.out.println("--------readHandler End-----------");
    }

    /**
     * 处理write操作
     *
     * @param selectionKey
     * @throws Exception
     */
    private static void writeHandler(SelectionKey selectionKey) throws Exception{
        System.out.println("----------writeHandler start---------");
        SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
        ByteBuffer byteBuffer = (ByteBuffer)selectionKey.attachment();

        //1 将读取到的数据写回client
        while (byteBuffer.hasRemaining()){
            socketChannel.write(byteBuffer);
        }

        //2 重新注册Read事件
        socketChannel.register(selector, SelectionKey.OP_READ);
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
        //注意：这里将fd4 加入到fd3 是懒加载，也就是只有调用selector.select()时才会加入进去，我们可以通过观察线程对内核的系统调用来查看这一现象
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("Server started on 9090");
    }

}
