package iotpackage;

public class IoTKey {
    /**
     * 表明该Key属于哪个服务器之间
     * */
    String id;
    /**
     * 密钥内容
     * */
    String context;

    public IoTKey(String id, String context) {
        this.id = id;
        this.context = context;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
