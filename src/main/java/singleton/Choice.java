package singleton;

import bean.CoordinateSystem;

import java.io.File;

public class Choice {

    private static Choice instance;
    public static Choice getInstance() {
        if (instance == null) {
            instance = new Choice();
        }
        return instance;
    }

    private String method;
    private CoordinateSystem coordinateSystem;
    private double centralMeridian;
    private File choosedFile;

    private Choice(){
        method = null;
        coordinateSystem = null;
        centralMeridian = 0;
        choosedFile = null;
    }

    public double getCentralMeridian() {
        return centralMeridian;
    }

    public void setCentralMeridian(double centralMeridian) {
        this.centralMeridian = centralMeridian;
    }

    public CoordinateSystem getCoordinateSystem() {
        return coordinateSystem;
    }

    public void setCoordinateSystem(CoordinateSystem coordinateSystem) {
        this.coordinateSystem = coordinateSystem;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public File getChoosedFile() {
        return choosedFile;
    }

    public void setChoosedFile(File choosedFile) {
        this.choosedFile = choosedFile;
    }
}
