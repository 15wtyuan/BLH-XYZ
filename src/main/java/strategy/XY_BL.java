package strategy;

import bean.Point3d;
import singleton.Choice;

public class XY_BL extends AbstractCalculator implements ICalculator {
    @Override
    public String[] calculate(String[] str) {
        String[] result = {"", ""};
        try {
            Point3d xy = new Point3d(0, 0,0);
            double[] bl ;
            xy.X = Double.valueOf(str[0]);
            xy.Y = Double.valueOf(str[1]);
            bl = gaussXYtoBL(xy.X,xy.Y);
            result[0] = DegToDMS(bl[0]);
            result[1] = DegToDMS(bl[1]);
        } catch (Exception e) {
            System.out.println("转换出错！");
            //e.printStackTrace();
        }
        return result;
    }

    private double[] gaussXYtoBL(double X, double Y) {
        int ProjNo;
        int ZoneWide;
        double[] output = new double[2];
        double longitude1, latitude1, longitude0, X0, Y0, xval, yval;// latitude0,
        double e1, e2, f, a, ee, NN, T, C, M, D, R, u, fai, iPI;
        iPI = 0.0174532925199433; // //3.1415926535898/180.0;

        a = Choice.getInstance().getCoordinateSystem().RA;
        f = 1.0/Choice.getInstance().getCoordinateSystem().Flat;

        ZoneWide = 6;
        ProjNo = (int) (X / 1000000);
        longitude0 = (ProjNo - 1) * ZoneWide + ZoneWide / 2;
//        longitude0 = Choice.getInstance().getCentralMeridian();
        longitude0 = longitude0 * iPI; // 中央经线

        X0 = ProjNo * 1000000 + 500000;
        Y0 = 0;
        xval = X - X0;
        yval = Y - Y0; // 带内大地坐标
        e2 = 2 * f - f * f;
        e1 = (1.0 - Math.sqrt(1 - e2)) / (1.0 + Math.sqrt(1 - e2));
        ee = e2 / (1 - e2);
        M = yval;
        u = M / (a * (1 - e2 / 4 - 3 * e2 * e2 / 64 - 5 * e2 * e2 * e2 / 256));
        fai = u + (3 * e1 / 2 - 27 * e1 * e1 * e1 / 32) * Math.sin(2 * u) + (21 * e1 * e1 / 16 - 55 * e1 * e1 * e1 * e1 / 32) * Math.sin(4 * u) + (151 * e1 * e1 * e1 / 96) * Math.sin(6 * u) + (1097 * e1 * e1 * e1 * e1 / 512) * Math.sin(8 * u);
        C = ee * Math.cos(fai) * Math.cos(fai);
        T = Math.tan(fai) * Math.tan(fai);
        NN = a / Math.sqrt(1.0 - e2 * Math.sin(fai) * Math.sin(fai));
        R = a * (1 - e2) / Math.sqrt((1 - e2 * Math.sin(fai) * Math.sin(fai)) * (1 - e2 * Math.sin(fai) * Math.sin(fai)) * (1 - e2 * Math.sin(fai) * Math.sin(fai)));
        D = xval / NN;
        // 计算经度(Longitude) 纬度(Latitude)
        longitude1 = longitude0 + (D - (1 + 2 * T + C) * D * D * D / 6 + (5 - 2 * C + 28 * T - 3 * C * C + 8 * ee + 24 * T * T) * D * D * D * D * D / 120) / Math.cos(fai);
        latitude1 = fai - (NN * Math.tan(fai) / R) * (D * D / 2 - (5 + 3 * T + 10 * C - 4 * C * C - 9 * ee) * D * D * D * D / 24 + (61 + 90 * T + 298 * C + 45 * T * T - 256 * ee - 3 * C * C) * D * D * D * D * D * D / 720);
        // 转换为度 DD
        output[0] = longitude1 / iPI;
        output[1] = latitude1 / iPI;
        return output;
    }
}
