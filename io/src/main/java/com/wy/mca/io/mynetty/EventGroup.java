package com.wy.mca.io.mynetty;

import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Description 模仿netty的NioEventGroup
 * @Author wangyong01
 * @Date 2022/4/8 7:25 下午
 * @Version 1.0
 */
public class EventGroup implements Runnable{

    /**
     * selector
     */
    public Selector selector;

    /**
     * 任务队列
     */
    public LinkedBlockingQueue<Channel> linkedBlockingQueue = new LinkedBlockingQueue<>();

    /**
     * selector所属的组：boss ? group
     */
    private EventGroupManager eventGroupManager;

    public EventGroup(EventGroupManager eventGroupManager) {
        try {
            this.eventGroupManager = eventGroupManager;
            selector = Selector.open();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    //Nio EventLoop
    @Override
    public void run() {
        while (true){
            try {
                System.out.println("Current Thread:" + Thread.currentThread().getName());

                //关于select()的思考
                //1.1 epoll模型下，底层调用的是epoll_wait，查询链表中是否有事件（accept read write），没有的话就会阻塞
                //1.2 select()阻塞：
                //epoll使用步骤：epoll_create，epoll_ctl，epoll_wait，分别对应：Selector.open ; server.register(selector, op) ; selector.select()
                // 如果直接在epoll_create后执行epoll_wait就会进行阻塞，那么我们就需要先wake_up，然后执行epoll_ctl
                if (selector.select() > 0){
                    //1 selectKeys
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();

                    //2 processKeys
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()){
                        SelectionKey selectionKey = iterator.next();
                        iterator.remove();

                        if (selectionKey.isAcceptable()){
                            acceptHandler(selectionKey);
                        } else if (selectionKey.isReadable()){
                            readHandler(selectionKey);
                        } else if (selectionKey.isWritable()){

                        }
                    }
                }
                //3 run other tasks
                if (!linkedBlockingQueue.isEmpty()){
                    Channel channel = linkedBlockingQueue.take();
                    if (channel instanceof ServerSocketChannel){
                        ServerSocketChannel server = (ServerSocketChannel) channel;
                        server.register(selector, SelectionKey.OP_ACCEPT);
                    }else {
                        SocketChannel socketChannel = (SocketChannel) channel;
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    }
                }
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    private void acceptHandler(SelectionKey selectionKey) throws Exception{
        System.out.println("acceptHandler current Thread: " + Thread.currentThread().getName());

        ServerSocketChannel serverSocketChannel = (ServerSocketChannel)selectionKey.channel();
        SocketChannel client = serverSocketChannel.accept();
        client.configureBlocking(false);

        System.out.println("------------------------------------");
        System.out.println("Receive client connect :" + client.getRemoteAddress());
        System.out.println("------------------------------------");

        this.eventGroupManager.registerBossSelector(client);
    }

    private void readHandler(SelectionKey selectionKey) throws Exception{
        System.out.println("--------readHandler start-----------" + Thread.currentThread().getName());
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

                byteBuffer.flip();
                client.write(byteBuffer);

                //1.2 并将读取到的数据写回client
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


}
