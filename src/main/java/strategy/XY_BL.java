package strategy;

import bean.Point3d;
import singleton.Choice;
import tools.Trans;

import java.util.List;

public class XY_BL extends AbstractCalculator implements ICalculator {
    @Override
    public String[] calculate(String[] str) {
        String[] result = {"", ""};
        try {
            Point3d xy = new Point3d(0, 0,0);
            Trans trans = new Trans();
            Point3d bl = new Point3d(0, 0,0);

            xy.X = Double.valueOf(str[0]);
            xy.Y = Double.valueOf(str[1]);

            trans.xyToBL(xy, Choice.getInstance().getCentralMeridian(), Choice.getInstance().getCoordinateSystem().RA, Choice.getInstance().getCoordinateSystem().RB, bl);
            result[0] = trans.DegToDMS(bl.X);
            result[1] = trans.DegToDMS(bl.Y);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
