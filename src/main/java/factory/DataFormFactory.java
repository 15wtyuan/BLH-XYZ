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
            return 3;
        }
        else if ("BL -> XY".equals(type)) {
            return 3;
        }
        else {
            System.out.println("请输入正确的类型!");
            return -1;
        }
    }
}
