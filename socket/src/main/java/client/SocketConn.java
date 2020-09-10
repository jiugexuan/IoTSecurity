package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class SocketConn {
    public static final String CHARCODE = "utf-8";
    public static void main(String[] args) {
        Socket socket = null;
        int port = 10086;
        OutputStream socketOut = null;
        BufferedReader br = null;
        try {
            while(true) {


                socket = new Socket("localhost", port);
                String msg = "{\"Info\":{\"Process\":\"Verify\",\"Operation\":\"Response\",\"Source\":{\"ID\":\"AS\",\"IP\":\"127.3.4.1\"},\"Destination\":{\"ID\":\"user1\",\"IP\":\"127.0.0.1\"},\"Data\":{\"Code\":\"0100\"},\"Ciphertext\":{\"content\":\"[context]\",\"id\":\"abcdefg\"}},\"Sign\":{\"Context\":\"{\\\"Process\\\":\\\"Verify\\\",\\\"Operation\\\":\\\"Response\\\",\\\"Source\\\":{\\\"ID\\\":\\\"AS\\\",\\\"IP\\\":\\\"127.3.4.1\\\"},\\\"Destination\\\":{\\\"ID\\\":\\\"user1\\\",\\\"IP\\\":\\\"127.0.0.1\\\"},\\\"Data\\\":{\\\"Code\\\":\\\"0100\\\"},\\\"Ciphertext\\\":{\\\"content\\\":\\\"[context]\\\",\\\"id\\\":\\\"abcdefg\\\"}}\",\"PublicKey\":\"publickey\"}}";
                System.out.println("c发出：" + msg);
                socketOut = socket.getOutputStream();
                socketOut.write(msg.getBytes(CHARCODE));
                socketOut.flush();
                socket.shutdownOutput();


                // 接收服务器的反馈
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String res = br.readLine();
                if (res != null) {
                    System.out.println("接收回复:" + res);
                }
            }



        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (socketOut != null) {
                try {
                    socketOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
