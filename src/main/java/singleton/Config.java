package singleton;

import bean.CoordinateSystem;
import org.ini4j.Wini;
import org.ini4j.Profile.Section;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

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
            coordinateSystemList = new ArrayList<CoordinateSystem>();
            Wini ini = new Wini(new File("config.ini"));
            Set<Entry<String, Section>> set = ini.entrySet();
            for (Entry<String, Section> entry : set) {
                String sectionName = entry.getKey();
                CoordinateSystem coordinateSystem = new CoordinateSystem(sectionName,Integer.valueOf(entry.getValue().get("RA")));
                coordinateSystemList.add(coordinateSystem);
            }
        }catch (Exception e){
            System.out.println("读取配置失败，使用默认配置");
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
