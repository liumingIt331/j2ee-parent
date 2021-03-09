package com.liuming.mej2ee.luban.nio.buffer;

import java.nio.ByteBuffer;

public class Demo4 {

    public static void main(String[] args) {
        byte[] bytes = new byte[]{'a', 'b', 'c'};

        // 这里wrap也是堆内缓冲
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        bytes[0] = 'b';

        byteBuffer.put(2, (byte) 'b');
        for (int i = 0; i < byteBuffer.capacity(); i++) {
            System.out.println((char) byteBuffer.get());
        }
    }
}
