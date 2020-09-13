package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class SERConn  extends SocketConn{

    public SERConn(){
        try {
            socket = new Socket("localhost", 9999);
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
