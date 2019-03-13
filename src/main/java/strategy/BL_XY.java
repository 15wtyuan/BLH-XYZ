package strategy;

import bean.Point3d;
import singleton.Choice;
import tools.Trans;

import java.text.DecimalFormat;

public class BL_XY extends AbstractCalculator implements ICalculator {
    @Override
    public String[] calculate(String[] str) {

        String[] result = {"", ""};

        try {
            double Bd, Bf, Bm, Ld, Lf, Lm;
            String[] tmpB, tmpL;
            tmpL = str[0].split(",");
            tmpB = str[1].split(",");
            Ld = Double.valueOf(tmpL[0]);
            Lf = Double.valueOf(tmpL[1]);
            Lm = Double.valueOf(tmpL[2]);

            Point3d bl = new Point3d(0, 0,0);
            bl.Y = (Ld + (Lf / 60) + (Lm / 3600));

            Bd = Double.valueOf(tmpB[0]);
            Bf = Double.valueOf(tmpB[1]);
            Bm = Double.valueOf(tmpB[2]);

            bl.X = (Bd + (Bf / 60) + (Bm / 3600));

            Trans trans = new Trans();
            Point3d xy = new Point3d(0, 0,0);
            xy = trans.BLToxy(bl, Choice.getInstance().getCentralMeridian(), Choice.getInstance().getCoordinateSystem().RA, Choice.getInstance().getCoordinateSystem().RB, xy);
            DecimalFormat df = new DecimalFormat("#.000");
            result[0] = df.format(xy.X);
            result[1] = df.format(xy.Y);
            System.out.println(result[0]);
            System.out.println(result[1]);
        } catch (Exception e) {
            System.out.println("转换出错！");
            //e.printStackTrace();
        }
        return result;
    }
}
