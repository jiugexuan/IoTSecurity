package iotpackage;

/**
 * @author 19710
 */
public class IPInfo {


    /**
     * 接送方账户
     * **/
    String id;
    /**
     * 接送方IP地址
     * **/
    String ip;

    public IPInfo() {

    }

    public IPInfo(String id, String ip) {
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

    public void printIPinformation(){
        System.out.println(">--------------");
        System.out.println("the IPInfomation:");
        System.out.println(">>Id:"+getId());
        System.out.println(">>IP:"+getIp());
        System.out.println(">--------------");
    }


}
