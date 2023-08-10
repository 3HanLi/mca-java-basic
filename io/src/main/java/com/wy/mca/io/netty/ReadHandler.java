package com.wy.mca.io.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @Description 处理读取的handler
 * 1）netty中处理读取内容的handler需要实现ChannelInboundHandler接口，我们这里继承其实现类ChannelInboundHandlerAdapter
 * 2）netty中处理写出内容的handler需要实现ChannelOutboundHandler接口
 * @Author wangyong01
 * @Date 2022/4/12 4:51 下午
 * @Version 1.0
 */
public class ReadHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client registered.." + ctx.channel());
    }

    /**
     * client连接server后，如果server向client发送数据就会调用该方法，表示我们关注的read事件
     *
     * @param ctx
     * @param msg   server向client发送的一定是消息，也就是ByteBuf对象
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channel Read start ");
        ByteBuf byteBuf = (ByteBuf) msg;

        //1 读取到server发送的数据
        CharSequence readData = byteBuf.getCharSequence(0, byteBuf.readableBytes(), CharsetUtil.UTF_8);
        System.out.println("Read data:" + readData);

        //2 读取Server数据后的处理，这里我们写回去（纯测试使用）
        ctx.writeAndFlush(byteBuf);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client read complete ");
    }
}
