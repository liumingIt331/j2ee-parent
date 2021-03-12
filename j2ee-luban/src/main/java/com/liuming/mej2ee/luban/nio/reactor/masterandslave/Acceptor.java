package com.liuming.mej2ee.luban.nio.reactor.masterandslave;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Acceptor implements Runnable {

    private final ServerSocketChannel serverSocketChannel;
    private final int cores = Runtime.getRuntime().availableProcessors(); // 获取cpu核心数
    private final Selector[] selectors = new Selector[cores]; //
    private int selIdx = 0; // 当前可使用的subReactor索引
    private TCPSubReactor[] r = new TCPSubReactor[cores]; // subReactor线程
    private Thread[] t = new Thread[cores]; // subReactor线程

    public Acceptor(ServerSocketChannel serverSocketChannel) throws IOException {
        this.serverSocketChannel = serverSocketChannel;

        // 创建多个selector以及多个subReactor线程
        for (int i = 0; i < cores; i++) {
            selectors[i] = Selector.open();
            r[i] = new TCPSubReactor(selectors[i], serverSocketChannel, i);
            t[i] = new Thread(r[i]);
            t[i].start();
        }
    }

    @Override
    public synchronized void run() {
        try {
            SocketChannel socketChannel = serverSocketChannel.accept(); // 接收客户端的连接
            if (socketChannel != null) {
                System.out.println(socketChannel.socket().getRemoteSocketAddress().toString() + " is connected.");
                socketChannel.configureBlocking(false);

                r[selIdx].setRestart(true); // 暂停线程
                selectors[selIdx].wakeup(); // 使一个阻塞的select操作立即返回
                SelectionKey sk = socketChannel.register(selectors[selIdx], SelectionKey.OP_READ);
                selectors[selIdx].wakeup();
                r[selIdx].setRestart(false); // 重启线程
                sk.attach(new TCPHandler(socketChannel, sk));
                if (++selIdx == selectors.length) {
                    selIdx = 0;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
