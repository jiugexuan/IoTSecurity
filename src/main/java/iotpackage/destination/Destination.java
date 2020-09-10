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

    public void printDetination(){
        System.out.println("the destiontion\n"+"id:"+getId()+"\nip:"+getIp()+"\n");
    }
}
