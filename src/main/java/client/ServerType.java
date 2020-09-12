package client;

public enum ServerType {
    //代表各类
    AS,
    TGS,
    SERVER,
    CHATSERVER,
    FILESERVER;


    public static ServerType getValue(String server){
        return valueOf(server.toUpperCase());
    }
}