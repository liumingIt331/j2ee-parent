package com.liuming.mej2ee.luban.nio.buffer;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Demo3 {

    public static void main(String[] args) {
        FileOutputStream fileOutputStream = null;
        FileInputStream fileInputStream = null;
        try {
            fileOutputStream = new FileOutputStream("demo3Write.txt");
            fileInputStream = new FileInputStream("demo3Read.txt");

            FileChannel channelRead = fileInputStream.getChannel();
            FileChannel channelWrite = fileOutputStream.getChannel();

            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(100); // 堆外缓冲
//            ByteBuffer byteBuffer = ByteBuffer.allocate(100); // 堆内缓冲

            while (true) {
                byteBuffer.clear();
                // 往buffer中写
                int readNumber = channelRead.read(byteBuffer);
                System.out.println("readNumber: " + readNumber);
                if (readNumber == -1) {
                    break;
                }

                byteBuffer.flip();

                // 从buffer种读
                channelWrite.write(byteBuffer);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
