package com.wy.mca.io.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Description Server端accept的handler
 * @Author wangyong01
 * @Date 2022/4/12 5:27 下午
 * @Version 1.0
 */
public class ServerAcceptHandler extends ChannelInboundHandlerAdapter {

    private NioEventLoopGroup nioEventLoopGroup;

    private ServerInitAdapterHandler adapterHandler;

    public ServerAcceptHandler(NioEventLoopGroup nioEventLoopGroup, ServerInitAdapterHandler adapterHandler) {
        this.nioEventLoopGroup = nioEventLoopGroup;
        this.adapterHandler = adapterHandler;
    }

    /**
     * selector注册成功的事件
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("server channel registered..");
    }

    /**
     * 处理来自client的连接事件
     *
     * @param ctx
     * @param msg   表示client连接，对应到NIO就是：serverSocketChannel.accept()，只不过netty对此进行了封装，我们可以直接转为SocketChannel
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //1.1 client连接
        NioSocketChannel socketChannel = (NioSocketChannel) msg;
        //1.2 将client注册到selector上
        nioEventLoopGroup.register(socketChannel);

        //1.3 将处理事件的handler放入pipeline中，可以放入多个，那么这些handler就会以此处理读取到的内容
        socketChannel.pipeline().addLast(adapterHandler);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("server channel read complete..");
    }
}
