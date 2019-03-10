package tools;

import bean.Point3d;

import java.text.DecimalFormat;

public class Trans {

    private static final double PI = 3.14159265358979;

    public Point3d BLHtoXYZ(Point3d blh, double RA, double RB, Point3d xyz) {
        double n, epf;
        epf = 1 - Math.pow(RB, 2) / Math.pow(RA, 2);
        n = RA / Math.sqrt(1 - epf * Math.pow(Math.sin(blh.X), 2));
        xyz.X = (n + blh.Z) * Math.cos(blh.X) * Math.cos(blh.Y);
        xyz.Y = (n + blh.Z) * Math.cos(blh.X) * Math.sin(blh.Y);
        xyz.Z = (n * (1 - epf) + blh.Z) * Math.sin(blh.X);
        return xyz;
    }

    public Point3d XYZtoBLH(Point3d xyz, double RA, double RB, Point3d blh) {
        double bt, n, epf;

        epf = 1 - Math.pow(RB, 2) / Math.pow(RA, 2);
        bt = RA * Math.sqrt(1 - epf);

        String lstr, bstr;

        blh.Y = Math.atan(xyz.Y / xyz.X) * 180 / PI;
        if (blh.Y < 0) blh.Y = blh.Y + 180;

        double n0, h0, b0;
        double n1, h1, b1;

        n0 = RA;
        h0 = Math.sqrt(xyz.X * xyz.X + xyz.Y * xyz.Y + xyz.Z * xyz.Z) - Math.sqrt(RA * bt);
        double bz1;
        bz1 = xyz.Z / Math.sqrt(xyz.X * xyz.X + xyz.Y * xyz.Y);
        b0 = Math.atan(bz1 * (1 - epf * n0 / (n0 + h0)));
        b1 = b0;
        do {
            b0 = b1;
            n1 = RA / Math.sqrt(1 - epf * Math.sin(b0) * Math.sin(b0));
            h1 = Math.sqrt(xyz.X * xyz.X + xyz.Y * xyz.Y) / Math.cos(b0) - n1;
            b1 = Math.atan(bz1 / (1 - (epf * n1) / (n1 + h1)));
        } while (Math.abs(b1 - b0) >= Math.pow(10, -16));

        blh.X = b1 * 180 / PI;
        blh.Z = h1;
        return blh;
    }

