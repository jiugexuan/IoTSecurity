package iotpackage.data.autheticator;

import iotpackage.data.TS;
import iotpackage.destination.Destination;
import iotpackage.source.Source;

public class Authenticator {
    /**验证服务器
     * id
     * **/
    Destination id;
    /***验证对象ad
     * ad
     * **/
    Source ad;
    /**时间戳**/
    TS ts;

    public Authenticator(Destination id, Source ad, TS ts) {
        this.id = id;
        this.ad = ad;
        this.ts = ts;
    }

    public void printfAuthenticator(){
        System.out.println("--------------");
        System.out.println("the Autheticator:");
        System.out.println(">>Destination id:");
        this.id.printDestination();
        System.out.println(">>Source ad:");
        this.ad.printSource();
        System.out.println(">>ts:");
        this.ts.printTS();
        System.out.println("--------------");
    }

    public Destination getId() {
        return id;
    }

    public void setId(Destination id) {
        this.id = id;
    }

    public Source getAd() {
        return ad;
    }

    public void setAd(Source ad) {
        this.ad = ad;
    }

    public TS getTs() {
        return ts;
    }

    public void setTs(TS ts) {
        this.ts = ts;
    }
}
