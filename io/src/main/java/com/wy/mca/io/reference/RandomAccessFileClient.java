package com.wy.mca.io.reference;

import com.wy.mca.io.util.TimeUnitUtil;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 随机读取
 *
 * @author wangyong01
 */
public class RandomAccessFileClient {

    public static void main(String[] args) throws Exception {
        randomAccessFileOperate();
    }

    private static void randomAccessFileOperate() throws Exception {
        String filePath = "/Users/admin/Desktop/output.txt";
        //1.1 普通的write操作，会先把数据写入到应用程序的缓冲区，然后写入到内核的page cache，这里会发生系统调用，也就是：从用户态切换到内核态
        RandomAccessFile randomAccessFile = new RandomAccessFile(filePath, "rw");
        randomAccessFile.write("I am wangyong\n".getBytes());
        randomAccessFile.write("You are My wife\n".getBytes());
        System.out.println("Write: I am wangyong You are My wife");
        System.in.read();

        //1.2 基于指定偏移量的write操作，可以在不打开文件的情况下直接写入指定位置
        randomAccessFile.seek(4);
        randomAccessFile.write("ooxx".getBytes());
        System.out.println("Write ooxx at pos 4");
        System.in.read();

        //2.1 通过fileChannel获取和内核的page cache直接映射的对象，也就是mmap，总结起来就是：mmap是一个可以直接操作内核page cache的对象
        //2.2 只有文件的channel能进行mmap，因为文件是块设备，所以才能使得page cache和文件地址映射起来
        System.out.println("Mmap操作");
        FileChannel fileChannel = randomAccessFile.getChannel();
        //2.3 mmap操作会直接调用操作系统的fwrite函数，需要传入page_size的整数倍，也就是4K的整数倍，这里传入一个page_size
        MappedByteBuffer mmap = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 4096);
        //2.4 上面的write操作会发生用户态到内核台的切换，基于mmap的write操作会直接写入到内核的page cache，不会发生系统调用，效率极高
        mmap.put("_xxoo".getBytes());
        //2.5 及时将mmap对应的page cache刷回磁盘，即使不调用，内核也会周期性的将数据刷回磁盘，默认是30s一次
        mmap.force();

        System.in.read();

        //3.1 从从开始读取文件，将文件内容读取到ByteBuffer
        System.out.println("Read mmap to byteBuffer");
        randomAccessFile.seek(0);
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4096);
        fileChannel.read(byteBuffer);

        //3.2 开启读模式，同时观察进程的文件描述符
        byteBuffer.flip();
        for (int i = 0; i < byteBuffer.limit(); i++) {
            TimeUnitUtil.sleepMillions(100);
            System.out.print((char) byteBuffer.get());
        }

    }

}
