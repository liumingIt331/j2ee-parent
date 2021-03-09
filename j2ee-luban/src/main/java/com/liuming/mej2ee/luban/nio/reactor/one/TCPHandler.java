package com.liuming.mej2ee.luban.nio.reactor.one;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class TCPHandler implements Runnable {

    private SocketChannel socketChannel;
    private SelectionKey selectionKey;
    private Integer state;

    public TCPHandler(SocketChannel socketChannel, SelectionKey selectionKey) {
        this.socketChannel = socketChannel;
        this.selectionKey = selectionKey;
        state = 0; // 初始状态设定为READING
    }

    @Override
    public void run() {
        try {
            if (state == 0) {
                read();
            } else {
                send();
            }
        } catch (IOException e) {
            System.out.println("[Warning!] A client has been closed.");
            closeChannel();
        }
    }

    private void closeChannel() {
        try {
            socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void send() throws IOException {
        String str = "Your message has send to " + socketChannel.socket().getRemoteSocketAddress().toString() + "\r\n";
        ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());

        while (buffer.hasRemaining()) {
            socketChannel.write(buffer);
        }

        state = 0;
        selectionKey.interestOps(SelectionKey.OP_READ);
        selectionKey.selector().wakeup();
    }

    private void read() throws IOException {
        byte[] arr = new byte[1024];
        ByteBuffer byteBuffer = ByteBuffer.wrap(arr);

        int numBytes = socketChannel.read(byteBuffer);
        if (numBytes == -1) {
            System.out.println("[Warning!] A client has been closed.");
            closeChannel();
            return;
        }

        String str = new String(arr);
        if (!str.trim().equals("")) {
            process(str); // 逻辑处理
            System.out.println(socketChannel.socket().getRemoteSocketAddress().toString() + " > " + str);
            state = 1;
            selectionKey.interestOps(SelectionKey.OP_WRITE);
            selectionKey.selector().wakeup(); // 使一个阻塞住的selector操作立即返回，针对selector.select()阻塞
        }

    }

    /**
     * 模拟处理业务逻辑
     *
     * @param str
     */
    private void process(String str) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
