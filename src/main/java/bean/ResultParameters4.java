package bean;

public class ResultParameters4 {
    public double rota;
    public double scale;
    public double dx;
    public double dy;

    @Override
    public String toString() {
        return "计算结果\n"
                + "rota: " + rota + "\n"
                + "scale: " + scale + "\n"
                + "dx: " + dx + "\n"
                + "dy: " + dy + "\n";
    }
}
