package com.liuming.mej2ee.luban.nio.reactor.one;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

public class TCPReactor implements Runnable {

    private final ServerSocketChannel serverSocketChannel;
    private final Selector selector;

    public TCPReactor(int port) throws Exception {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(port);
        serverSocketChannel.bind(address);// 绑定端口
        serverSocketChannel.configureBlocking(false);// 设置为非阻塞
        SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);// 监听连接事件
        selectionKey.attach(new Acceptor(selector, serverSocketChannel)); // 给定key一个附加的Acceptor对象

        // 另一种附加对象的方式
//        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT, new Acceptor(selector, serverSocketChannel));
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            System.out.println("Waiting for new event on port: " + serverSocketChannel.socket().getLocalPort());

            try {
                if (selector.select() == 0) {
                    continue;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                // 调度中心
                dispatch(selectionKey);
                iterator.remove();
            }
        }
    }

    /**
     * 根据附加对象去区分具体的事件类型及操作
     *
     * @param selectionKey
     */
    private void dispatch(SelectionKey selectionKey) {
        Runnable runnable = (Runnable) selectionKey.attachment();
        if (runnable != null)
            runnable.run();
    }
}
