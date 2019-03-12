package singleton;

public class Data {
    private static Data ourInstance = new Data();

    public static Data getInstance() {
        return ourInstance;
    }

    private Object[][] originalData;
    private Object[][] tranData;
    private String[] tableHeader  = {"","",""};

    private Data() {
        originalData = new Object[0][3];
        tranData = new Object[0][3];
    }

    public Object[][] getOriginalData() {
        return originalData;
    }

    public void setOriginalData(Object[][] originalData) {
        this.originalData = originalData;
        calculator();
        //todo
        String[] tableHeader = {"","",""};
        setTableHeader(tableHeader);
    }

    public void calculator(){

    }


    public Object[][] getTranData() {
        return tranData;
    }

    public void setTranData(Object[][] tranData) {
        this.tranData = tranData;
    }

    public String[] getTableHeader() {
        return tableHeader;
    }

    public void setTableHeader(String[] tableHeader) {
        this.tableHeader = tableHeader;
    }
}
