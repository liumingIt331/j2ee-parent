package com.liuming.mej2ee.luban.nio.netty.seconddemo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TestServer {

    public static void main(String[] args) {
        EventLoopGroup boss = null;
        EventLoopGroup worker = null;

        try {
            boss = new NioEventLoopGroup(1);
            worker = new NioEventLoopGroup();

            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new TestServerInitializer());

            ChannelFuture channelFuture = bootstrap.bind(8090).sync();
            channelFuture.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != boss) boss.shutdownGracefully();
            if (null != worker) worker.shutdownGracefully();
        }
    }
}
