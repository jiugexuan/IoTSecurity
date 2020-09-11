package Server;

import java.io.*;
import java.net.Socket;

public class ServerThread implements Runnable {
    public static final String CHARCODE = "utf-8";
    private Socket socket;
    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    private PrintWriter getWriter(Socket socket) throws IOException {
        OutputStream socketOut = socket.getOutputStream();
        return new PrintWriter(socketOut, true);
    }

    private BufferedReader getReader(Socket socket) throws IOException {
        InputStream socketIn = socket.getInputStream();
        return new BufferedReader(new InputStreamReader(socketIn));
    }

    @Override
    public void run() {
        BufferedReader br = null;
        PrintWriter out = null;
        try {
            br = getReader(socket);
            out = getWriter(socket);
            String msg = null;
            String res = null;
            while ((msg = br.readLine()) != null) {
                res = "shoudaotongxin";
                System.out.println("发出回复："+res);
                out.println(res);
                out.flush();
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
            if (out != null) {
                out.close();
            }
        }
    }

}
