package TGS;

import com.fasterxml.jackson.core.JsonProcessingException;
import iotpackage.IoTKey;
import iotpackage.constructor.CipherConstructor;
import iotpackage.constructor.PackageConstructor;
import iotpackage.constructor.PackageParser;
import iotpackage.data.TS;
import iotpackage.data.autheticator.Authenticator;
import iotpackage.data.ciphertext.Ciphertext;
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
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class TGS implements Runnable {

    final static int MAX_SIZE = 4096;
    private Socket socket;
    public TGS(Socket socket){
        this.socket=socket;
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
            len = socketIn.read(result, 0, MAX_SIZE);
            // log.info("Messeag has been received!");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            // log.warning("Fail to receive Messeag due to IOException!");
            e.printStackTrace();
        }
        return len;
    }
    @Override
    public void run() {
        //接收到的报文
        byte[] bytes = new byte[4096];
        //接收
        receive(bytes);
        String content= new String(bytes);
        System.out.println("\nTGS 接受的数据为："+content);
        PackageParser packageParser= null;
        Ticket ticketText = null;
        Authenticator authenticator =null;
        String info = null;

        try {
            packageParser = new PackageParser(content);
            info = packageParser.getDataJson();
            System.out.println(info);
            ticketText = packageParser.getTicket(info,"tickkey AS TGS","65");

        } catch (IOException | IllegalBlockSizeException | InvalidKeyException | BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }



        System.out.print("\n TGS解密ticket：");

        ticketText.printfTicket();

        System.out.print("\n TGS解密ticket得到的密码：");

        ticketText.getKey().printIoTKey();

        //ticket中的 AS生成的 密码
        String Auth = ticketText.getKey().getContext();
        //C 发来的报文 ticket中 时间戳
        String Ctime = ticketText.getTs().getContext();
        // 报文 ticket 中 lifetime
        String lifetime = ticketText.getLifetime().getContext();

        try {
            authenticator = packageParser.getAuthenticator(info,Auth,"");
        } catch (IOException | IllegalBlockSizeException | InvalidKeyException | BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }

        System.out.print("\n TGS解密Authentic得到的：");
        authenticator.printfAuthenticator();

        //authenticator 中的时间戳
        String Atime = authenticator.getTs().getContext();

        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(Atime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
       long AuthenticTime = calendar.getTimeInMillis();

        Calendar calendar2 = Calendar.getInstance();
        try {
            calendar2.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(Ctime));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long TicketTime = calendar2.getTimeInMillis();

        System.out.print("atime"+AuthenticTime+"ctim"+TicketTime);
        long differ = AuthenticTime - TicketTime;

        if (differ > Long.parseLong(lifetime)){
            send("请求超时".getBytes());
        } else {
            String TGStoC = null;
            PackageConstructor packageConstructor = new PackageConstructor();
            IoTKey ioTKey =new IoTKey("CandTGS","12345678");
            Ticket ticketV = new Ticket(ioTKey,new Source("TGS","127.0.0.1"),new Destination("user","127.0.0.1"),new TS(4),new Lifetime("TGS","54000"));

            try {

                TGStoC = packageConstructor.getPackageTGStoC("Verify","Response",new Source("TGS","127.0.0.1") ,new Destination("user","127.0.0.1"),"0100",Auth,ioTKey,"127.0.0.3",new TS(4),ticketV,"V","tickkey TGS C","");
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            System.out.print("\n TGS 发送报文到客户端："+TGStoC);
            send(TGStoC.getBytes());
        }


    }
}