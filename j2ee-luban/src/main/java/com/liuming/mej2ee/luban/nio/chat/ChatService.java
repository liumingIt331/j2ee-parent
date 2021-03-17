package com.liuming.mej2ee.luban.nio.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * selectionKey
 * 是一个抽象类，表示selectableChannel在selector中注册的表示，每个Channel向Selector中注册时，都会创建一个SelectionKey
 * selectionKey将channel和Selector建立了关系，并维护了channel事件
 * 可以通过cancel方法取消键，取消的键不会立即从selector中移除，而是添加在canceledKeys中，在下一次select操作时移除它，所以在调用某个key时，需要使用isInvalid进行校验
 */
public class ChatService {

    private Selector selector;

    private ServerSocketChannel serverSocketChannel;

    private long timeout = 2000;

    public ChatService() {
        try {
            // 服务端channel
            serverSocketChannel = ServerSocketChannel.open();

            // 选择器对象
            selector = Selector.open();

            // 绑定端口
            serverSocketChannel.bind(new InetSocketAddress(9000));

            // 设置非阻塞模式（必须步骤）
            serverSocketChannel.configureBlocking(false);

            //把ServerSocketChannel注册到Selector
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);// OP_ACCEPT 监听连接

            System.out.println("服务端准备就绪!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() throws IOException {
//        int count = 0;
//        long start = System.nanoTime();
//        selector.select(timeout);
        while (true) {
//            long end = System.nanoTime();
//            if (end - start >= TimeUnit.MICROSECONDS.toNanos((timeout))) {
//                count = 1;
//            } else {
//                count++;
//            }
//
//            if (count >= 10) {
//                System.out.println("有可能发生空轮询" + count + "次");
//                rebuildSelector();
//                count = 0;
//                selector.selectNow();
//                continue;
//            }

            // 等timeout时间 查看有没有感兴趣的时间发生，有则添加到SelectionKey里面
            // 不指定时间就是阻塞了，一直等到有客户端来连接才会继续执行
            selector.select(timeout);

            // 得到SelectionKey对象，判断是什么事件
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                if (selectionKey.isAcceptable()) { // 连接事件
                    // 获取网络通道
                    SocketChannel socketChannel = serverSocketChannel.accept();

                    // 设置非阻塞式
                    socketChannel.configureBlocking(false);

                    // 连接上了，注册读取事件
                    socketChannel.register(selector, SelectionKey.OP_READ);

                    System.out.println(socketChannel.getRemoteAddress().toString() + "上线了");

                }

                if (selectionKey.isReadable()) { //读取客户端数据事件
                    // 读取客户端发来的数据
                    readClientData(selectionKey);
                }

                // 手动从当前集合将本次运行完的对象删除（必须删除：如果不删除还会处理以前已经处理过的事件）
                iterator.remove();
            }
        }
    }

    /**
     * 读取客户端发来的数据
     *
     * @param selectionKey
     * @throws IOException
     */
    private void readClientData(SelectionKey selectionKey) throws IOException {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        try {
            int read = socketChannel.read(byteBuffer);
            byteBuffer.flip();
            if (read > 0) {
                byte[] bytes = new byte[read];
                byteBuffer.get(bytes, 0, read);
                // 读取了数据
                String data = new String(bytes, "utf-8");
                writeClientData(socketChannel, data);
            }
        } catch (IOException e) {
            selectionKey.cancel();
            if (socketChannel != null) {
                System.out.println(socketChannel.getRemoteAddress().toString() + "下线了");
                socketChannel.close();
            }
        }
    }

    /**
     * 给其他客户端返回数据
     *
     * @param socketChannel
     * @param data
     * @throws IOException
     */
    private void writeClientData(SocketChannel socketChannel, String data) throws IOException {
        Set<SelectionKey> keys = selector.keys();
        for (SelectionKey key : keys) {
            if (key.isValid()) {
                SelectableChannel channel = key.channel();
                if (channel instanceof SocketChannel) {
                    SocketChannel socketChannel1 = (SocketChannel) channel;
                    if (channel != socketChannel) { // 不能发给自己
                        ByteBuffer wrap = ByteBuffer.wrap(data.getBytes());
                        socketChannel1.write(wrap);
                    }
                }
            }
        }
    }

    private void rebuildSelector() throws IOException {
        Selector newSelector = Selector.open();
        Selector oldSelector = selector;
        for (SelectionKey selectionKey : oldSelector.keys()) {
            int i = selectionKey.interestOps();
            selectionKey.cancel();
            selectionKey.channel().register(newSelector, i);
        }
        selector = newSelector;
        oldSelector.close();
    }

    public static void main(String[] args) throws Exception {
        new ChatService().start();
    }
}
