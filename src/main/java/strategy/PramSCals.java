package strategy;

import bean.Point2d;
import bean.Point3d;
import bean.ResultParameters4;
import bean.ResultParameters7;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;

import java.util.ArrayList;
import java.util.List;

public class PramSCals {

    public static Point3d Para7Convert(Point3d aPtSource, ResultParameters7 sep)
    {
        double k = sep.scale;
        double a2 = k * sep.rotax;
        double a3 = k * sep.rotay;
        double a4 = k * sep.rotax;

        Point3d ToCoordinate = new Point3d();
        ToCoordinate.X = sep.dx + k * aPtSource.X - a3 * aPtSource.Z + a4 * aPtSource.Y;
        ToCoordinate.Y = sep.dy + k * aPtSource.Y + a2 * aPtSource.Z - a4 * aPtSource.X;
        ToCoordinate.Z = sep.dx + k * aPtSource.Z - a2 * aPtSource.Y + a3 * aPtSource.X;

        return ToCoordinate;
    }

    public static Point3d Para4Convert(Point3d aPtSource, ResultParameters4 forPara)
    {
        double k = forPara.scale;
        double a1 = k * Math.cos(forPara.rota);
        double a2 = k * Math.sin(forPara.rota);

        Point3d ToCoordinate = new Point3d();
        ToCoordinate.X = forPara.dx + a1 * aPtSource.X + a2 * aPtSource.Y;
        ToCoordinate.Y = forPara.dy + a1 * aPtSource.Y - a2 * aPtSource.X;
        ToCoordinate.Z = aPtSource.Z;

        return ToCoordinate;
    }



    public static ResultParameters4 Calc4Para(List<Point3d> aPtSource, List<Point3d> aPtTo)
    {
        Double rms;
        double[][] arrA = new double[aPtSource.size() * 2][ 4]; // 如果是4个已知点， 8 * 4矩阵  A*X=B中的矩阵A
        for (int i = 0; i <= arrA.length - 1; i++)
        {
            if (i % 2 == 0)
            {
                arrA[i][ 0] = aPtSource.get(i / 2).X;
                arrA[i][ 1] = aPtSource.get(i / 2).Y;
                arrA[i][ 2] = 1;
                arrA[i][ 3] = 0;
            }
            else if (i % 2 == 1)
            {
                arrA[i][ 0] = aPtSource.get(i / 2).Y;
                arrA[i][ 1] = -aPtSource.get(i / 2).X;
                arrA[i][ 2] = 0;
                arrA[i][ 3] = 1;
            }
        }
        double[][] arrB = new double[aPtSource.size() * 2][ 1]; // A * X = B 中的矩阵B, 如果有4个点，就是 8*1矩阵
        for (int i = 0; i <= arrB.length - 1; i++)
        {
            if (i % 2 == 0)
            {
                arrB[i][ 0] = aPtTo.get(i / 2).X;
            }
            else if (i % 2 == 1)
            {
                arrB[i][ 0] = aPtTo.get(i / 2).Y;
            }
        }
        RealMatrix mtrA = new Array2DRowRealMatrix(arrA); // A矩阵
        RealMatrix mtrAT = mtrA.transpose(); // A的转置
        RealMatrix mtrB = new Array2DRowRealMatrix(arrB); // B矩阵
        RealMatrix mtrATmulA = mtrAT.multiply(mtrA); // A的转置×A
        // 求(A的转置×A)的逆矩阵
        mtrATmulA = inverseMatrix(mtrATmulA);
        // A的转置 × B
        RealMatrix mtrATmulB = mtrAT.multiply(mtrB); // A的转置 * B
        // 结果
        RealMatrix mtrResult = mtrATmulA.multiply(mtrATmulB);

        ResultParameters4 forPara = new ResultParameters4();
        forPara.dx = mtrResult.getData()[2][ 0];
        forPara.dy = mtrResult.getData()[3][ 0];
        forPara.rota = Math.atan(mtrResult.getData()[1][ 0] / mtrResult.getData()[0][ 0]);
        forPara.scale = mtrResult.getData()[1][ 0] / Math.sin(forPara.rota);

        //计算均方根误差rms
        List<Point3d> pTo = new ArrayList<>();
        for (int i = 0; i < aPtSource.size(); i++)
        {
            pTo.add(Para4Convert(aPtSource.get(i), forPara));
        }
        rms = 0d;
        for (int i = 0; i < aPtTo.size(); i++)
        {
            double deltaX = aPtTo.get(i).X - pTo.get(i).X;
            double deltaY = aPtTo.get(i).Y - pTo.get(i).Y;

            double oneSigma = Math.sqrt((Math.pow(deltaX, 2) + Math.pow(deltaY, 2)) / 2);

            rms += oneSigma;
        }

        rms = rms / aPtTo.size();

        return forPara;
    }

