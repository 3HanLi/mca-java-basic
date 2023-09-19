package com.wy.mca.io.nio.channel;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author wangyong01
 * @version 2023/9/15 6:14 PM
 * @description
 */
public class SocketChannelClient01 {

    public static void main(String[] args) throws Exception{
        //1.1 创建SocketChannel
        SocketChannel socketChannel = SocketChannel.open();
        //1.2 连接ServerSocketChannel
        boolean connect = socketChannel.connect(new InetSocketAddress("localhost", 9090));
        if (connect){
            System.out.println("Connect Success");
            //1.3 分配缓冲区
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            byteBuffer.put("HelloWorld From NioSocketChannel".getBytes());
            //1.4 写数据
            byteBuffer.flip();
            socketChannel.write(byteBuffer);
        }
    }
}
