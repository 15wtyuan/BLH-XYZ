package singleton;

import bean.CoordinateSystem;
import org.ini4j.Wini;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Config {

    private static Config instance;
    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }

    private ArrayList<CoordinateSystem> coordinateSystemList;

    private Config(){
        // TODO: 2019/3/7

        try {
            Wini ini = new Wini(new File("config.ini"));

        }catch (Exception e){
            e.printStackTrace();
            coordinateSystemList = new ArrayList<CoordinateSystem>();
            CoordinateSystem coordinateSystem1 = new CoordinateSystem("1954年北京坐标系",6378245);
            CoordinateSystem coordinateSystem2 = new CoordinateSystem("WGS84世界坐标系",6378137);
            coordinateSystemList.add(coordinateSystem1);
            coordinateSystemList.add(coordinateSystem2);
        }
    }

    public List<CoordinateSystem> getCoordinateSystemList() {
        return coordinateSystemList;
    }

    public void setCoordinateSystemList(ArrayList<CoordinateSystem> coordinateSystemList) {
        this.coordinateSystemList = coordinateSystemList;
    }
}
