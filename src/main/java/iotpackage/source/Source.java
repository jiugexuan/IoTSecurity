package iotpackage.source;


import iotpackage.IPInfo;

public class Source  extends IPInfo {


    public Source(String id, String ip) {
        super(id, ip);
    }

    public Source(){
        super();
    }

    public void printSource(){
        System.out.println(">--------------");
        System.out.println("the Source:");
        System.out.println(">>Id:"+getId());
        System.out.println(">>IP:"+getIp());
        System.out.println(">--------------");
    }


}

