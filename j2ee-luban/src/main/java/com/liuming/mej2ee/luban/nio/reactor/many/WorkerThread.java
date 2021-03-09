package com.liuming.mej2ee.luban.nio.reactor.many;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class WorkerThread extends ReadState implements Runnable {

    private TCPHandler handler;
    private String str;
    private SocketChannel socketChannel;

    public WorkerThread(TCPHandler handler, String str) {
        this.handler = handler;
        this.str = str;
    }

    public WorkerThread(TCPHandler handler, String str, SocketChannel socketChannel) {
        this.handler = handler;
        this.str = str;
        this.socketChannel = socketChannel;
    }

    @Override
    public void run() {
        process(handler, str);
    }
}
