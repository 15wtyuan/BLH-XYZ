package bean;

public class ResultParameters7 {
    public double rotax;
    public double rotay;
    public double rotaz;
    public double scale;
    public double dx;
    public double dy;
    public double dz;

    @Override
    public String toString() {
        return "计算结果\n"
                + "rotax: " + rotax + "\n"
                + "rotay: " + rotay + "\n"
                + "rotaz: " + rotaz + "\n"
                + "scale: " + scale + "\n"
                + "dx: " + dx + "\n"
                + "dy: " + dy + "\n"
                + "dz: " + dz + "\n";
    }
}
