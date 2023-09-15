package com.wy.mca.io.nio.channel;

import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author wangyong01
 * @version 2023/8/16 9:11 PM
 * @description
 */
public class FileChannelClient {

    /**
     * 通过FileChannel读取文件部分内容到ByteBuffer
     * @throws Exception
     */
    @Test
    public void fileChannel01() throws Exception {
        //1. 设置ByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);

        //2. 获取Channel
        FileChannel fileChannel = FileChannel.open(Paths.get("/Users/admin/logs/boss_info.log"), StandardOpenOption.READ);
        //2.1 读取数据到ByteBuffer
        fileChannel.read(byteBuffer);

        //3. 切换到读模式
        byteBuffer.flip();
        System.out.println("Read from file: ");
        System.out.println(new String(byteBuffer.array()));
    }

    /**
     * 通过FileChannel读取文件并全部输出
     */
    @Test
    public void fileChannel02() throws Exception {
        //1. 设置ByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        //2.1 获取Channel read
        FileChannel readChannel = FileChannel.open(Paths.get("/Users/admin/logs/boss_info.log"), StandardOpenOption.READ);
        //2.2 获取Channel write
        FileChannel writeChannel = FileChannel.open(Paths.get("/Users/admin/logs/boss_info_write.log"), StandardOpenOption.WRITE);

        //3. 读取数据到ByteBuffer
        while (readChannel.read(byteBuffer) != -1){
            //3.1. 切换到读模式
            byteBuffer.flip();

            //4. 写出到文件
            writeChannel.write(byteBuffer);
//            writeChannel.force(true);

            //5. 清空ByteBuffer
            byteBuffer.clear();
        }
    }

}
