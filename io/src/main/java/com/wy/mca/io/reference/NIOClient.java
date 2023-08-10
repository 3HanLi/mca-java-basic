package com.wy.mca.io.reference;

import java.nio.Buffer;
import java.nio.ByteBuffer;

public class NIOClient {

    public static void main(String[] args) {
//        nioMethod();
        nioOperate();
    }

    /**
     * 1 分配内存的两种方式
     *   1.1 堆外分配内存
     *       ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
     *   1.2 堆上分配内存
     *       ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
     * 2 方法详解
     *   2.1 capacity：缓冲区容量
     *   2.2 limit：缓冲区可读取的最大容量
     *   2.3 position：当前读取到的下标
     *   2.4 put：填充字节数组
     *   2.5 clear：清空元素，并重置position
     *   2.6 flip：每次读取之前都要先调用flip，将position恢复到0，将limit移动到实际大小
     *   2.6 compact：读取元素后，会导致左侧有空余，进行压缩，同时重置limit和capacity
     */
    private static void nioMethod(){
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
        System.out.println("position: " + byteBuffer.position());
        System.out.println("limit: " +  byteBuffer.limit());
        System.out.println("capacity: " + byteBuffer.capacity());
        System.out.println("Mark:" + byteBuffer);

        byteBuffer.put("123".getBytes());
        System.out.println("Mark:" + byteBuffer);

        Buffer flip = byteBuffer.flip();
//        while (flip.hasRemaining()){
//            byte b = byteBuffer.get();
//            System.out.println("Get:" + b);
//        }
        byteBuffer.get();
        System.out.println("Mark:" + byteBuffer);

        byteBuffer.compact();
        System.out.println("Mark:" + byteBuffer);

        byteBuffer.flip();
        System.out.println("Mark:" + byteBuffer);
    }

    private static void nioOperate(){
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
        byteBuffer.put("123".getBytes());

        Buffer flip = byteBuffer.flip();
        byte[] bytes = new byte[byteBuffer.limit()];
        int idx = 0;
        while (flip.hasRemaining()){
            bytes[idx++] = byteBuffer.get();
        }
        System.out.println("Read:" + new String(bytes));
        System.out.println("Mark:" + byteBuffer);

        byteBuffer.compact();
//        byteBuffer.flip();
//        byteBuffer.clear();
        System.out.println("Mark:" + byteBuffer);

        byteBuffer.put("4567".getBytes());
        System.out.println("Mark:" + byteBuffer);
    }

}
