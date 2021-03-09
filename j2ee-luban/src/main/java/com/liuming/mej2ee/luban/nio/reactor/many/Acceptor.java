package com.liuming.mej2ee.luban.nio.reactor.many;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Acceptor implements Runnable {

    private Selector selector;
    private ServerSocketChannel serverSocketChannel;

    public Acceptor(Selector selector, ServerSocketChannel serverSocketChannel) {
        this.selector = selector;
        this.serverSocketChannel = serverSocketChannel;
    }

    @Override
    public void run() {
        try {
            SocketChannel socketChannel = serverSocketChannel.accept(); // 接收客户端的连接
            if (socketChannel != null) {
                System.out.println(socketChannel.socket().getRemoteSocketAddress().toString() + " is connected.");
                socketChannel.configureBlocking(false);
                SelectionKey selectionKey = socketChannel.register(selector, SelectionKey.OP_READ);
                selector.wakeup();
                selectionKey.attach(new TCPHandler(socketChannel, selectionKey));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
