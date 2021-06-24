package com.liuming.mej2ee.luban.netty.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerChannelHandler extends SimpleChannelInboundHandler<String> {

    // 保存连接的channel
    private static ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        group.writeAndFlush(String.format("[%s][服务器]\t用户：%s 加入聊天室！ \n",
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), channel.remoteAddress()));

        group.add(channel);

        channel.writeAndFlush(String.format("你好，%s 欢迎来到Netty聊天室！\n", channel.remoteAddress()));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        group.remove(channel);

        group.writeAndFlush(String.format("[%s][服务器]\t用户：%s 离开聊天室！ \n",
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), channel.remoteAddress()));

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel currentChannel = ctx.channel();

        System.out.println(String.format("[%s][%s]\t%s\n",
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), currentChannel.remoteAddress(), msg));


        for (Channel channel : group) {
            String sendMessage = "";
            if (channel == currentChannel) {
                sendMessage = String.format("[%s][You]\t%s\n",
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), msg);
            } else {
                sendMessage = String.format("[%s][%s]\t%s\n",
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),
                        currentChannel.remoteAddress(), msg);
            }
            channel.writeAndFlush(sendMessage);
        }
    }
}
