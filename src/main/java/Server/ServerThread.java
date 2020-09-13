package Server;

import ServerSql.ServerSql;
import client.UI.Send;
import com.fasterxml.jackson.core.JsonProcessingException;
import iotpackage.IPInfo;
import iotpackage.IoTKey;
import iotpackage.constructor.PackageConstructor;
import iotpackage.constructor.PackageParser;
import iotpackage.data.TS;
import iotpackage.data.autheticator.Authenticator;
import iotpackage.data.ciphertext.Ciphertext;
import iotpackage.data.ciphertext.Lifetime;
import iotpackage.data.fuction.Email;
import iotpackage.data.fuction.User.Receiver;
import iotpackage.data.fuction.User.Sender;
import iotpackage.data.ticket.Ticket;
import iotpackage.destination.Destination;
import iotpackage.source.Source;
import securityalgorithm.DESUtil;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class ServerThread implements Runnable {
    public String UserIP = "127.0.0.1";
    public String SERIP = "127.0.0.1";
    public static String Kcv = "";
    public String KeyV = "852456789";
    public static String User = "";

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

    //


    public void ServerClientVerify(String content){

        PackageParser packageParser= null;
        Ticket ticketText = null;
        Authenticator authenticator =null;
        String info = null;
        Source source = new Source();
        try {
            packageParser = new PackageParser(content);
            packageParser.getSource(source);
            User = source.getId();
            System.out.println(info);
            info = packageParser.getDataJson();
            System.out.println(info);
            ticketText = packageParser.getTicket(info,KeyV,"ticketV");
        } catch (IOException | IllegalBlockSizeException | InvalidKeyException | BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
            return;
        }
        System.out.print("\nSer解密ticket：");
        ticketText.printfTicket();

        System.out.print("\nSer解密ticket得到的密码：");
        //kcv
        ticketText.getKey().printIoTKey();


        Kcv = ticketText.getKey().getContext();

        System.out.print("\nkcv 的密码："+Kcv+"\n");

        try {
            authenticator = packageParser.getAuthenticator(info,Kcv,"authenticator C");
        } catch (IOException | IllegalBlockSizeException | InvalidKeyException | BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
            return;
        }

        System.out.print("\nServer解密Authentic得到的：");
        authenticator.printfAuthenticator();
        //authenticator 中的时间戳
        String Atime = authenticator.getTs().getContext();
        //Ser-> C
        String SERtoC = null;
        PackageConstructor packageConstructor = new PackageConstructor();
        IoTKey ioTKey =new IoTKey("key C V",Kcv);
        Ticket ticketV = null;
        ticketV = new Ticket(ioTKey,new Source("SER",SERIP),new Destination("user",UserIP),new TS(4),new Lifetime("4","54000"));
        try {
            System.out.print("Kcv"+Kcv);
            SERtoC = packageConstructor.getPackageVtoCVerify("Verify","Response",new Source("SERVER",SERIP) ,new Destination(User,UserIP),"0100",Kcv,new TS(6),"");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return;
        }
        System.out.println("SER 发送报文到客户端："+SERtoC);
        send(SERtoC.getBytes());
        ServerSql.creatRevTable(User);
        ServerSql.creatSendTable(User);
    }

    public void ServerMailSend(String content) throws IOException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        System.out.println("邮件发送部分："+content);
        PackageParser packageParser = new PackageParser(content);
        //source and destination
        Ciphertext mailcontent = packageParser.getCiphertext();
        String EncryptContent = mailcontent.getContext();
        System.out.println("SerMailSend的Kcv"+Kcv);
        String Decrypt= DESUtil.getDecryptString(EncryptContent,Kcv);
        System.out.println("解密获得"+Decrypt);
        Email email = packageParser.getEmailFromGson(Decrypt);
        email.printEmail();
        String EmailTile = email.getTitle();
        String EmailConten = email.getContext();
        String Emailtime = email.getTime();
        Receiver EmailRecAccount = email.getReceiver();
        Sender EmailSenAccount = email.getSender();
        String EmailID = email.getId();
        String result = ServerSql.sendMail(EmailID,EmailSenAccount,EmailRecAccount,EmailTile,EmailConten);
        System.out.println("result"+result);
        String VtoC = null;
        PackageConstructor packageConstructor = new PackageConstructor();
        while (result != null){
            if (result.contains("0104")){
                VtoC = packageConstructor.getPackageServiceResponse("Service","Send",new Source("SERVER",SERIP),new Destination(User,UserIP),"0104","","");
                send(VtoC.getBytes());
                System.out.println("123");
            } else if (result.contains("1000")){
                VtoC = packageConstructor.getPackageServiceResponse("Service","Send",new Source("SERVER",SERIP),new Destination(User,UserIP),"0100","","");
                send(VtoC.getBytes());
            }
        }
    }

    public void ServerMailList(String content){
        System.out.println("邮件列表部分："+content);
    }

    public String ServerRec() throws IllegalBlockSizeException, NoSuchAlgorithmException, IOException, BadPaddingException, NoSuchPaddingException, InvalidKeyException {
        byte[] bytes = new byte[8000];
        receive(bytes);
        String content= new String(bytes);
        System.out.println("Server 接收的数据为："+content);
        PackageParser packageParser= null;
        String operation = null;
        try {
            packageParser = new PackageParser(content);
            operation = packageParser.getOperation();
        } catch (IOException e) {
            e.printStackTrace();
            return "0105 client发送到Server的报文异常";
        }
        System.out.println("操作码："+operation);
        if (operation.contains("Request")){
            ServerClientVerify(content);

        }else if (operation.contains("Send")){

            ServerMailSend(content);

        }else if (operation.contains("ListRequest")){
            ServerMailList(content);
        }else {
            return "0105 client发送到Server的报文operation获取异常";
        }
        return "接收报文正常";
    }

    @Override
    public void run() {
        try {
            ServerRec();
        } catch (IllegalBlockSizeException | InvalidKeyException | NoSuchPaddingException | BadPaddingException | IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }



}