    public Point3d xyToBL(Point3d xy, double L0, double RA, double RB, Point3d bl) {
        double R, xx, i, A, L;
        double M, Et, N, t, V, W, C;
        double e, e1, P, Q, f;
        double yy;

        f = (RA - RB) / RA;

        C = Math.pow(RA, 2) / RB;
        e = Math.sqrt((Math.pow(RA, 2) - Math.pow(RB, 2)) / Math.pow(RA, 2));
        e1 = Math.sqrt((Math.pow(RA, 2) - Math.pow(RB, 2)) / Math.pow(RB, 2));
        P = 206264.81;
        yy = 500000.0;
        xy.Y = xy.Y - yy;
//        '  Debug.Print 1 / f, e ^ 2, e1 ^ 2
        double Ap, Bp, CP, dp, Ep, Fp, Gp;

        Ap = 1.0 + 3.0 / 4.0 * Math.pow(e, 2) + 45.0 / 64.0 * Math.pow(e, 4) + 175.0 / 256.0 * Math.pow(e, 6) + 11025.0 / 16384.0 * Math.pow(e, 8) + 43659.0 / 65536.0 * Math.pow(e, 10) + 693693.0 / 1048576.0 * Math.pow(e, 12);
        Bp = 3.0 / 8.0 * Math.pow(e, 2) + 15.0 / 32.0 * Math.pow(e, 4) + 525.0 / 1024.0 * Math.pow(e, 6) + 2205.0 / 4096.0 * Math.pow(e, 8) + 72765.0 / 131072.0 * Math.pow(e, 10) + 297297.0 / 524288.0 * Math.pow(e, 12);
        CP = 15.0 / 256.0 * Math.pow(e, 4) + 105.0 / 1024.0 * Math.pow(e, 6) + 2205.0 / 16384.0 * Math.pow(e, 8) + 10395.0 / 65536.0 * Math.pow(e, 10) + 1486485.0 / 8388608.0 * Math.pow(e, 12);
        dp = 35.0 / 3072.0 * Math.pow(e, 6) + 105.0 / 4096.0 * Math.pow(e, 8) + 10395.0 / 262144.0 * Math.pow(e, 10) + 55055.0 / 1048576.0 * Math.pow(e, 12);
        Ep = 315.0 / 131072.0 * Math.pow(e, 8) + 3465.0 / 524288.0 * Math.pow(e, 10) + 99099.0 / 8388608.0 * Math.pow(e, 12);
        Fp = 693.0 / 1310720.0 * Math.pow(e, 10) + 9009.0 / 5242880.0 * Math.pow(e, 12);
        Gp = 1001.0 / 8388608.0 * Math.pow(e, 12);

        double Bf[];
        double a1, Bfo;
        double Nf, Tf, ETf, Vf;
        int j;
        double B0, B1;
        double FB, FBp;
        i = 0;
        B0 = xy.X / (RA * (1 - Math.pow(e, 2)) * Ap) * 180 / PI;
        FB = RA * (1 - Math.pow(e, 2)) * (Ap * B0 * PI / 180 - Bp * Math.sin(2 * B0 * PI / 180) + CP * Math.sin(4 * B0 * PI / 180) - dp * Math.sin(6 * B0 * PI / 180) + Ep * Math.sin(8 * B0 * PI / 180) - Fp * Math.sin(10 * B0 * PI / 180) + Gp * Math.sin(12 * B0 * PI / 180));
        FBp = RA * (1 - Math.pow(e, 2)) * (Ap - 2 * Bp * Math.cos(2 * B0 * PI / 180) + 4 * CP * Math.cos(4 * B0 * PI / 180) - 6 * dp * Math.cos(6 * B0 * PI / 180) + 8 * Ep * Math.cos(8 * B0 * PI / 180) - 10 * Fp * Math.cos(10 * B0 * PI / 180) + 12 * Gp * Math.cos(12 * B0 * PI / 180));
        B1 = B0 + (xy.X - FB) / FBp * 180 / PI;
        while (Math.abs(B1 - B0) > 0.0001 / 3600) {
            B0 = B1;
            FB = RA * (1 - Math.pow(e, 2)) * (Ap * B0 * PI / 180 - Bp * Math.sin(2 * B0 * PI / 180) + CP * Math.sin(4 * B0 * PI / 180) - dp * Math.sin(6 * B0 * PI / 180) + Ep * Math.sin(8 * B0 * PI / 180) - Fp * Math.sin(10 * B0 * PI / 180) + Gp * Math.sin(12 * B0 * PI / 180));
            FBp = RA * (1 - Math.pow(e, 2)) * (Ap - 2 * Bp * Math.cos(2 * B0 * PI / 180) + 4 * CP * Math.cos(4 * B0 * PI / 180) - 6 * dp * Math.cos(6 * B0 * PI / 180) + 8 * Ep * Math.cos(8 * B0 * PI / 180) - 10 * Fp * Math.cos(10 * B0 * PI / 180) + 12 * Gp * Math.cos(12 * B0 * PI / 180));

            B1 = B0 + (xy.X - FB) / FBp * 180 / PI;
        }
        B0 = B1;
        t = Math.tan(B0 * PI / 180);
        Et = e1 * Math.cos(B0 * PI / 180);
        N = RA / Math.sqrt(1 - Math.pow(e, 2) * Math.pow(Math.sin(B0 * PI / 180), 2));

        V = C / N;
        M = C / Math.pow(V, 3);

        a1 = Math.pow((xy.Y / N), 2) / 2 - (5 + 3 * Math.pow(t, 2) + Math.pow(Et, 2) - 9 * Math.pow(Et, 2) * Math.pow(t, 2)) * Math.pow((xy.Y / N), 4) / 24 + (61 + 90 * Math.pow(t, 2) + 45 * Math.pow(t, 4)) * Math.pow((xy.Y / N), 6) / 720;
        bl.X = B0 - N * t / M * a1 * P / 3600;//* 180 / Pi

        bl.Y = (1 - (1 + 2 * Math.pow(t, 2) + Math.pow(Et, 2)) * Math.pow((xy.Y / N), 2) / 6 + (5 + 28 * Math.pow(t, 2) + 24 * Math.pow(t, 4) + 6 * Math.pow(Et, 2) + 8 * Math.pow(Et, 2) * Math.pow(t, 2)) * Math.pow((xy.Y / N), 4) / 120) / Math.cos(B0 * PI / 180) * xy.Y / N * P;
        bl.Y = bl.Y / 3600 + L0;
        xy.Y = xy.Y + yy;

//        '  Debug.Print x & ", " & y + YY & ", " & FmtDMS(B, 4) & ", " & FmtDMS(LL / 3600 + L0, 4)
        return bl;
    }

