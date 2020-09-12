package iotpackage.destination;


import iotpackage.IPInfo;

public class Destination extends IPInfo {


    public Destination(String id, String ip) {
        super(id, ip);
    }

    public Destination(){
        super();
    }

    public void printDestination(){
        System.out.println(">--------------");
        System.out.println("the Destination:");
        System.out.println(">>Id:"+getId());
        System.out.println(">>IP:"+getIp());
        System.out.println(">--------------");
    }

}
