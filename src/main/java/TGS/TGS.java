package TGS;

import access.IPInTheItem;
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
import securityalgorithm.RSAUtil;

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
import java.util.Map;


public class TGS implements Runnable {
    IPInTheItem ipInTheItem=new IPInTheItem();
    public String UserIP = ipInTheItem.getUserIP();
    public String ASIP = ipInTheItem.getASIP();
    public String TGSIP = ipInTheItem.getTGSIP();
    public String SERIP = ipInTheItem.getSERIP();
    public String Kctgs = "";
    public String Kcv = "9517538246";
    public String KeyV = "852456789";
    public String KeyTGS = "741852963";
    Map<String,String> keyMap= RSAUtil.createKeys(1024,ipInTheItem.getUserIP());
    String publicKey=keyMap.get("publicKey");
    String privateKey=keyMap.get("privateKey");


    final static int MAX_SIZE = 4096;
    private Socket socket;
    public TGS(Socket socket) throws NoSuchAlgorithmException {
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
         Source sourceOfPackage=null;

        try {
            packageParser = new PackageParser(content);
            info = packageParser.getDataJson();
            sourceOfPackage=packageParser.getSource();
            System.out.println(info);
            ticketText = packageParser.getTicket(info,KeyTGS,"tickettgs");

        } catch (IOException | IllegalBlockSizeException | InvalidKeyException | BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }

        System.out.print("\n TGS解密ticket：");

        ticketText.printfTicket();

        System.out.print("\n TGS解密ticket得到的密码：");
        //kc tgs
        ticketText.getKey().printIoTKey();

        //ticket中的 AS生成的 密码
        Kctgs = ticketText.getKey().getContext();

        System.out.print("\n TGS c 的密码："+Kctgs+"\n");
        //C 发来的报文 ticket中 时间戳
        String Ctime = ticketText.getTs().getContext();
        // 报文 ticket 中 lifetime
        String lifetime = ticketText.getLifetime().getContext();

        try {
            authenticator = packageParser.getAuthenticator(info,Kctgs,"authenticator C");
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
            send("0105请求超时".getBytes());
        } else {

            //TGS -> C
            String TGStoC = null;
            PackageConstructor packageConstructor = new PackageConstructor();
            IoTKey ioTKey =new IoTKey("key C V",Kcv);
            Ticket ticketV = null;
            ticketV = new Ticket(ioTKey,new Source("TGS",TGSIP), sourceOfPackage.changeToDestination(), new TS(4),new Lifetime("4","54000"));
            try {
                TGStoC = packageConstructor.getPackageTGStoC("Verify","Response",new Source("TGS",TGSIP) ,sourceOfPackage.changeToDestination(),"0100",Kctgs,ioTKey,SERIP,new TS(4),ticketV,"V",KeyV,privateKey,publicKey);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            System.out.print("\n TGS 发送报文到客户端："+TGStoC);

            send(TGStoC.getBytes());
        }


    }
}