package com.liuming.mej2ee.luban.nio.reactor.masterandslave;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

public class TCPSubReactor implements Runnable {

    private final ServerSocketChannel ssc;
    private final Selector selector;
    private boolean restart = false;
    int num;

    public TCPSubReactor(Selector selector, ServerSocketChannel serverSocketChannel, int num) {
        this.ssc = serverSocketChannel;
        this.selector = selector;
        this.num = num;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
//            System.out.println("Waiting for new event on port: " + serverSocketChannel.socket().getLocalPort());

            System.out.println("Wating for restart.");
            while (!Thread.interrupted() && !restart) {
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

    public void setRestart(boolean restart) {
        this.restart = restart;
    }
}
