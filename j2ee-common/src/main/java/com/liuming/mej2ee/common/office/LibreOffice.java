package com.liuming.mej2ee.common.office;

import java.io.IOException;

public class LibreOffice {

    /**
     * 利用libreOffice将office文档转换成pdf
     *
     * @param inputFile 目标文件地址
     * @param pdfFile   输出文件夹
     * @return
     */
    public static boolean convertOffice2PDF(String inputFile, String pdfFile) {
        long start = System.currentTimeMillis();
        String command;
        boolean flag;
        String osName = System.getProperty("os.name");
        System.out.println("OSName : " + osName);
        if (osName.contains("Windows")) {
//            command = "cmd /c soffice --headless --invisible --convert-to pdf:writer_pdf_Export " + inputFile + " --outdir " + pdfFile;
            command = "C:/Program Files/LibreOffice/program/soffice --convert-to pdf " + inputFile + " --outdir " + pdfFile;
        } else {
            command = "libreoffice --headless --invisible --convert-to pdf:writer_pdf_Export " + inputFile + " --outdir " + pdfFile;
        }
        flag = executeLibreOfficeCommand(command);
        long end = System.currentTimeMillis();
        System.out.println(String.format("用时:{%d} ms", end - start));
        return flag;
    }


    /**
     * 执行command指令
     *
     * @param command
     * @return
     */
    public static boolean executeLibreOfficeCommand(String command) {
        System.out.println("开始进行转化.......");
        Process process;// Process可以控制该子进程的执行或获取该子进程的信息
        try {
            System.out.println(String.format("convertOffice2PDF cmd : %s", command));
            process = Runtime.getRuntime().exec(command);// exec()方法指示Java虚拟机创建一个子进程执行指定的可执行程序，并返回与该子进程对应的Process对象实例。
            // 下面两个可以获取输入输出流
//            InputStream errorStream = process.getErrorStream();
//            InputStream inputStream = process.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        int exitStatus = 0;
        try {
            exitStatus = process.waitFor();// 等待子进程完成再往下执行，返回值是子线程执行完毕的返回值,返回0表示正常结束
            // 第二种接受返回值的方法
            int i = process.exitValue(); // 接收执行完毕的返回值
            System.out.println("i----" + i);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        if (exitStatus != 0) {
            System.out.println(String.format("convertOffice2PDF cmd exitStatus {%s}", exitStatus));
        } else {
            System.out.println(String.format("convertOffice2PDF cmd exitStatus {%s}", exitStatus));
        }
        process.destroy(); // 销毁子进程
        System.out.println("转化结束.......");
        return true;
    }

    public static void main(String[] args) {
        String srcPath = "D:/test/pdf/template_demo_result222.docx";
        String destDir = "D:/test/pdf/";

        convertOffice2PDF(srcPath, destDir);
    }
}
