package factory;

import strategy.BLH_XYZ;
import strategy.BL_XY;
import strategy.XYZ_BLH;
import strategy.XY_BL;

public class DataFormFactory {

    public static int colNumProduce(String type) {
        if ("BLH -> XYZ".equals(type)) {
            return 3;
        } else if ("XYZ -> BLH".equals(type)) {
            return 3;
        }
        else if ("XY -> BL".equals(type)) {
            return 2;
        }
        else if ("BL -> XY".equals(type)) {
            return 2;
        }
        else if ("求四参数".equals(type)){
            return 2;
        }
        else if ("求七参数".equals(type)){
            return 3;
        }
        else {
            System.out.println("请输入正确的类型!");
            System.out.println(type);
            return -1;
        }
    }

    public static boolean isTrans(String type){
        if ("BLH -> XYZ".equals(type)) {
            return true;
        } else if ("XYZ -> BLH".equals(type)) {
            return true;
        }
        else if ("XY -> BL".equals(type)) {
            return true;
        }
        else if ("BL -> XY".equals(type)) {
            return true;
        }
        else if ("求四参数".equals(type)){
            return false;
        }
        else if ("求七参数".equals(type)){
            return false;
        }
        else {
            System.out.println("请输入正确的类型!");
            System.out.println(type);
            return false;
        }
    }
}
