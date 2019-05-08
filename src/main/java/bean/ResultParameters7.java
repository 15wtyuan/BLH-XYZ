package bean;

public class ResultParameters7 {
    public String name;
    public double rotax;
    public double rotay;
    public double rotaz;
    public double scale;
    public double dx;
    public double dy;
    public double dz;

    public ResultParameters7() {
    }

    public ResultParameters7(String name, double rotax, double rotay, double rotaz, double scale, double dx, double dy, double dz) {
        this.name = name;
        this.rotax = rotax;
        this.rotay = rotay;
        this.rotaz = rotaz;
        this.scale = scale;
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
    }

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
