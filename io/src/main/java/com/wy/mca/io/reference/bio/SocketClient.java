package com.wy.mca.io.reference.bio;

import java.io.*;
import java.net.Socket;

/**
 * SocketClient
 * @author wangyong01
 */
public class SocketClient {

    public static void main(String[] args) {
        try {
            Socket client = new Socket("localhost",9090);

            //设置缓冲区大小
            client.setSendBufferSize(20);
            //是否开启tcp优化，false表示开启优化，true表示不开启（也就是有数据就发送）
            client.setTcpNoDelay(false);
            OutputStream out = client.getOutputStream();

            InputStream in = System.in;
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            while(true){
                String line = reader.readLine();
                if(line != null ){
                    byte[] bb = line.getBytes();
                    for (byte b : bb) {
                        out.write(b);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
