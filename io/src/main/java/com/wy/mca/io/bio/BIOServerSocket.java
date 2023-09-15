package com.wy.mca.io.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * BIO模式下的ServerSocket
 */
public class BIOServerSocket {

    public static void main(String[] args) throws IOException {
        //1 Server启动
        //1.1 启动server socket，得到fd3
        ServerSocket serverSocket = new ServerSocket();
        //1.2 将fd3绑定到端口号9090，用于监听外部请求
        serverSocket.bind(new InetSocketAddress("localhost", 9090));
        System.out.println("Server bind port : 9090");

        //2.1 client连接并发送数据，此时数据会积压在Recv-Q队列中
        System.in.read();

        while (true){
            //2.2 调用accept方法后，会分配fd5，代表来自client的连接；
            //3.1 在BIO模式下，如果没有client连接，那么server会阻塞在这里，因而我们需要抛出线程，避免后续的client连接阻塞
            Socket client = serverSocket.accept();
            System.out.println("Receive client ip:" + client.getInetAddress().getHostAddress() + "; port:" + client.getPort());

            new Thread(()->{
                try {
                    InputStream inputStream = client.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                    char[] chars = new char[1024];
                    while (true){
                        //3.2 数据读取会阻塞
                        //man 2 read
                        //> 0：表示读取到字节
                        //= 0：表示读取到末尾，或者没有读取到数据
                        //< 0：读取错误，或者网络断开
                        int readNums = bufferedReader.read(chars);
                        if (readNums > 0){
                            System.out.println("Read char Nums : "+ readNums + ";Read chars:" + new String(chars));
                        }else if (readNums == 0){
                            System.out.println("Read nothing");
                        }else {
                            System.out.println("Client closed");
                            client.close();
                            break;
                        }
                    }
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }).start();
        }

    }
}
