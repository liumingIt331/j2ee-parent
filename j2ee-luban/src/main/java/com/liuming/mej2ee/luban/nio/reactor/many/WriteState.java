package com.liuming.mej2ee.luban.nio.reactor.many;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ThreadPoolExecutor;

public class WriteState implements HandlerState {
    @Override
    public void changeState(TCPHandler handler) {

    }

    @Override
    public void handler(TCPHandler handler, SelectionKey selectionKey, SocketChannel socketChannel, ThreadPoolExecutor pool) throws IOException {

    }
}
