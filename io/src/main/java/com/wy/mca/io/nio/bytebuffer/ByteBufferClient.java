package com.wy.mca.io.nio.bytebuffer;

import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

/**
 * @author wangyong01
 * @version 2023/8/16 9:02 PM
 * @description
 */
public class ByteBufferClient {

    @Test
    public void byteBuffer01() {
        //1. 定义ByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(32);

        //2. 向byteBuffer写入数据
        byte[] bytes = "I am studying IT and Math".getBytes();
        System.out.println("bytes len:" + bytes.length);
        byteBuffer.put(bytes);

        //3. 读取数据-切换读模式
        byteBuffer.flip();
        //3.1 读取一个字节
        byte b = byteBuffer.get();
        System.out.println("b:" + (char)(b));
        //3.2 读取3个字节
        byte[] readBytes = new byte[3];
        byteBuffer.get(readBytes);
        System.out.println("read three bytes:" + new String(readBytes));

        //4. 写数据
        int remaining = byteBuffer.remaining();
        System.out.println("remaining:" + remaining);
    }
}
