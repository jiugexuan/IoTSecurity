package client;

public class ConnManger {
    private SocketConn conn = null;
    String ip;
    String port;

    public String getIp() {
        return ip;
    }

    public String getPort() {
        return port;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public ConnManger(String server,String ip, String port) {
        switch(ServerType.getValue(server)){
            case AS: conn = new ASConn(ip,port); break;
            case TGS: conn = new TGSConn(ip,port); break;
            case SERVER : conn = new SERConn(ip,port);break;
            //case TGS: conn = new TGSConn(ip,port); break;
            // case SERVER : conn = new SERConn(ip,port);break;
        }
    }

    public ConnManger(String server,String ip) {
        switch(ServerType.getValue(server)){
            case AS: conn = new ASConn(ip); break;
            case TGS: conn = new TGSConn(ip); break;
            case SERVER : conn = new SERConn(ip);break;
            //case TGS: conn = new TGSConn(ip,port); break;
            // case SERVER : conn = new SERConn(ip,port);break;
        }
    }


    public ConnManger(String server){
        switch(ServerType.getValue(server)){
            case AS: conn = new ASConn(ip,port); break;
            case TGS: conn = new TGSConn(ip,port); break;
            case SERVER : conn = new SERConn(ip,port);break;
            //case TGS: conn = new TGSConn(ip,port); break;
           // case SERVER : conn = new SERConn(ip,port);break;
        }
    }

    public SocketConn getConn(){
        return conn;
    }
}
