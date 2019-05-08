package singleton;

import bean.CoordinateSystem;
import bean.ResultParameters4;
import bean.ResultParameters7;

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
    private ResultParameters7 resultParameters7;
    private ResultParameters4 resultParameters4;

    private Choice(){
        init();
    }

    public void init(){
        method = null;
        coordinateSystem = null;
        centralMeridian = -1;
        choosedFile = null;
    }

    public ResultParameters7 getResultParameters7() {
        return resultParameters7;
    }

    public void setResultParameters7(ResultParameters7 resultParameters7) {
        this.resultParameters7 = resultParameters7;
    }

    public ResultParameters4 getResultParameters4() {
        return resultParameters4;
    }

    public void setResultParameters4(ResultParameters4 resultParameters4) {
        this.resultParameters4 = resultParameters4;
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
