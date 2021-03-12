package com.liuming.mej2ee.luban.nio.reactor.masterandslave;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ThreadPoolExecutor;

public class ReadState implements HandlerState {

    private SelectionKey selectionKey;

    @Override
    public void changeState(TCPHandler handler) {
        handler.setState(new WorkState());
    }

    @Override
    public void handler(TCPHandler handler, SelectionKey selectionKey, SocketChannel socketChannel, ThreadPoolExecutor pool) throws IOException {
        this.selectionKey = selectionKey;

        byte[] arr = new byte[1024];
        ByteBuffer buffer = ByteBuffer.wrap(arr);

        int numBytes = socketChannel.read(buffer);
        if (numBytes == -1) {
            System.out.println("[Warning!] A client has been closed.");
            handler.closeChannel();
            return;
        }

        String str = new String(arr);
        if (!str.trim().equals("")) {
            handler.setState(new WorkState());
            pool.execute(new WorkerThread(handler, str));
            System.out.println(socketChannel.socket().getRemoteSocketAddress().toString() + " > " + str);
        }

    }

    /**
     * 模拟处理业务逻辑
     *
     * @param handler
     * @param str
     */
    synchronized void process(TCPHandler handler, String str) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        handler.setState(new WriteState());
        this.selectionKey.interestOps(SelectionKey.OP_WRITE);
        this.selectionKey.selector().wakeup(); // 使一个阻塞住的selector操作立即返回，针对selector.select()阻塞
    }

    /**
     * 模拟处理业务逻辑
     *
     * @param handler
     * @param str
     */
    synchronized void process(TCPHandler handler, String str, SocketChannel socketChannel) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String content = "Your message has send to " + socketChannel.socket().getRemoteSocketAddress().toString() + "\r\n";
        ByteBuffer buffer = ByteBuffer.wrap(content.getBytes());

        while (buffer.hasRemaining()) {
            try {
                socketChannel.write(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

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
}
