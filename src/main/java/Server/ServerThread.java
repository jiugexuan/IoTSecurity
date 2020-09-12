package Server;

import com.fasterxml.jackson.core.JsonProcessingException;
import iotpackage.IoTKey;
import iotpackage.constructor.PackageConstructor;
import iotpackage.constructor.PackageParser;
import iotpackage.data.TS;
import iotpackage.data.autheticator.Authenticator;
import iotpackage.data.ciphertext.Lifetime;
import iotpackage.data.ticket.Ticket;
import iotpackage.destination.Destination;
import iotpackage.source.Source;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ServerThread implements Runnable {
    public String UserIP = "127.0.0.1";
    public String ASIP = "127.0.0.1";
    public String TGSIP = "127.0.0.1";
    public String SERIP = "127.0.0.1";
    public String Kctgs = "963852741";
    public String Kcv = "";
    public String KeyV = "852456789";
    public String KeyTGS = "741852963";

    public static final String CHARCODE = "utf-8";
    private Socket socket;
    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    public void send(byte[] content){
        try {
            OutputStream socketOut = socket.getOutputStream();
            socketOut.write(content);
            socketOut.flush();
            // log.info("Messeag has been sent!");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            //log.warning("Fail to send Messeag due to IOException!");
            e.printStackTrace();
        }
    }

    public int receive(byte[] result){
        int len = -1;
        try {
            InputStream socketIn = socket.getInputStream();
            len = socketIn.read(result, 0, 4048);
            // log.info("Messeag has been received!");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            // log.warning("Fail to receive Messeag due to IOException!");
            e.printStackTrace();
        }
        return len;
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
        //接收到的报文
        byte[] bytes = new byte[4096];
        //接收
        receive(bytes);
        String content= new String(bytes);
        System.out.println("\n Server 接收的数据为："+content);
        PackageParser packageParser= null;
        Ticket ticketText = null;
        Authenticator authenticator =null;
        String info = null;
        try {
            packageParser = new PackageParser(content);
            info = packageParser.getDataJson();
            System.out.println(info);
            ticketText = packageParser.getTicket(info,KeyV,"ticketV");

        } catch (IOException | IllegalBlockSizeException | InvalidKeyException | BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }

        System.out.print("\n Ser解密ticket：");

        ticketText.printfTicket();

        System.out.print("\n Ser解密ticket得到的密码：");
        //kcv
        ticketText.getKey().printIoTKey();


        Kcv = ticketText.getKey().getContext();

        System.out.print("\n kcv 的密码："+Kcv+"\n");


        try {
            authenticator = packageParser.getAuthenticator(info,Kctgs,"authenticator C");
        } catch (IOException | IllegalBlockSizeException | InvalidKeyException | BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }

        System.out.print("\n Server解密Authentic得到的：");
        authenticator.printfAuthenticator();

        //authenticator 中的时间戳
        String Atime = authenticator.getTs().getContext();

            //TGS -> C
            String SERtoC = null;
            PackageConstructor packageConstructor = new PackageConstructor();
            IoTKey ioTKey =new IoTKey("key C V",Kcv);
            Ticket ticketV = null;
            ticketV = new Ticket(ioTKey,new Source("TGS",TGSIP),new Destination("user",UserIP),new TS(4),new Lifetime("4","54000"));
            try {
                SERtoC = packageConstructor.getPackageVtoCVerify("Verify","Response",new Source("SERVER",SERIP) ,new Destination("user",UserIP),"0100",Kcv,new TS(6),"");
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            System.out.print("\n SER 发送报文到客户端："+SERtoC);

            send(SERtoC.getBytes());


    }


}
