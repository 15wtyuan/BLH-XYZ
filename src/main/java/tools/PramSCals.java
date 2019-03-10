package tools;

import bean.Point2d;
import bean.Point3d;
import bean.ResultParameters4;
import bean.ResultParameters7;

public class PramSCals {

    private static int sign(Double a) {
        if (a < 0) return -1;
        if (a == 0) return 0;
        return 1;
    }

    //求坐标方位角
    private static double FWJ(Point2d p1, Point2d p2) {
        Double dx, dy;
        dx = p2.X - p1.X;
        dy = p2.Y - p1.Y;
        return Math.PI - sign(dy) - Math.atan(dx / dy);
    }

    //两点之间的距离公式
    private static double Dist(Point2d p1, Point2d p2) {
        double d;
        d = Math.sqrt(Math.pow((p2.X - p1.X), 2) + Math.pow((p2.Y - p1.Y), 2));
        return d;
    }

    public static ResultParameters4 Canshu4(Point2d[] p1, Point2d[] p2) {
        ResultParameters4 resultParameters4 = new ResultParameters4();
        resultParameters4.rota = FWJ(p2[0], p2[1]) - FWJ(p1[0], p1[1]);
        resultParameters4.scale = Dist(p2[0], p2[1]) / Dist(p1[0], p1[1]);
        resultParameters4.dx = p2[0].X - resultParameters4.scale * Math.cos(resultParameters4.rota) * p1[0].X + resultParameters4.scale * Math.sin(resultParameters4.rota) * p1[0].Y;
        resultParameters4.dy = p2[0].Y - resultParameters4.scale * Math.sin(resultParameters4.rota) * p1[0].X - resultParameters4.scale * Math.cos(resultParameters4.rota) * p1[0].Y;
        resultParameters4.dx = (double) Math.round(resultParameters4.dx*1000000)/1000000;
        resultParameters4.dy = (double) Math.round(resultParameters4.dy*1000000)/1000000;
        resultParameters4.rota = (double) Math.round(resultParameters4.rota*1000000)/1000000;
        resultParameters4.scale = (double) Math.round(resultParameters4.scale*1000000)/1000000;
        return resultParameters4;
    }

    ///三个以上的点计算四参数
    public static ResultParameters4 Canshu4(Point2d[] p1, Point2d[] p2, int PointCount) {
        double u = 1.0, v = 0, Dx = 0.0, Dy = 0.0;

        int intCount = PointCount;
        //Matrix dx1 ;//误差方程改正数
        Matrix B;//误差方程系数矩阵
        // Matrix W ;//误差方程常数项
        double[][] dx1 = new double[4][ 1];
        double[][] B1 = new double[2 * intCount][ 4];
        double[][] W1 = new double[2 * intCount][ 1];
        // Matrix BT, N, InvN, BTW;
        double[][] BT = new double[4][ 2 * intCount];
        double[][] N = new double[4][ 4];
        double[][] InvN = new double[4][ 4];
        double[][] BTW = new double[4][ 1];
        for (int i = 0; i < intCount; i++) {
            //计算误差方程系数
            B1[2 * i][ 0] =1;
            B1[2 * i][ 1] =0;
            B1[2 * i][ 2] =p1[i].X;
            B1[2 * i][ 3] =-p1[i].Y;


            B1[2 * i + 1][ 0] =0;
            B1[2 * i + 1][1] =1;
            B1[2 * i + 1][ 2] =p1[i].Y;
            B1[2 * i + 1][ 3] =p1[i].X;
        }
        B = new Matrix(B1);
        for (int i = 0; i < intCount; i++) {
            //计算误差方程系常数
            W1[2 * i][ 0] =p2[i].X - u * p1[i].X + v * p1[i].Y - Dx;
            W1[2 * i + 1][ 0] =p2[i].Y - u * p1[i].Y - v * p1[i].X - Dy;

        }
        //最小二乘求解
        BT = B.MatrixInver(B1, BT);//转置
        N = B.MatrixMultiply(BT, B1,N);
        InvN = B.MatrixOpp(N);
        BTW = B.MatrixMultiply(BT, W1,BTW);
        dx1 = B.MatrixMultiply(InvN, BTW,dx1);
        Dx = Dx + dx1[0][ 0];
        Dy = Dy + dx1[1][ 0];
        u = u + dx1[2][ 0];
        v = v + dx1[3][ 0];
        ResultParameters4 resultParameters4 = new ResultParameters4();
        resultParameters4 .dx = Dx;
        resultParameters4.dy = Dy;
        resultParameters4.rota = Math.atan(v / u);
        resultParameters4.scale = u / Math.cos(resultParameters4.rota);
        resultParameters4.dx = (double) Math.round(resultParameters4.dx*1000000)/1000000;
        resultParameters4.dy = (double) Math.round(resultParameters4.dy*1000000)/1000000;
        resultParameters4.rota = (double) Math.round(resultParameters4.rota*1000000)/1000000;
        resultParameters4.scale = (double) Math.round(resultParameters4.scale*1000000)/1000000;
        return resultParameters4;
    }

