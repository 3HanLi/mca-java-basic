package com.wy.mca.io.reference;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class BasicOperateIO {

    private static byte[] data = "123456789\n".getBytes();
    private static String path = "/root/out.txt";

    public static void main(String[] args) throws Exception {

        switch (args[0]) {
            case "0":
                testBasicFileIO();
                break;
            case "1":
                testBufferedFileIO();
                break;
            case "2":
                testRandomAccessFileWrite();
            case "3":
                whatByteBuffer();
            default:

        }
    }


    /**
     * 最基本的File写入
     * 1）每写入一次，就会发生一次系统调用
     * 2）数据会优先写入到内存中，当发生断电，会导致内核来不及将内存中的数据耍会磁盘，导致数据丢失；如果正常关机，则数据不会丢失
     *
     * @throws Exception
     */
    public static void testBasicFileIO() throws Exception {
        File file = new File(path);
        FileOutputStream out = new FileOutputStream(file);
        while (true) {
            //Thread.sleep(10);
            out.write(data);
        }
    }

    /**
     * 测试BufferIO
     * 1）会在java进程空间开辟一个8kb的字节数组，out.write会优先将数据写入该字节数组
     * 2）当该字节数组空间满了之后，才会发生一次系统调用，将数据写入到内存，但是如果发生断电，依然会导致内核来不及讲内存中的数据刷会磁盘，而导致数据丢失
     *
     * @throws Exception
     */
    public static void testBufferedFileIO() throws Exception {
        File file = new File(path);
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
        while (true) {
            //Thread.sleep(10);
            out.write(data);
        }
    }

    /**
     * 测试RandomAccessFile进行随机读写，并引申出mmap
     *
     * @throws Exception
     */
    public static void testRandomAccessFileWrite() throws Exception {
        //1 使用randomAccessFile写入数据，然后阻塞
        RandomAccessFile raf = new RandomAccessFile(path, "rw");
        raf.write("hello mashibing\n".getBytes());
        raf.write("hello seanzhou\n".getBytes());
        System.out.println("write------------");
        System.in.read();

        //2 使用随机寻址，直接定位到pos=4的位置
        raf.seek(4);
        raf.write("ooxx".getBytes());
        System.out.println("seek---------");
        System.in.read();

        //IO模型 Start

        //3 通过RandomAccessFile获取fileChannel，只有FileChannel才有map方法，只有块设备才能做内存映射，而socket是不能做内存映射的
        FileChannel rafchannel = raf.getChannel();
        //mmap  堆外  和文件映射的   byte  not  object
        //3.1 通过map方法能够获取到堆外空间ByteBuffer对象，可以直接和内存的page cache映射起来
        MappedByteBuffer map = rafchannel.map(FileChannel.MapMode.READ_WRITE, 0, 4096);

        //3.2 使用put代替曾经的write方法，当执行put方法写入时就不会发生系统调用，提高效率。但是这种方式依然需要将数据写入到page cache中，依旧会存在丢失数据的风险
        map.put("@@@".getBytes());
        //不是系统调用  但是数据会到达 内核的pagecache
        //曾经我们是需要out.write()  这样的系统调用，才能让程序的data 进入内核的pagecache
        //曾经必须有用户态内核态切换
        //mmap的内存映射，依然是内核的pagecache体系所约束的！！！
        //换言之，丢数据
        //你可以去github上找一些 其他C程序员写的jni扩展库，使用linux内核的Direct IO
        //直接IO是忽略linux的pagecache
        //是把pagecache  交给了程序自己开辟一个字节数组当作pagecache，动用代码逻辑来维护一致性/dirty。。。一系列复杂问题

        //IO 模型 End

        //3.3 directIO：相当于应用程序自己管理page cache，只不过这个page cache是在应用程序自己的内存空间
        //数据库一般都是使用directIO，也就是程序自己管理page cache，这样可以保证数据的可靠性，同时也能提升性能
        //不过这种方法，需要应用程序额外植入代码自己维护page cache的dirty页
        System.out.println("map--put--------");
        System.in.read();

        //3.4 通过force强制将page cache刷回磁盘
        //map.force(); //  等同于out.flush
        raf.seek(0);

        //3.5 分配ByteBuffer，并通过read将channel中的数据读取到buffer中.
        ByteBuffer buffer = ByteBuffer.allocate(8192);
        //ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        //buffer.put()

        int read = rafchannel.read(buffer);
        System.out.println(buffer);
        buffer.flip();
        System.out.println(buffer);

        for (int i = 0; i < buffer.limit(); i++) {
            Thread.sleep(200);
            System.out.print(((char) buffer.get(i)));
        }
    }


    /**
     * ByteBuffer详解
     */
    public static void whatByteBuffer() {
        //ByteBuffer buffer = ByteBuffer.allocate(1024);
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

        System.out.println("postition: " + buffer.position());
        System.out.println("limit: " + buffer.limit());
        System.out.println("capacity: " + buffer.capacity());
        System.out.println("mark: " + buffer);

        buffer.put("123".getBytes());

        System.out.println("-------------put:123......");
        System.out.println("mark: " + buffer);

        buffer.flip();   //读写交替

        System.out.println("-------------flip......");
        System.out.println("mark: " + buffer);

        buffer.get();

        System.out.println("-------------get......");
        System.out.println("mark: " + buffer);

        buffer.compact();

        System.out.println("-------------compact......");
        System.out.println("mark: " + buffer);

        buffer.clear();

        System.out.println("-------------clear......");
        System.out.println("mark: " + buffer);

    }
}
