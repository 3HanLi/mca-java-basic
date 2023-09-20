package com.wy.mca.io.reference.bio;

import com.google.common.collect.Lists;

import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.List;

/**
 * 测试同一个client向server发送超过10W个连接
 *
 * @author wangyong01
 */
public class C10KClient {

    public static void main(String[] args) {

        List<SocketChannel> connectList = Lists.newArrayList();
        //1 server端IP
        InetSocketAddress serverSocketAddress = new InetSocketAddress("172.16.163.128", 9090);

        for (int i = 10000; i < 65000; i++) {
            try {
                //1.1
                SocketChannel client02 = SocketChannel.open();
                client02.bind(new InetSocketAddress("172.16.163.1", i));
                client02.connect(serverSocketAddress);
                connectList.add(client02);

                //1.2 本机IP
                SocketChannel client01 = SocketChannel.open();
                client01.bind(new InetSocketAddress("172.16.26.210", i));
                client01.connect(serverSocketAddress);
                connectList.add(client01);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        System.out.println("Connect Size:" + connectList.size());

        try{
            System.in.read();
        } catch (Exception ex){

        }
        System.out.println("Program end");
    }

}
