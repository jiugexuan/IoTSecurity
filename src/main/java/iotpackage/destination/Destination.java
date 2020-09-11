package iotpackage.destination;

public class Destination {
    /**
     * 接送方账户
     * **/
    String id;
    /**
     * 接送方IP地址
     * **/
    String ip;

    public Destination() {

    }

    public Destination(String id, String ip) {
        this.id = id;
        this.ip = ip;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }


    public void printDestination(){
        System.out.println(">--------------");
        System.out.println("the Destination:");
        System.out.println(">>Id:"+getId());
        System.out.println(">>IP:"+getIp());
        System.out.println(">--------------");
    }
}