    public Point3d BLToxy(Point3d bl, double L0, double RA, double RB, Point3d xy) {
        //公式2
        double R, xx, i, L;
        int A;
        double M, Et, N, t, V, W, C;
        double e, e1, P, Q;
        double yy;

        C = Math.pow(RA, 2) / RB;
        e = Math.sqrt((Math.pow(RA, 2) - Math.pow(RB, 2)) / Math.pow(RA, 2));
        e1 = Math.sqrt((Math.pow(RA, 2) - Math.pow(RB, 2)) / Math.pow(RB, 2));
        Et = e1 * Math.cos(bl.X * Math.PI / 180);
        V = Math.sqrt(1 + Math.pow(Et, 2));
        W = V * Math.sqrt(1 - Math.pow(e, 2));
        N = RA / Math.sqrt(1 - Math.pow(e, 2) * Math.pow(Math.sin(bl.X * Math.PI / 180), 2));
        R = N / V;
        M = C / Math.pow(V, 3);
        P = 206264.81;
        yy = 500000;
        t = Math.tan(bl.X * Math.PI / 180);


        L = bl.Y - L0;
        double Ap, Bp, CP, dp, Ep, Fp, Gp;

        Ap = 1.0 + 3.0 / 4.0 * Math.pow(e, 2) + 45.0 / 64.0 * Math.pow(e, 4) + 175.0 / 256.0 * Math.pow(e, 6) + 11025.0 / 16384.0 * Math.pow(e, 8) + 43659.0 / 65536.0 * Math.pow(e, 10) + 693693.0 / 1048576.0 * Math.pow(e, 12);
        Bp = 3.0 / 8.0 * Math.pow(e, 2) + 15.0 / 32.0 * Math.pow(e, 4) + 525.0 / 1024.0 * Math.pow(e, 6) + 2205.0 / 4096.0 * Math.pow(e, 8) + 72765.0 / 131072.0 * Math.pow(e, 10) + 297297.0 / 524288.0 * Math.pow(e, 12);
        CP = 15.0 / 256.0 * Math.pow(e, 4) + 105.0 / 1024.0 * Math.pow(e, 6) + 2205.0 / 16384.0 * Math.pow(e, 8) + 10395.0 / 65536.0 * Math.pow(e, 10) + 1486485.0 / 8388608.0 * Math.pow(e, 12);
        dp = 35.0 / 3072.0 * Math.pow(e, 6) + 105.0 / 4096.0 * Math.pow(e, 8) + 10395.0 / 262144.0 * Math.pow(e, 10) + 55055.0 / 1048576.0 * Math.pow(e, 12);
        Ep = 315.0 / 131072.0 * Math.pow(e, 8) + 3465.0 / 524288.0 * Math.pow(e, 10) + 99099.0 / 8388608.0 * Math.pow(e, 12);
        Fp = 693.0 / 1310720.0 * Math.pow(e, 10) + 9009.0 / 5242880.0 * Math.pow(e, 12);
        Gp = 1001.0 / 8388608.0 * Math.pow(e, 12);

        Q = Math.cos(bl.X * Math.PI / 180) * L * Math.PI / 180;
        xx = RA * (1 - Math.pow(e, 2)) * (Ap * bl.X * Math.PI / 180 - Bp * Math.sin(2 * bl.X * Math.PI / 180) + CP * Math.sin(4 * bl.X * Math.PI / 180) - dp * Math.sin(6 * bl.X * Math.PI / 180) + Ep * Math.sin(8 * bl.X * Math.PI / 180) - Fp * Math.sin(10 * bl.X * Math.PI / 180) + Gp * Math.sin(12 * bl.X * Math.PI / 180));
        xy.X = xx + N * t * (Math.pow(Q, 2) / 2 + (5 - Math.pow(t, 2) + 9 * Math.pow(Et, 2) + 4 * Math.pow(Et, 4)) * Math.pow(Q, 4) / 24 + (61 - 58 * Math.pow(t, 2) + Math.pow(t, 4)) * Math.pow(Q, 6) / 720);
        xy.Y = yy + N * (Q + (1 - Math.pow(t, 2) + Math.pow(Et, 2)) * Math.pow(Q, 3) / 6 + (5 - 18 * Math.pow(t, 2) + Math.pow(t, 4) + 14 * Math.pow(Et, 4) - 58 * Math.pow(Et, 2) * Math.pow(t, 2)) * Math.pow(Q, 5) / 120);
        //  Debug.Print FmtNum(X, 3), FmtNum(Y, 3)
        return xy;
    }

     public String DegToDMS(double A){
         int D,M;
         double SS;
         A = A + Math.pow(10 , -8);
         D = (int) A;
         M = (int) ((A - (int)A) * 60);
         SS = (A - D - M / 60) * 3600;
         DecimalFormat df = new DecimalFormat("#.0000");
         String s = df.format(SS);
         String ss[] = s.split("\\.");
         int is = Integer.valueOf(ss[0]);
         is = is % 100;
         s = String.valueOf(is) + "." + ss[1];
         return String.valueOf(D)+","+String.valueOf(M)+","+ s;
     }
}
