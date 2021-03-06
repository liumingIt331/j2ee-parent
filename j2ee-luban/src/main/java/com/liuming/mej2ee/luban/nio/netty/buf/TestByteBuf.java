package com.liuming.mej2ee.luban.nio.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.Arrays;

public class TestByteBuf {

    public static void main(String[] args) {

        // 1.创建一个非池化的ByteBuf 大小为10字节
        ByteBuf buf = Unpooled.buffer(10);
        System.out.println("原始ByteBuf为------------------>" + buf.toString());
        System.out.println("1.ByteBuf中的内容为-----------------" + Arrays.toString(buf.array()));

        // 2.写入一段内容
        byte[] bytes = {1, 2, 3, 4, 5};
        buf.writeBytes(bytes);
        System.out.println("写入的Bytes为-------------->" + Arrays.toString(bytes));
        System.out.println("写入一段内容后ByteBuf为-------------->" + buf.toString());
        System.out.println("2.ByteBuf中的内容为-------------->" + Arrays.toString(buf.array()));

        // 3.读取一段内容
        byte b1 = buf.readByte();
        byte b2 = buf.readByte();
        System.out.println("读取的Bytes为-------------->" + Arrays.toString(new byte[]{b1, b2}));
        System.out.println("读取一段内容后ByteBuf为-------------->" + buf.toString());
        System.out.println("3.ByteBuf中的内容为-------------->" + Arrays.toString(buf.array()));

        // 4.将读取的内容丢弃
        buf.discardReadBytes();
        System.out.println("丢弃一段内容后ByteBuf为-------------->" + buf.toString());
        System.out.println("4.ByteBuf中的内容为-------------->" + Arrays.toString(buf.array()));

//        // 5.再写入一段内容
//        bytes = new byte[]{1, 2};
//        buf.writeBytes(bytes);
//        System.out.println("再写入的Bytes为-------------->" + Arrays.toString(bytes));
//        System.out.println("再写入一段内容后ByteBuf为-------------->" + buf.toString());
//        System.out.println("5.ByteBuf中的内容为-------------->" + Arrays.toString(buf.array()));

        // 5.清空读写指针(数据不清除)
        buf.clear();
        System.out.println("清空读写指针后ByteBuf为-------------->" + buf.toString());
        System.out.println("5.ByteBuf中的内容为-------------->" + Arrays.toString(buf.array()));

        // 6.再次写入一段内容，比第一段内容少
        byte[] bytes2 = {1, 2, 3};
        buf.writeBytes(bytes2);
        System.out.println("写入的Bytes为-------------->" + Arrays.toString(bytes));
        System.out.println("写入一段内容后ByteBuf为-------------->" + buf.toString());
        System.out.println("6.ByteBuf中的内容为-------------->" + Arrays.toString(buf.array()));

        // 7.将ByteBuf清零(指针不变)
        buf.setZero(0, buf.capacity());
        System.out.println("ByteBuf清零后ByteBuf为-------------->" + buf.toString());
        System.out.println("7.ByteBuf中的内容为-------------->" + Arrays.toString(buf.array()));

        // 8.再次写入一段超过容量的数据 会自动扩容
        byte[] bytes3 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        buf.writeBytes(bytes3);
        System.out.println("写入的Bytes为-------------->" + Arrays.toString(bytes));
        System.out.println("写入一段内容后ByteBuf为-------------->" + buf.toString());
        System.out.println("8.ByteBuf中的内容为-------------->" + Arrays.toString(buf.array()));
    }
}
