package com.wy.mca.io.nio;

import com.google.common.collect.Lists;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.List;

public class NIOServerSocket {

    public static void main(String[] args) throws Exception{
        //1.1 创建ServerSocketChannel，获取fd
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //1.2 绑定端口号
        serverSocketChannel.bind(new InetSocketAddress(9090));
        //设置非阻塞
        serverSocketChannel.configureBlocking(false);

        List<SocketChannel> clientSocketList = Lists.newArrayList();
        while (true){
            //1.3 监听
            SocketChannel socketChannel = serverSocketChannel.accept();

            //2.1 获取到client，设置非阻塞
            if (null != socketChannel){
                socketChannel.configureBlocking(false);
                //2.2 将连接的client加入fds集合
                clientSocketList.add(socketChannel);
            }

            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(8096);
            //2.3 遍历每一个fd进行读取，查看是否有数据，存在如下弊端：a) 我们需要遍历全量的fds b) 每次都要重复传入同样的fds遍历
            for (SocketChannel client : clientSocketList){
                int readNum = socketChannel.read(byteBuffer);
                //2.4 如果能读取到数据，进行处理
                if (readNum > 0){
                    //3.1 切换读取模式
                    byteBuffer.flip();

                    //3.2 查看可读取的字节数，并进行读取
                    int readLimit = byteBuffer.limit();
                    byte[] bytes = new byte[readLimit];
                    byteBuffer.get(bytes);

                    //3.3 输出读取内容
                    String readInfo = new String(bytes);
                    System.out.println("Read Info From :" + client.getRemoteAddress() + "; readInfo : " + readInfo);
                    byteBuffer.clear();
                }
            }
        }

    }
}
