package com.wy.mca.io.bio;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * BIO模式下的Client
 * @date 2021年09月15日 16:14:00
 * @author wangyong01
 */
public class BIOClient {

    public static void main(String[] args) throws Exception{
        //1 BIO Client
        Socket client = new Socket();
        client.setTcpNoDelay(false);
        client.connect(new InetSocketAddress("localhost",9090));

        OutputStream os = client.getOutputStream();
        while (true){
            //2 读取一行，并写出
            Scanner scanner = new Scanner(System.in);
            String inputLine = scanner.nextLine();
            System.out.println("Read line:" + inputLine);

            byte[] bytes = inputLine.getBytes();
            for (byte b : bytes){
                os.write(b);
            }
        }
    }
}
