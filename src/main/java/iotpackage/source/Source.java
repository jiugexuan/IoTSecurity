package iotpackage.source;


public class Source  {
    /**
     *发送方账户名
     * **/
    String id;
    /**
     *发送IP地址
     * **/
    String ip;

    public Source() {

    }

    public Source(String id, String ip) {
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

    public void printSource(){
        System.out.println("the source\n"+"id:"+getId()+"\nip:"+getIp()+"\n");
    }

}

