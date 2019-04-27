package strategy;

import java.text.DecimalFormat;

public abstract class AbstractCalculator {
    // TODO: 2019/3/7
    public String DegToDMS(double A) {
        int D, M;
        double SS;
        A = A + Math.pow(10, -8);
        D = (int) A;
        M = (int) ((A - (int) A) * 60);
        SS = (A - D - M / 60) * 3600;
        DecimalFormat df = new DecimalFormat("#.0000");
        String s = df.format(SS);
        String ss[] = s.split("\\.");
        int is = Integer.valueOf(ss[0]);
        is = is % 100;
        s = String.valueOf(is) + "." + ss[1];
        return String.valueOf(D) + "," + String.valueOf(M) + "," + s;
    }
}
