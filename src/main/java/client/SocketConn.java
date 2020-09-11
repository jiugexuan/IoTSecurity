package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Logger;

public class SocketConn {

    final static int port = 8888;

    final static int MAX_SIZE = 4096;

    static Socket socket = null;

    static SocketConn conn = null;

    static Logger log = Logger.getLogger("Connect-Status-Log");


    public void send(byte[] content){
        try {
            OutputStream socketOut = socket.getOutputStream();
            socketOut.write(content);
            socketOut.flush();
            System.out.print("\n socket 信息发送");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            log.warning("Fail to send Messeag due to IOException!");
            e.printStackTrace();
        }
    }

    public int receive(byte[] result){
        int len = -1;
        try {
            InputStream socketIn = socket.getInputStream();
            len = socketIn.read(result);
            System.out.print("\n socket 信息接收");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            log.warning("Fail to receive Messeag due to IOException!");
            e.printStackTrace();
        }
        return len;
    }

    public void close() throws IOException{
        socket.close();
        socket = null;
    }
}
