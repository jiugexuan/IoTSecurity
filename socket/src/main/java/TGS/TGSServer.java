package TGS;



import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TGSServer {
    private static final int PORT = 10086;//协议端口
    private ServerSocket serverSocket;//服务端ServerSocket
    private ExecutorService executorService;
    private final int POOL_SIZE = 10;//线程池数量

    public TGSServer() throws IOException {
        serverSocket = new ServerSocket(PORT);
        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * POOL_SIZE);
        System.out.println("服务已启动");
    }


    public void service() {
        while (true) {
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                executorService.execute(new Handler(socket));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) throws IOException {
        new TGSServer().service();
    }

    class Handler implements Runnable {

        public static final String CHARCODE = "utf-8";
        private Socket socket;
        public Handler(Socket socket) {
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

}


