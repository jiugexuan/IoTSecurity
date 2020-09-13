package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class TGSConn extends SocketConn {

    String ip="127.0.0.1";
    String port="6766";

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

    public TGSConn(String ip, String port) {
        try {
            socket = new Socket(ip, Integer.parseInt(port));
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public TGSConn(String ip) {
        try {
            socket = new Socket(ip, Integer.parseInt(this.port));
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public TGSConn(){
        try {
            socket = new Socket(this.ip, 6766);
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
