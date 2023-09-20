package com.wy.mca.io.reference.bio;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * BIO案例演示
 * @author wangyong01
 */
public class BIOSocketIOProperties {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(9090);
            System.out.println("Step1:8090 listen");
            while (true){
                //1 Server端启动后，监听外部连接，对应的文件描述符为fd3，serverSocket.accept()阻塞
                //2 当有外部连接时，启动线程进行处理
                final Socket clientSocket = serverSocket.accept();
                System.out.println("Step2 accept clientPort:" + clientSocket.getPort());

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //2.1 启动线程进行处理，此时的clientSocket对应fd5
                            InputStream inputStream = clientSocket.getInputStream();
                            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                            while (true){
                                //2.2 当fd中数据为空时进行阻塞
                                String readLine = reader.readLine();
                                if (null != readLine){
                                    System.out.println("Read:" + readLine);
                                }
                                //2.3 client断开连接时fd5消失
                                else {
                                    System.out.println("数据读取完毕,关闭client连接");
                                    clientSocket.close();
                                    break;
                                }
                            }
                            System.out.println("客户端断开");
                        } catch (Exception ex){
                            System.out.println("读取Socket数据异常");
                            ex.printStackTrace();
                        }
                    }
                }).start();
            }
        } catch (Exception ex){
            System.out.println("Server启动失败");
            ex.printStackTrace();
        }
    }

}
