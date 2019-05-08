package singleton;

import bean.*;
import factory.CalculatorFactory;
import strategy.ICalculator;
import strategy.PramSCals;
import java.util.ArrayList;

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

    public void setAllData(Object[][] originalData,Object[][] tranData){
        this.originalData = originalData;
        this.tranData = tranData;
        if (Choice.getInstance().getMethod()!=null){
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
        }else if ("求四参数".equals(type)){
            tableHeader1 = new String[]{"X", "Y"};
            tableHeader2 = new String[]{"X", "Y"};
        }
        else if ("求七参数".equals(type)){
            tableHeader1 = new String[]{"X", "Y", "Z"};
            tableHeader2 = new String[]{"X", "Y", "Z"};
        } else {
            tableHeader1 = new  String[]{"", "", ""};
            tableHeader2 = new  String[]{"", "", ""};
        }
    }

    public void calculator() {
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

    public ResultParameters4 getResultParameters4(){
        Object[][] data1 = originalData;
        Object[][] data2 = tranData;
        ArrayList<Point3d> list1 = new ArrayList<>();
        ArrayList<Point3d> list2 = new ArrayList<>();
        for (int i = 0; i < data1.length; i++) {
            try {
                Point3d temp1 = new Point3d(Double.valueOf((String) data1[i][0]), Double.valueOf((String) data1[i][1]),0d);
                Point3d temp2 = new Point3d(Double.valueOf((String) data2[i][0]), Double.valueOf((String) data2[i][1]),0d);
                list1.add(temp1);
                list2.add(temp2);
            } catch (Exception e) {
                System.out.println("转换四参数出错！");
            }
        }
//        Point2d[] p1 = new Point2d[list1.size()];
//        Point2d[] p2 = new Point2d[list2.size()];
//
//        for (int i = 0; i < list1.size(); i++) {
//            p1[i] = list1.get(i);
//            p2[i] = list2.get(i);
//        }

        ResultParameters4 resultParameters4 = PramSCals.Calc4Para(list1, list2);
        return resultParameters4;
    }

    public ResultParameters7 getResultParameters7(){
        Object[][] data1 = originalData;
        Object[][] data2 = tranData;
        ArrayList<Point3d> list1 = new ArrayList<>();
        ArrayList<Point3d> list2 = new ArrayList<>();
        for (int i = 0; i < data1.length; i++) {
            try {
                Point3d temp1 = new Point3d(Double.valueOf((String) data1[i][0]), Double.valueOf((String) data1[i][1]), Double.valueOf((String) data1[i][2]));
                Point3d temp2 = new Point3d(Double.valueOf((String) data2[i][0]), Double.valueOf((String) data2[i][1]), Double.valueOf((String) data2[i][2]));
                list1.add(temp1);
                list2.add(temp2);
            } catch (Exception e) {
                System.out.println("转换七参数出错！");
            }
        }
//        System.out.println(list1.size());
//        System.out.println(list2.size());
//        Point3d[] p1 = new Point3d[list1.size()];
//        Point3d[] p2 = new Point3d[list2.size()];
//
//        for (int i = 0; i < list1.size(); i++) {
//            p1[i] = list1.get(i);
//            p2[i] = list2.get(i);
//        }
        ResultParameters7 resultParameters7 = PramSCals.Calc7Para(list1, list2);
//        ResultParameters7 resultParameters7 = PramSCals.Canshu7(p1, p2,list1.size());
        return resultParameters7;
    }


    public void generateTestData(int num)
    {
        Object[][] testData = new Object[num][3];
        for (int i=0;i<num;i++)
        {
            double X = -(2620000 + (int)(Math.random()*10000)) + ((double)((int)(Math.random()*100000000)))/100000000;
            double Y = (5230000 + (int)(Math.random()*10000)) + ((double)((int)(Math.random()*100000000)))/100000000;
            double Z = (2510000 + (int)(Math.random()*10000)) + ((double)((int)(Math.random()*100000000)))/100000000;
            testData[i][0] = String.valueOf(X);
            testData[i][1] = String.valueOf(Y);
            testData[i][2] =String.valueOf(Z);
        }
        Choice.getInstance().setCoordinateSystem(new CoordinateSystem("1954年北京坐标系",6378245,298.3));
        Choice.getInstance().setCentralMeridian(117.0);
        Choice.getInstance().setMethod("XYZ -> BLH");
        setOriginalData(testData);
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
