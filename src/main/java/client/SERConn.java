package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class SERConn  extends SocketConn{
    String ip="127.0.0.1";
    String port="9999";

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

    public SERConn(String ip, String port) {
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

    public SERConn(String ip) {
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

    public SERConn(){
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
}
