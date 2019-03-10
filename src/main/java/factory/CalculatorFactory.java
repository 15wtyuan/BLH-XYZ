package factory;

import strategy.*;

public class CalculatorFactory {
    private static CalculatorFactory ourInstance = new CalculatorFactory();

    public static CalculatorFactory getInstance() {
        return ourInstance;
    }

    private CalculatorFactory() {
    }

    public ICalculator produce(String type) {
        if ("BLH -> XYZ".equals(type)) {
            return new BLH_XYZ();
        } else if ("XYZ -> BLH".equals(type)) {
            return new XYZ_BLH();
        }
        else if ("XY -> BL".equals(type)) {
            return new XY_BL();
        }
        else if ("BL -> XY".equals(type)) {
            return new BL_XY();
        }
        else {
            System.out.println("请输入正确的类型!");
            return null;
        }
    }
}