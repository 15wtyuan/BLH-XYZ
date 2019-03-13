package strategy;

import bean.Point3d;
import singleton.Choice;
import tools.Trans;

import java.text.DecimalFormat;
import java.util.List;

public class XYZ_BLH extends AbstractCalculator implements ICalculator{
    @Override
    public String[] calculate(String[] str) {
        String[] result = {"", "", ""};
        try {
            double Bt;
            Bt = Choice.getInstance().getCoordinateSystem().RA * Math.sqrt(1 - Choice.getInstance().getCoordinateSystem().EPF);

            double L, x, y, Z;
            String Lstr, Bstr;

            x = Double.valueOf(str[0]);
            y = Double.valueOf(str[1]);
            Z = Double.valueOf(str[2]);
            L = Math.atan(y / x) * 180 / Math.PI;
            if (L < 0) {
                L = L + 180;
            }
            Trans trans = new Trans();
            Lstr = trans.DegToDMS(L);
            result[0] = Lstr;

            double N0, H0, B0;
            double N1, H1, B1;

            N0 = Choice.getInstance().getCoordinateSystem().RA;
            H0 = Math.sqrt(x * x + y * y + Z * Z) - Math.sqrt(Choice.getInstance().getCoordinateSystem().RA * Bt);
            double bz1;
            bz1 = Z / Math.sqrt(x * x + y * y);
            B0 = Math.atan(bz1 * (1 - Choice.getInstance().getCoordinateSystem().EPF * N0 / (N0 + H0)));
            int i;
            B1 = B0;

            do {
                B0 = B1;
                N1 = Choice.getInstance().getCoordinateSystem().RA / Math.sqrt(1 - Choice.getInstance().getCoordinateSystem().EPF * Math.sin(B0) * Math.sin(B0));
                H1 = Math.sqrt(x * x + y * y) / Math.cos(B0) - N1;
                B1 = Math.atan(bz1 / (1 - (Choice.getInstance().getCoordinateSystem().EPF * N1) / (N1 + H1)));
            } while (Math.abs(B1 - B0) >= Math.pow(10, -16));
            result[1] = trans.DegToDMS(B1 * 180 / Math.PI);
            DecimalFormat df = new DecimalFormat("#.0000");
            result[2] = df.format(H1);
        } catch (Exception e) {
            System.out.println("转换出错！");
            //e.printStackTrace();
        }
        return result;
    }
}
