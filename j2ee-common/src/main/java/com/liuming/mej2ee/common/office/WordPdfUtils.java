package com.liuming.mej2ee.common.office;

import com.liuming.mej2ee.common.utils.CommandExecute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * @author Rocca
 */
public class WordPdfUtils {

    protected static final Logger logger = LoggerFactory.getLogger(WordPdfUtils.class);

    public boolean wordConverterToPdf(String docxPath) throws IOException {
        File file = new File(docxPath);
        String path = file.getParent();
        try {
            String osName = System.getProperty("os.name");
            String command = "";
            if (osName.contains("Windows")) {
                command = "C:/Program Files/LibreOffice/program/soffice --convert-to pdf  -outdir " + path + " " + docxPath;
            } else {
                command = "doc2pdf --output=" + path + File.separator + file.getName().replaceAll(".(?i)docx", ".pdf") + " " + docxPath;
            }
            String result = CommandExecute.executeCommand(command);
            System.out.println("生成pdf的result==" + result);
            if (result.equals("") || result.contains("writer_pdf_Export")) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return false;
    }

    //测试用
    public static void main(String[] args) {
        try {
            // 建议都用/左斜杠这种，左斜杠是windows和linux通用的
            String srcPath = "D:/test/pdf/template_demo_result.docx";
            new WordPdfUtils().wordConverterToPdf(srcPath);
        } catch (IOException e) {
            System.out.println("word转换成pdf时出错");
            e.printStackTrace();
        }
    }
}
