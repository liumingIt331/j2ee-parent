package com.liuming.mej2ee.luban.nio.netty.seconddemo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

import java.util.UUID;

public class TestServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 有客户端连接时执行
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("有客户端接入：" + ctx.channel().remoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "-->连接关闭");
    }

    /**
     * 收到客户端发来的数据执行
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            System.out.println("读取消息：" + msg);
            ctx.channel().writeAndFlush("我已经收到了你的消息" + UUID.randomUUID().toString());
        } finally {
//            ReferenceCountUtil.release(msg);
        }
    }


    /**
     * 读取完毕后执行
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("读取消息完毕");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
    }
}
