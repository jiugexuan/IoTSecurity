package iotpackage.data.ticket;

import iotpackage.IoTKey;
import iotpackage.data.TS;
import iotpackage.data.ciphertext.Lifetime;
import iotpackage.destination.Destination;
import iotpackage.source.Source;

public class Ticket {
    /**
     * 指明密钥关系
     * **/
    IoTKey key;
    /****/
    Source id;
    Destination ad;
    TS ts;
    Lifetime lifetime;

    public Ticket(IoTKey key, Source id, Destination ad, TS ts, Lifetime lifetime) {
        this.key = key;
        this.id = id;
        this.ad = ad;
        this.ts = ts;
        this.lifetime = lifetime;
    }

    public IoTKey getKey() {
        return key;
    }

    public void setKey(IoTKey key) {
        this.key = key;
    }

    public Source getId() {
        return id;
    }

    public void setId(Source id) {
        this.id = id;
    }

    public Destination getAd() {
        return ad;
    }

    public void setAd(Destination ad) {
        this.ad = ad;
    }

    public TS getTs() {
        return ts;
    }

    public void setTs(TS ts) {
        this.ts = ts;
    }

    public Lifetime getLifetime() {
        return lifetime;
    }

    public void setLifetime(Lifetime lifetime) {
        this.lifetime = lifetime;
    }
}
