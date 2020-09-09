package iotpackage.data;

/**
 * @author ji
 * */
public class TS {
    /**TS的id号**/
    int id;
    /**TS的内容*/
    String context;

    public TS(int id, String context) {
        this.id = id;
        this.context = context;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
