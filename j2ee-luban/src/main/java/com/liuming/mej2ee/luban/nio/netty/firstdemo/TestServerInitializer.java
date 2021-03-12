package com.liuming.mej2ee.luban.nio.netty.firstdemo;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * HttpRequestEncoder，将HttpRequest或HttpContent编码成ByteBuf
 * HttpRequestDecoder，将ByteBuf解码成HttpRequest或HttpContent
 * HttpResponseEncoder，将HttpResponse或HttpContent编码成ByteBuf
 * HttpResponseDecoder，将ByteBuf解码成HttpResponse或HttpContent
 *
 * ChannelInitializer 特殊的Handler
 *  本身是一个Handler，但是只是一个辅助的Handler，通过调用handlerAdded()-->initChannel()往Pipeline中添加其他Handler，
 *  添加完毕后ChannelInitializer会被删除
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast("httpServerCodec", new HttpServerCodec());
        pipeline.addLast("testServerHandler", new TestServerHandler());
    }
}
