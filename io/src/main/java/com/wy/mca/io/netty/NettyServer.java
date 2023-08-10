package com.wy.mca.io.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @Description
 * @Author wangyong01
 * @Date 2022/4/12 5:05 下午
 * @Version 1.0
 */
public class NettyServer {

    public static void main(String[] args) throws Exception{

//        startServer();

        startNettyServer();

    }

    public static void startServer() throws Exception{
        //1.1 创建selector group
        //这里可以指定多个线程，每个线程对应一个selector，用于提高处理效率
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup(2);
        //1.2 创建Server ==> ServerSocketChannel.open()，并注册到selector，注册时默认关注read事件（read 从 client发过来的数据）
        NioServerSocketChannel server = new NioServerSocketChannel();
        eventLoopGroup.register(server);
        //1.3 netty中，会把处理事件的handler放入pipeline中，因而我们只需要在server的pipeline加入handler，并在handler处理即可
        //对应到server这里，就是accept
        server.pipeline().addLast(new ServerAcceptHandler(eventLoopGroup, new ServerInitAdapterHandler()));

        //2.1 注册之后再bind
        ChannelFuture bind = server.bind(new InetSocketAddress(9090));

        //3.1 Server端不close，那么就一直阻塞
        bind.sync().channel().closeFuture().sync();
    }

    public static void startNettyServer() throws Exception{
        //boss group 和 worker group
        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        NioEventLoopGroup worker = new NioEventLoopGroup(3);

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap
                //1.1 指定selector group
                .group(boss, worker)
                //1.2 将 server register到selector
                .channel(NioServerSocketChannel.class)
                //1.3 将关注的handler放入pipeline中
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new ReadHandler());
                    }
                })
                //2.1 注册成功后bind端口
                .bind(9090)
                //3.1 Server端不关闭，就一直阻塞
                .sync()
                .channel()
                .closeFuture()
                .sync();
    }
}
