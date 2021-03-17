package com.liuming.mej2ee.luban.nio.netty.seconddemo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class TestClient {

    public static void main(String[] args) {

        EventLoopGroup client = new NioEventLoopGroup(1);

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(client)
                    .channel(NioSocketChannel.class)
                    .handler(new TestClientInitializer());

            ChannelFuture future = bootstrap.connect("127.0.0.1", 8090).sync();
            future.channel().closeFuture().sync();

        } catch (Exception e) {

        } finally {
            client.shutdownGracefully();
        }
    }
}
