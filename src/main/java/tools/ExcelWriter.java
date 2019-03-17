package tools;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import singleton.Data;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelWriter {
    public static void writerAll(String path) throws IOException, FileNotFoundException {
        //创建workbook
        XSSFWorkbook workbook = new XSSFWorkbook();

        //添加workSheet ，不添加打开会报错
        XSSFSheet sheet1 = workbook.createSheet("sheet1");

        //新建文件
        FileOutputStream out = null;

        for (int i = 0; i < Data.getInstance().getOriginalData().length; i++) {
            XSSFRow row = workbook.getSheet("sheet1").createRow(i);
            for (int j = 0; j < Data.getInstance().getOriginalData()[0].length; j++) {
                row.createCell(j).setCellValue((String) Data.getInstance().getOriginalData()[i][j]);
            }
            for (int j = Data.getInstance().getOriginalData()[0].length; j < (Data.getInstance().getOriginalData()[0].length + Data.getInstance().getTranData()[0].length); j++) {
                row.createCell(j).setCellValue((String) Data.getInstance().getOriginalData()[i][j - Data.getInstance().getOriginalData()[0].length]);
            }
        }

        out = new FileOutputStream(path);
        workbook.write(out);
        out.close();
    }

    public static void writerTransData(String path) throws IOException, FileNotFoundException {
        //创建workbook
        XSSFWorkbook workbook = new XSSFWorkbook();

        //添加workSheet ，不添加打开会报错
        XSSFSheet sheet1 = workbook.createSheet("sheet1");

        //新建文件
        FileOutputStream out = null;

        for (int i = 0; i < Data.getInstance().getTranData().length; i++) {
            XSSFRow row = workbook.getSheet("sheet1").createRow(i);
            for (int j = 0; j < Data.getInstance().getTranData()[0].length; j++) {
                row.createCell(j).setCellValue((String) Data.getInstance().getTranData()[i][j]);
            }
        }
        out = new FileOutputStream(path);
        workbook.write(out);
        out.close();
    }

}
