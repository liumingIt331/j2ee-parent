package com.liuming.mej2ee.luban.nio.reactor.masterandslave;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TCPHandler implements Runnable {

    private SocketChannel socketChannel;
    private SelectionKey selectionKey;
    private static final Integer THREAD_COUNTING = 10;
    private static final ThreadPoolExecutor POOL = new ThreadPoolExecutor(
            THREAD_COUNTING, THREAD_COUNTING, 10, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>()
    );

    private HandlerState state; // 以状态模式实现Handler

    public TCPHandler(SocketChannel socketChannel, SelectionKey selectionKey) {
        this.socketChannel = socketChannel;
        this.selectionKey = selectionKey;
        state = new ReadState(); // 初始状态设定为READING
        POOL.setMaximumPoolSize(32); // 设置最大线程数

    }

    @Override
    public void run() {
        try {
            state.handler(this, selectionKey, socketChannel, POOL);
        } catch (IOException e) {
            System.out.println("[Warning!] A client has been closed.");
        }

    }

    public void closeChannel() {
        try {
            socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setState(HandlerState state) {
        this.state = state;
    }
}
