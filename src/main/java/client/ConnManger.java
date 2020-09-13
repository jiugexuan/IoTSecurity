package client;

public class ConnManger {
    private SocketConn conn = null;

    public ConnManger(String server){
        switch(ServerType.getValue(server)){
            case AS: conn = new ASConn(); break;
            case TGS: conn = new TGSConn(); break;
            case SERVER : conn = new SERConn();break;
        }
    }

    public SocketConn getConn(){
        return conn;
    }
}
