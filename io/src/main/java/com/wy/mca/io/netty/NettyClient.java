package com.wy.mca.io.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @Description
 * @Author wangyong01
 * @Date 2022/4/12 3:56 下午
 * @Version 1.0
 */
public class NettyClient {

    public static void main(String[] args) throws Exception{

//        useByteBuf();

//        startClient();

        startNettyClient();

    }

    public static void startNettyClient() throws Exception{
        //创建selector
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup(1);

        Bootstrap bootstrap = new Bootstrap();
        ChannelFuture connect = bootstrap
                //1.1 指定selector
                .group(eventLoopGroup)
                //1.2 将client register selector
                .channel(NioSocketChannel.class)
                //1.3 将关注的事件放入pipeline
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new ReadHandler());
                    }
                })
                //2.1 client连接Server
                .connect("localhost", 9090);

        //2.2 client连接Server并设置同步，获取client
        Channel channel = connect.sync().channel();
        //2.3 封装ByteBuf对象并发送数据
        ByteBuf byteBuf = Unpooled.wrappedBuffer("dingbeibei".getBytes());
        channel.writeAndFlush(byteBuf);

        //2.3 如果通道不关闭，就一直阻塞
        channel.closeFuture().sync();
        System.out.println("Netty Client stop");
    }

    /**
     * Netty工具类启动client，关注点
     * 1）client主动发送数据
     * 2）server什么时候给我们回送数据，这就需要client不能阻塞，必然是基于事件的
     *
     * @throws Exception
     */
    public static void startClient() throws Exception{
        //1.1   创建selector group ==> fd3
        //这里可以指定多个线程，每个thread对应一个selector
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup(1);
        //1.2   创建client 并 register 到 selector，注册时默认就是关注read事件
        NioSocketChannel client = new NioSocketChannel();
        eventLoopGroup.register(client);
        //1.3 netty中，会把处理事件的handler放入client的pipeline中，因而我们只需要在client的pipeline加入handler，并在handler处理即可
        //对应到client这里就是：read
        client.pipeline().addLast(new ReadHandler());

        //2.1 client连接Server是异步的，这里需要设置同步
        ChannelFuture connect = client.connect(new InetSocketAddress("localhost", 9090));
        connect.sync();

        //2.2 client发送数据也是阻塞的，这里需要设置同步
        ByteBuf byteBuf = Unpooled.wrappedBuffer("wangyong01".getBytes());
        ChannelFuture send = client.writeAndFlush(byteBuf);
        send.sync();

        //3 如果连接的通道不关闭的话，就一直阻塞；换句话说就是：如果server不关闭，就一直阻塞
        connect.channel().closeFuture().sync();
    }

    /**
     * ByteBuf使用
     */
    public static void useByteBuf(){
        //1 ByteBuf创建工具类
        //1.1 PooledByteBufAllocator
        //1.2 UnpooledByteBufAllocator

        //2 ByteBuf分配方式
        //2.1 heapBuffer / buffer：堆上分配空间
        //2.2 directBuffer：堆外分配空间

        ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.heapBuffer(8, 20);
//        ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(8, 20);
//        ByteBuf byteBuf = UnpooledByteBufAllocator.DEFAULT.directBuffer(8, 20);

        byteBuf.writeBytes("1234".getBytes());
        printByteBufInfo(byteBuf);

        byteBuf.writeBytes("5678".getBytes());
        printByteBufInfo(byteBuf);

        //当初始容量写满后，此时容量会翻倍
        byteBuf.writeBytes("90".getBytes());
        printByteBufInfo(byteBuf);
    }

    private static void printByteBufInfo(ByteBuf byteBuf){
        System.out.println("capacity:" + byteBuf.capacity());
        System.out.println("readIndex:" + byteBuf.readerIndex());
        System.out.println("readBytes:" + byteBuf.readableBytes());
        System.out.println("writeIndex:" + byteBuf.writerIndex());
        System.out.println("writableBytes:" + byteBuf.writableBytes());
    }

}
