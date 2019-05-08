package singleton;

import bean.CoordinateSystem;
import bean.ResultParameters4;
import bean.ResultParameters7;
import org.ini4j.Ini;
import org.ini4j.Profile.Section;
import org.ini4j.Wini;

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
    private ArrayList<ResultParameters4> resultParameters4ArrayList;
    private ArrayList<ResultParameters7> resultParameters7ArrayList;

    private Config(){
        // TODO: 2019/3/7

        try {
            coordinateSystemList = new ArrayList<>();
            Wini ini = new Wini(new File("config.ini"));
            Set<Entry<String, Section>> set = ini.entrySet();
            for (Entry<String, Section> entry : set) {
                String sectionName = entry.getKey();
                CoordinateSystem coordinateSystem = new CoordinateSystem(sectionName,Double.valueOf(entry.getValue().get("RA")),Double.valueOf(entry.getValue().get("Flat")));
                coordinateSystemList.add(coordinateSystem);
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("读取配置失败，使用默认配置");
            coordinateSystemList = new ArrayList<>();
            CoordinateSystem coordinateSystem1 = new CoordinateSystem("1954年北京坐标系",6378245,298.3);
            CoordinateSystem coordinateSystem2 = new CoordinateSystem("WGS84世界坐标系",6378137,298.257223);
            coordinateSystemList.add(coordinateSystem1);
            coordinateSystemList.add(coordinateSystem2);
        }

        try {
            resultParameters4ArrayList = new ArrayList<>();
            Wini ini = new Wini(new File("Parameters4.ini"));
            Set<Entry<String, Section>> set = ini.entrySet();
            for (Entry<String, Section> entry : set) {
                String sectionName = entry.getKey();
                ResultParameters4 resultParameters4 = new ResultParameters4(sectionName,
                        Double.valueOf(entry.getValue().get("rota")),
                        Double.valueOf(entry.getValue().get("scale")),
                        Double.valueOf(entry.getValue().get("dx")),
                        Double.valueOf(entry.getValue().get("dy")));
                resultParameters4ArrayList.add(resultParameters4);
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("读取四参数配置失败");
        }

        try {
            resultParameters7ArrayList = new ArrayList<>();
            Wini ini = new Wini(new File("Parameters7.ini"));
            Set<Entry<String, Section>> set = ini.entrySet();
            for (Entry<String, Section> entry : set) {
                String sectionName = entry.getKey();
                ResultParameters7 resultParameters7 = new ResultParameters7(sectionName,
                        Double.valueOf(entry.getValue().get("rotax")),
                        Double.valueOf(entry.getValue().get("rotay")),
                        Double.valueOf(entry.getValue().get("rotaz")),
                        Double.valueOf(entry.getValue().get("scale")),
                        Double.valueOf(entry.getValue().get("dx")),
                        Double.valueOf(entry.getValue().get("dy")),
                        Double.valueOf(entry.getValue().get("dz")));
                resultParameters7ArrayList.add(resultParameters7);
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("读取七参数配置失败");
        }
    }

    public ArrayList<ResultParameters4> getResultParameters4ArrayList() {
        return resultParameters4ArrayList;
    }

    public void setResultParameters4ArrayList(ArrayList<ResultParameters4> resultParameters4ArrayList) {
        this.resultParameters4ArrayList = resultParameters4ArrayList;
    }

    public ArrayList<ResultParameters7> getResultParameters7ArrayList() {
        return resultParameters7ArrayList;
    }

    public void setResultParameters7ArrayList(ArrayList<ResultParameters7> resultParameters7ArrayList) {
        this.resultParameters7ArrayList = resultParameters7ArrayList;
    }

    public static void saveParameters7(ResultParameters7 resultParameters7) {
        try {
            Wini ini = new Wini(new File("Parameters7.ini"));
            if (ini.get(resultParameters7.name)!=null)
                return;
            ini.add(resultParameters7.name);
            Ini.Section section = ini.get(resultParameters7.name);
            section.add("rotax",resultParameters7.rotax);
            section.add("rotay",resultParameters7.rotay);
            section.add("rotaz",resultParameters7.rotaz);
            section.add("dx",resultParameters7.dx);
            section.add("dy",resultParameters7.dy);
            section.add("dz",resultParameters7.dz);
            section.add("scale",resultParameters7.scale);
            ini.store();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("保存7参数失败");
        }
    }

    public static void saveParameters4(ResultParameters4 resultParameters4) {
        try {
            Wini ini = new Wini(new File("Parameters4.ini"));
            if (ini.get(resultParameters4.name)!=null)
                return;
            ini.add(resultParameters4.name);
            Ini.Section section = ini.get(resultParameters4.name);
            section.add("rota",resultParameters4.rota);
            section.add("dx",resultParameters4.dx);
            section.add("dy",resultParameters4.dy);
            section.add("scale",resultParameters4.scale);
            ini.store();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("保存4参数失败");
        }
    }

    public List<CoordinateSystem> getCoordinateSystemList() {
        return coordinateSystemList;
    }

    public void setCoordinateSystemList(ArrayList<CoordinateSystem> coordinateSystemList) {
        this.coordinateSystemList = coordinateSystemList;
    }
}
