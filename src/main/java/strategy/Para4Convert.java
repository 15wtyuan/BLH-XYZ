package strategy;

import bean.Point3d;
import singleton.Choice;

import java.text.DecimalFormat;

public class Para4Convert extends AbstractCalculator implements ICalculator  {
    @Override
    public String[] calculate(String[] str) {
        String[] result = {"", ""};
        try {
            Point3d point3d = new Point3d(Double.valueOf(str[0]),Double.valueOf(str[1]),0);
            Point3d temp = PramSCals.Para4Convert(point3d,Choice.getInstance().getResultParameters4());
            DecimalFormat df = new DecimalFormat("#.0000");
            result[0] = df.format(temp.X);
            result[1] = df.format(temp.Y);
        } catch (Exception e) {
            System.out.println("转换出错！");
            //e.printStackTrace();
        }
        return result;
    }
}