package com.liuming.mej2ee.common.utils;

import com.spire.doc.Document;
import com.spire.doc.FileFormat;

public class DocToPdfByInspireTest {

    public static void main(String[] args) throws Exception {
        //加载word示例文档

        Document document = new Document();

        document.loadFromFile("D:/test/pdf/template_demo_result.docx");

        //保存为PDF格式

        document.saveToFile("D:/test/pdf/index.pdf", FileFormat.PDF);

    }
}
