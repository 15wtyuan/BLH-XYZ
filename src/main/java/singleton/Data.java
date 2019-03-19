package singleton;

import factory.CalculatorFactory;
import strategy.ICalculator;

public class Data {
    private static Data ourInstance = new Data();

    public static Data getInstance() {
        return ourInstance;
    }

    private Object[][] originalData;
    private Object[][] tranData;
    private String[] tableHeader1 = {"", "", ""};
    private String[] tableHeader2 = {"", "", ""};

    private Data() {
        originalData = new Object[0][3];
        tranData = new Object[0][3];
    }

    public Object[][] getOriginalData() {
        return originalData;
    }

    public void setOriginalData(Object[][] originalData) {
        this.originalData = originalData;
        if (Choice.getInstance().getMethod()!=null){
            calculator();
            getTableHeader(Choice.getInstance().getMethod());
        }
    }

    private void getTableHeader(String type) {
        if ("BLH -> XYZ".equals(type)) {
            tableHeader1 = new String[]{"B", "L", "H"};
            tableHeader2 = new String[]{"X", "Y", "Z"};
        } else if ("XYZ -> BLH".equals(type)) {
            tableHeader1 = new String[]{"X", "Y", "Z"};
            tableHeader2 = new String[]{"B", "L", "H"};
        } else if ("XY -> BL".equals(type)) {
            tableHeader1 = new String[]{"X", "Y"};
            tableHeader2 = new String[]{"B", "L"};
        } else if ("BL -> XY".equals(type)) {
            tableHeader1 = new String[]{"B", "L"};
            tableHeader2 = new String[]{"X", "Y"};
        } else {
            tableHeader1 = new  String[]{"", "", ""};
            tableHeader2 = new  String[]{"", "", ""};
        }
    }

    private void calculator() {
        tranData = new Object[originalData.length][originalData[0].length];
        for (int i=0;i<originalData.length;i++){
            for (int j=0;j<originalData[i].length;j++){
                tranData[i][j] = "";
            }
        }
        ICalculator calculator = CalculatorFactory.produce(Choice.getInstance().getMethod());
        for (int i=0;i<originalData.length;i++){
            String[] temp = new String[originalData[i].length];
            for (int j=0;j<originalData[i].length;j++){
                temp[j] = (String) originalData[i][j];
            }
            try {
                String[] result = calculator.calculate(temp);
                tranData[i] = result;
            }catch (NumberFormatException e){
                System.out.println("第"+i+"行转换出现错误");
            }
        }
    }


    public Object[][] getTranData() {
        return tranData;
    }

    public void setTranData(Object[][] tranData) {
        this.tranData = tranData;
    }

    public String[] getTableHeader1() {
        return tableHeader1;
    }

    public void setTableHeader1(String[] tableHeader1) {
        this.tableHeader1 = tableHeader1;
    }

    public String[] getTableHeader2() {
        return tableHeader2;
    }

    public void setTableHeader2(String[] tableHeader2) {
        this.tableHeader2 = tableHeader2;
    }
}
