package iotpackage.data;

/**
 * @author 19710
 */
public class Contain {
    String id;
    Object context;

    public Contain(String id, Object context) {
        this.id = id;
        this.context = context;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getContext() {
        return context;
    }

    public void setContext(Object context) {
        this.context = context;
    }
}
