package strategy;

import bean.Point3d;
import singleton.Choice;

import java.text.DecimalFormat;

public class Para7Convert extends AbstractCalculator implements ICalculator  {
    @Override
    public String[] calculate(String[] str) {
        String[] result = {"", "", ""};
        try {
            Point3d point3d = new Point3d(Double.valueOf(str[0]),Double.valueOf(str[1]),Double.valueOf(str[2]));
            Point3d temp = PramSCals.Para7Convert(point3d,Choice.getInstance().getResultParameters7());
            DecimalFormat df = new DecimalFormat("#.0000");
            result[0] = df.format(temp.X);
            result[1] = df.format(temp.Y);
            result[2] = df.format(temp.Z);
        } catch (Exception e) {
            System.out.println("转换出错！");
            System.out.println(str[0]+" "+str[0]+" "+str[0]);
//            e.printStackTrace();
        }
        return result;
    }
}
