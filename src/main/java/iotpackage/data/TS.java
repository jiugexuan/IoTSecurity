package iotpackage.data;

import java.text.SimpleDateFormat;
import java.util.Date;

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

    public TS(int id){
        this.id=id;
        this.context=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
    }


    public void printTS(){
        System.out.println(">--------------");
        System.out.println("the ts:");
        System.out.println(">>id:"+getId());
        System.out.println(">>context:"+getContext());
        System.out.println(">--------------");

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
