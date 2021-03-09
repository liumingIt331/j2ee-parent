package com.liuming.mej2ee.luban.nio.reactor.many;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ThreadPoolExecutor;

public interface HandlerState {

    void changeState(TCPHandler handler);

    void handler(TCPHandler handler, SelectionKey selectionKey,
                 SocketChannel socketChannel, ThreadPoolExecutor pool) throws IOException;
}
