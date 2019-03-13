package strategy;

import bean.Point3d;
import singleton.Choice;

import java.text.DecimalFormat;
import java.util.List;

public class BLH_XYZ extends AbstractCalculator implements ICalculator {
    @Override
    public String[] calculate(String[] str) {
        String[] result = {"", "", ""};
        try {
            double N, Bd, Bf, Bm, Ld, Lf, Lm;
            String[] tmpB, tmpL;

            tmpL = str[0].split(",");
            tmpB = str[1].split(",");
            Ld = Double.valueOf(tmpL[0]);
            Lf = Double.valueOf(tmpL[1]);
            Lm = Double.valueOf(tmpL[2]);

            Point3d blh = new Point3d(0, 0, 0);
            blh.Y = (Ld + (Lf / 60) + (Lm / 3600)) * Math.PI / 180;

            Bd = Double.valueOf(tmpB[0]);
            Bf = Double.valueOf(tmpB[1]);
            Bm = Double.valueOf(tmpB[2]);
            blh.X = (Bd + (Bf / 60) + (Bm / 3600)) * Math.PI / 180;

            N = Choice.getInstance().getCoordinateSystem().RA / Math.sqrt(1 - Choice.getInstance().getCoordinateSystem().EPF * Math.pow(Math.sin(blh.X), 2));
            blh.Z = Double.valueOf(str[2]);
            Point3d xyz = new Point3d(0, 0, 0);
            xyz.X = (N + blh.Z) * Math.cos(blh.X) * Math.cos(blh.Y);
            xyz.Y = (N + blh.Z) * Math.cos(blh.X) * Math.sin(blh.Y);
            xyz.Z = (N * (1 - Choice.getInstance().getCoordinateSystem().EPF) + blh.Z) * Math.sin(blh.X);

            DecimalFormat df = new DecimalFormat("#.0000");
            result[0] = df.format(xyz.X);
            result[1] = df.format(xyz.Y);
            result[2] = df.format(xyz.Z);
        } catch (Exception e) {
            System.out.println("转换出错！");
            //e.printStackTrace();
        }
        return result;
    }
}
