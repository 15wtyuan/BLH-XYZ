package bean;

public class CoordinateSystem {

    // TODO: 2019/3/7
    public String name;
    public double RA;
    public double RB;
    public double EPF;
    public double Flat;

    public CoordinateSystem(String name,double RA,double Flat){
        this.name = name;
        this.RA = RA;
        this.RB = RA - RA / Flat;
        EPF = 1 - Math.pow(RB, 2) / Math.pow(RA, 2);
        this.Flat = Flat;
    }
}
