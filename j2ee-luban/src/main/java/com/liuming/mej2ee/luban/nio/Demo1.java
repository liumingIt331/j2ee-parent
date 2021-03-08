package com.liuming.mej2ee.luban.nio;

import java.nio.ByteBuffer;

public class Demo1 {

    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        for (int i = 0; i < byteBuffer.capacity(); i++) {
            byteBuffer.put((byte) i);
        }

        byteBuffer.position(2);
        byteBuffer.limit(8);
        // 根据原buffer的position和limit复制出一个新的bytebuffer
        // 这里只是复制，并未创建新的Buffer，所以resetByffer若改变，原buffer的内容也会改变
        ByteBuffer resetBuffer = byteBuffer.slice();
        System.out.println();
        System.out.println(resetBuffer.position());
        System.out.println(resetBuffer.limit());
        System.out.println(resetBuffer.capacity());
        System.out.println();

        for (int i = 0; i < resetBuffer.capacity(); i++) {
            byte anInt = byteBuffer.get();
            resetBuffer.put((byte) (anInt * 2));
        }

        byteBuffer.position(0);
        byteBuffer.limit(byteBuffer.capacity());
        while (byteBuffer.hasRemaining()) {
            System.out.println(byteBuffer.get());
        }
    }
}
