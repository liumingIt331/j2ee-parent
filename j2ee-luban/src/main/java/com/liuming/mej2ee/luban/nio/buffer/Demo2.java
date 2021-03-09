package com.liuming.mej2ee.luban.nio.buffer;

import java.nio.ByteBuffer;

/**
 * ByteBuffer的分类
 *  DirectByteBuffer：堆外缓冲区
 *  DirectByteBufferR：堆外缓冲区（只读）
 *      仅仅是对象在JVM里面(引用地址)，数据存放在堆外的内存
 *
 *  HeapByteBuffer：堆内缓冲区
 *  HeapByteBufferR：堆内缓冲区（只读）
 *      在JVM里面，底层结构是byte[]
 */
public class Demo2 {

    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        for (int i = 0; i < byteBuffer.capacity(); i++) {
            byteBuffer.put((byte) i);
        }

        // 复制只读的buffer
        ByteBuffer byteBuffer1 = byteBuffer.asReadOnlyBuffer();
        System.out.println(byteBuffer.getClass());
        System.out.println(byteBuffer1.getClass());
        byteBuffer1.flip();

        System.out.println("--------------------");

        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer1.position());

        System.out.println("--------------------");

        for (int i = 0; i < byteBuffer1.capacity(); i++) {
            System.out.println(byteBuffer1.get());
        }
    }
}
