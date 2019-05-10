package strategy;

import bean.Point3d;
import singleton.Choice;

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

            double[] xy ;
            xy = BLtoXY(bl.Y,bl.X);
            DecimalFormat df = new DecimalFormat("#.000");
            result[0] = df.format(xy[0]);
            result[1] = df.format(xy[1]);
            System.out.println(result[0]);
            System.out.println(result[1]);
        } catch (Exception e) {
            System.out.println("转换出错！");
            e.printStackTrace();
        }
        return result;
    }

    private double[] BLtoXY(double longitude, double latitude) {
        int ProjNo = 0;
        int ZoneWide = 6;
        double longitude1, latitude1, longitude0, X0, Y0, xval, yval;
        double a, f, e2, ee, NN, T, C, A, M, iPI;
        iPI = 0.0174532925199433;// 3.1415926535898/180.0;

        a = Choice.getInstance().getCoordinateSystem().RA;
        f = 1.0/Choice.getInstance().getCoordinateSystem().Flat;

        ProjNo = (int) (longitude / ZoneWide);
        longitude0 = ProjNo * ZoneWide + ZoneWide / 2;
//        longitude0 = Choice.getInstance().getCentralMeridian();
        longitude0 = longitude0 * iPI;
        longitude1 = longitude * iPI;
        latitude1 = latitude * iPI;

        e2 = 2 * f - f * f;
        ee = e2 * (1.0 - e2);
        NN = a / Math.sqrt(1.0 - e2 * Math.sin(latitude1) * Math.sin(latitude1));
        T = Math.tan(latitude1) * Math.tan(latitude1);
        C = ee * Math.cos(latitude1) * Math.cos(latitude1);
        A = (longitude1 - longitude0) * Math.cos(latitude1);
        M = a * ((1 - e2 / 4 - 3 * e2 * e2 / 64 - 5 * e2 * e2 * e2 / 256) * latitude1 - (3 * e2 / 8 + 3 * e2 * e2 / 32 + 45 * e2 * e2 * e2 / 1024) * Math.sin(2 * latitude1) + (15 * e2 * e2 / 256 + 45 * e2 * e2 * e2 / 1024) * Math.sin(4 * latitude1) - (35 * e2 * e2 * e2 / 3072) * Math.sin(6 * latitude1));
        xval = NN * (A + (1 - T + C) * A * A * A / 6 + (5 - 18 * T + T * T + 72 * C - 58 * ee) * A * A * A * A * A / 120);
        yval = M + NN * Math.tan(latitude1) * (A * A / 2 + (5 - T + 9 * C + 4 * C * C) * A * A * A * A / 24 + (61 - 58 * T + T * T + 600 * C - 330 * ee) * A * A * A * A * A * A / 720);
        X0 = 1000000 * (ProjNo + 1) + 500000;
        Y0 = 0;
        xval = xval + X0;
        yval = yval + Y0;
        return new double[]{xval, yval};
    }
}
