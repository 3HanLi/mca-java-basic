package com.wy.mca.io.netty;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Description 共享handler
 * 1）如果不指定@ChannelHandler.Sharable，那么每当有一个client连接过来后，server端就会重新注册，导致连接失败
 * 2）由于我们关注的事件handler可能比较多，为每个Handler添加一个@ChannelHandler.Sharable注解，对于开发来说不太友好，这里我们注册一个共享的handler做一层桥接
 * 3）其实就仿照Netty的ChildInitializer
 * @Author wangyong01
 * @Date 2022/4/12 5:43 下午
 * @Version 1.0
 */
//Sharable表明该handler是共享的，这样也就不会重复注册了
@ChannelHandler.Sharable
public class ServerInitAdapterHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        //1.1 添加事件handler
        addHandler(ctx);
        //1.2 当前handler只是做了一层中转，避免我们关注的每个handler都需要@Sharable注解，相当于做了一层桥接；其实我们并不需要该handler，桥接之后移除即可
        ctx.pipeline().remove(this);
    }

    public void addHandler(ChannelHandlerContext ctx){
        ctx.pipeline().addLast(new ReadHandler());
    }
}
