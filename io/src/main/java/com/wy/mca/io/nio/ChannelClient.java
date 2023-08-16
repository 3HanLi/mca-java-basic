package com.wy.mca.io.nio;

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
public class ChannelClient {

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

    @Test
    public void fileChannel02(){

    }

}
