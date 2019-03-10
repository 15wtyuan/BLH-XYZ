package bean;

public class CoordinateSystem {

    // TODO: 2019/3/7
    public String name;
    public double RA;
    public double RB;
    public double EPF;

    public CoordinateSystem(String name,double RA){
        this.name = name;
        this.RA = RA;
        RB = RA - RA / 298.3;
        EPF = 1 - Math.pow(RB, 2) / Math.pow(RA, 2);
    }
}
