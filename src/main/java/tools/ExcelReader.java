package tools;

import factory.DataFormFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import singleton.Choice;
import singleton.Data;

public class ExcelReader {

    public static void read(String ExcelPath) throws Exception {

        XSSFWorkbook workbook = new XSSFWorkbook(ExcelPath);
        XSSFSheet sheet = workbook.getSheet("Sheet1");

        //获取Excel文件中的所有行数
        int rows = sheet.getLastRowNum();
        //System.out.println(rows);

        Object[][] originalData = new Object[rows][DataFormFactory.colNumProduce(Choice.getInstance().getMethod())];

        //遍历行
        //List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < rows; i++) {
            //读取左上角单元格
            XSSFRow row = sheet.getRow(i + 1);

            //行不为空
            if (row != null) {
                //Map<String, Object> map = new LinkedHashMap<String, Object>();

                //获取Excel文件中所有的列
                int cells = row.getPhysicalNumberOfCells();

                for (int j = 0; j < DataFormFactory.colNumProduce(Choice.getInstance().getMethod()); j++) {
                    XSSFCell cell = row.getCell(j);
                    String value = getValue(cell);
                    originalData[i][j] = value;
                    //System.out.println(value);
                }
            }
        }
        Data.getInstance().setOriginalData(originalData);
    }

    //    在每一行中，通过列名来获取对应列的值
    private static String getValue(XSSFCell xssfCell) {
        if (xssfCell == null) {
            return "";
        }
        //返回布尔类型的值
        else if (xssfCell.getCellType() == xssfCell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(xssfCell.getBooleanCellValue());
        } else if (xssfCell.getCellType() == xssfCell.CELL_TYPE_NUMERIC) {
            //返回数值类型的值
            return String.valueOf(xssfCell.getNumericCellValue());
        } else {
            //返回字符串类型的值
            return String.valueOf(xssfCell.getStringCellValue());
        }
    }

}