    /// 求七参数
    public static ResultParameters7 Canshu7(Point3d[] p1, Point3d[] p2, int PointCount) {
        double[][] B1 = new double[PointCount * 3][ 7];
        Matrix B = new Matrix(B1);
        double[][] dx1 = new double[7][ 1];//V=B*X-L
        double[][] L = new double[PointCount * 3][ 1];
        double[][] BT = new double[7][ PointCount * 3];
        double[][] N = new double[7][ 7];
        double[][] InvN = new double[7][ 7];
        double[][] BTL = new double[7][ 1];
        //初始化L矩阵
        for (int i = 0; i < PointCount * 3; i++) {
            if (i % 3 == 0) {
                L[i][ 0] =p2[i / 3].X;
            } else if (i % 3 == 1) {
                L[i][ 0] =p2[i / 3].Y;
            } else if (i % 3 == 2) {
                L[i][ 0] =p2[i / 3].Z;
            }
        }
        //初始化B矩阵
        for (int i = 0; i < PointCount * 3; i++) {
            if (i % 3 == 0) {
                B1[i][ 0] =1;
                B1[i][ 1] =0;
                B1[i][ 2] =0;
                B1[i][ 3] =p1[i / 3].X;
                B1[i][ 4] =0;
                B1[i][ 5] =-p1[i / 3].Z;
                B1[i][ 6] =p1[i / 3].Y;

            } else if (i % 3 == 1) {
                B1[i][ 0] =0;
                B1[i][ 1] =1;
                B1[i][ 2] =0;
                B1[i][ 3] =p1[i / 3].Y;
                B1[i][ 4] =p1[i / 3].Z;
                B1[i][ 5] =0;
                B1[i][ 6] =-p1[i / 3].X;
            } else if (i % 3 == 2) {
                B1[i][ 0] =0;
                B1[i][ 1] =0;
                B1[i][ 2] =1;
                B1[i][ 3] =p1[i / 3].Z;
                B1[i][ 4] =-p1[i / 3].Y;
                B1[i][ 5] =p1[i / 3].X;
                B1[i][ 6] =0;
            }

        }
        //转置
        BT = B.MatrixInver(B1, BT);
        //法方程矩阵
        //N=BT*B
        N = B.MatrixMultiply(BT, B1,  N);
        //求逆
        InvN = B.MatrixOpp(N);
        //BTL=BT*L
        BTL = B.MatrixMultiply(BT, L,  BTL);
        //dx1=invN*BTL;
        dx1 = B.MatrixMultiply(InvN, BTL,  dx1);
        //
        ResultParameters7 resultParameters7 = new ResultParameters7();
        resultParameters7.dx = (double) Math.round(dx1[0][ 0]*1000000)/1000000;
        resultParameters7.dy = (double) Math.round(dx1[1][ 0]*1000000)/1000000;
        resultParameters7.dz = (double) Math.round(dx1[2][ 0]*1000000)/1000000;
        resultParameters7.scale = (double) Math.round(dx1[3][ 0]*1000000)/1000000;
        resultParameters7.rotax = (double) Math.round(dx1[4][ 0] /dx1[3][ 0]*1000000)/1000000;
        resultParameters7.rotay = (double) Math.round(dx1[5][ 0] /dx1[3][ 0]*1000000)/1000000;
        resultParameters7.rotaz = (double) Math.round(dx1[6][ 0] /dx1[3][ 0]*1000000)/1000000;
        return resultParameters7;
    }
}