    //求逆函数
    public static RealMatrix inverseMatrix(RealMatrix A) {
        RealMatrix result = new LUDecomposition(A).getSolver().getInverse();
        return result;
    }

    /// <summary>
    /// 根据3个或者3个以上的点的两套坐标系的坐标计算7参数(最小二乘法) 适用于小角度转换 bursa模型
    /// </summary>
    /// <param name="aPtSource">已知点的源坐标系的坐标</param>
    /// <param name="aPtTo">已知点的新坐标系的坐标</param>
    /// <param name="sep">输出: 7参数</param>
    public static ResultParameters7 Calc7Para(List<Point3d> aPtSource, List<Point3d> aPtTo)
    {
        double rms;
        double[][] arrA = new double[aPtSource.size() * 3][ 7]; // 如果是4个已知点， 12 * 7矩阵  A*X=B中的矩阵A
        for (int i = 0; i < arrA.length; i++)
        {
            if (i % 3 == 0)
            {
                arrA[i][ 0] = 1;
                arrA[i][ 1] = 0;
                arrA[i][ 2] = 0;
                arrA[i][ 3] = aPtSource.get(i / 3).X;
                arrA[i][ 4] = 0;
                arrA[i][ 5] = -aPtSource.get(i / 3).Z;
                arrA[i][ 6] = aPtSource.get(i / 3).Y;
            }
            else if (i % 3 == 1)
            {
                arrA[i][ 0] = 0;
                arrA[i][ 1] = 1;
                arrA[i][ 2] = 0;
                arrA[i][ 3] = aPtSource.get(i / 3).Y;
                arrA[i][ 4] = aPtSource.get(i / 3).Z;
                arrA[i][ 5] = 0;
                arrA[i][ 6] = -aPtSource.get(i / 3).X;
            }
            else if (i % 3 == 2)
            {
                arrA[i][ 0] = 0;
                arrA[i][ 1] = 0;
                arrA[i][ 2] = 1;
                arrA[i][ 3] = aPtSource.get(i / 3).Z;
                arrA[i][ 4] = -aPtSource.get(i / 3).Y;
                arrA[i][ 5] = aPtSource.get(i / 3).X;
                arrA[i][ 6] = 0;
            }
        }
        double[][] arrB = new double[aPtSource.size() * 3][1]; // A * X = B 中的矩阵B, 如果有4个点，就是 12*1矩阵
        for (int i = 0; i <= arrB.length - 1; i++)
        {
            if (i % 3 == 0)
            {
                arrB[i][ 0] = aPtTo.get(i / 3).X;
            }
            else if (i % 3 == 1)
            {
                arrB[i][ 0] = aPtTo.get(i / 3).Y;
            }
            else if (i % 3 == 2)
            {
                arrB[i][ 0] = aPtTo.get(i / 3).Z;
            }
        }
        RealMatrix mtrA = new Array2DRowRealMatrix(arrA); // A矩阵
        RealMatrix mtrAT = mtrA.transpose(); // A的转置
        RealMatrix mtrB = new Array2DRowRealMatrix(arrB); // B矩阵
        RealMatrix mtrATmulA = mtrAT.multiply( mtrA); // A的转置×A
        // 求(A的转置×A)的逆矩阵
        mtrATmulA = inverseMatrix(mtrATmulA);
        // A的转置 × B
        RealMatrix mtrATmulB = mtrAT.multiply( mtrB); // A的转置 * B
        // 结果
        RealMatrix mtrResult = mtrATmulA.multiply( mtrATmulB);
        ResultParameters7 sep = new ResultParameters7();
        sep.dx = mtrResult.getData()[0][ 0];
        sep.dy = mtrResult.getData()[1][ 0];
        sep.dz = mtrResult.getData()[2][ 0];
        sep.scale = mtrResult.getData()[3][ 0];
        sep.rotax = mtrResult.getData()[4][ 0] / sep.scale;
        sep.rotay = mtrResult.getData()[5][ 0] / sep.scale;
        sep.rotaz = mtrResult.getData()[6][ 0] / sep.scale;

        //计算均方根误差rms
        List<Point3d> pTo = new ArrayList<>();
        for (int i = 0; i < aPtSource.size(); i++)
        {
            pTo.add(Para7Convert(aPtSource.get(i), sep));
        }
        rms = 0;
        for (int i = 0; i < aPtTo.size(); i++)
        {
            double deltaX = aPtTo.get(i).X - pTo.get(i).X;
            double deltaY = aPtTo.get(i).Y - pTo.get(i).Y;

            double oneSigma = Math.sqrt((Math.pow(deltaX, 2) + Math.pow(deltaY, 2)) / 2);

            rms += oneSigma;
        }

        rms = rms / aPtTo.size();

        return sep;
    }

}
