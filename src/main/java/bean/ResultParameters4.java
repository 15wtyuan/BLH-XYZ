package bean;

public class ResultParameters4 {
    public String name;
    public double rota;
    public double scale;
    public double dx;
    public double dy;

    public  ResultParameters4(){}

    public ResultParameters4(String name, double rota, double scale, double dx, double dy) {
        this.name = name;
        this.rota = rota;
        this.scale = scale;
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public String toString() {
        return "计算结果\n"
                + "rota: " + rota + "\n"
                + "scale: " + scale + "\n"
                + "dx: " + dx + "\n"
                + "dy: " + dy + "\n";
    }
}
