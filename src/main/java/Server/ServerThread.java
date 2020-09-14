package Server;

import ServerSql.ServerSql;
import access.IPInTheItem;
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
import iotpackage.data.fuction.emailList.EmailList;
import iotpackage.data.fuction.emailList.ReceiveList;
import iotpackage.data.fuction.emailList.SendList;
import iotpackage.data.ticket.Ticket;
import iotpackage.destination.Destination;
import iotpackage.source.Source;
import securityalgorithm.DESUtil;
import securityalgorithm.RSAUtil;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Map;

public class ServerThread implements Runnable {

    IPInTheItem ipInTheItem=new IPInTheItem();

    public String UserIP = ipInTheItem.getUserIP();
    public String ASIP = ipInTheItem.getASIP();
    public String TGSIP = ipInTheItem.getTGSIP();
    public String SERIP = ipInTheItem.getSERIP();


    Map<String,String> keyMap= RSAUtil.createKeys(1024,ipInTheItem.getUserIP());
    String publicKey=keyMap.get("publicKey");
    String privateKey=keyMap.get("privateKey");

    public static String Kcv = "";
    public String KeyV = "852456789";
    public static String User = "";

    public static final String CHARCODE = "utf-8";
    private Socket socket;
    public ServerThread(Socket socket) throws NoSuchAlgorithmException {
        this.socket = socket;
    }

    public void send(byte[] content){
        try {
            OutputStream socketOut = socket.getOutputStream();
            socketOut.write(content);
            socketOut.flush();
            //FIXME

            // log.info("Messeag has been sent!");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            //log.warning("Fail to send Messeag due to IOException!");
           // e.printStackTrace();
        }
    }

    public int receive(byte[] result){
        int len = -1;
        try {
            InputStream socketIn = socket.getInputStream();
            len = socketIn.read(result, 0, 4048);
            //FIXME

            // log.info("Messeag has been received!");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            // log.warning("Fail to receive Messeag due to IOException!");
            //e.printStackTrace();
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
            SERtoC = packageConstructor.getPackageVtoCVerify("Verify","Response",new Source("SERVER",SERIP) ,new Destination(User,UserIP),"0100",Kcv,new TS(6),privateKey,publicKey);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return;
        }
        System.out.println("SER 发送报文到客户端："+SERtoC);
        send(SERtoC.getBytes());
        ServerSql.creatRevTable(User);
        ServerSql.creatSendTable(User);
    }

    public void ServerMailSend(String content) throws IOException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, SQLException {
        System.out.println("邮件发送部分："+content);
        PackageParser packageParser = new PackageParser(content);
        //source and destination
        Ciphertext mailcontent = packageParser.getCiphertext();
        String EncryptContent = mailcontent.getContext();
        System.out.println("SerMailSend的Kcv "+Kcv);
        String Decrypt= DESUtil.getDecryptString(EncryptContent,Kcv);
        System.out.println("解密获得 "+Decrypt);
        Email email = packageParser.getEmailFromGson(Decrypt);
        email.printEmail();
        String EmailTile = email.getTitle();
        String EmailConten = email.getContext();
        String Emailtime = email.getTime();
        Receiver EmailRecAccount = email.getReceiver();
        Sender EmailSenAccount = email.getSender();
        String EmailID = email.getId();
        String result = ServerSql.sendMail(EmailID,EmailSenAccount,EmailRecAccount,EmailTile,EmailConten);
        System.out.println("result "+result);
        String VtoC = null;
        PackageConstructor packageConstructor = new PackageConstructor();
        while (result != null){
            if (result.contains("0104")){
                VtoC = packageConstructor.getPackageServiceResponse("Service","Send",new Source("SERVER",SERIP),new Destination(User,UserIP),"0104",privateKey,publicKey);
                send(VtoC.getBytes());

            } else if (result.contains("1000")){
                VtoC = packageConstructor.getPackageServiceResponse("Service","Send",new Source("SERVER",SERIP),new Destination(User,UserIP),"1000",privateKey,publicKey);
                send(VtoC.getBytes());
            }
        }
    }

    public void ServerMailList(String content) throws IOException, SQLException, ClassNotFoundException {
        System.out.println("邮件列表部分："+content);
        PackageParser packageParser = new PackageParser(content);
        String user = packageParser.getAccount();
        ReceiveList receiveList = new ReceiveList();
        SendList sendList = new SendList();
        ServerSql.findRevAll(receiveList,user);
        ServerSql.findSendAll(sendList,new Sender(User,""));
        PackageConstructor packageConstructor = new PackageConstructor();
        String maillist = packageConstructor.getPackageEmailListALL("Service","ListRequest",new Source("SERVER",SERIP),new Destination(User,UserIP),"0000",Kcv,sendList,receiveList,privateKey,publicKey);
        send(maillist.getBytes());
        System.out.println("SERVER发送："+maillist);
    }

    public void ChangePWD(String content) throws IOException, SQLException {
        System.out.println("修改密码部分："+content);
        PackageParser packageParser = new PackageParser(content);
        String password = packageParser.getNewPassword();
        String user = packageParser.getAccount();
        System.out.println("password:"+password);
        String code = ServerSql.changePWD(user,password);
        System.out.println("code ser:"+code);
        String VtoC;
        PackageConstructor packageConstructor = new PackageConstructor();
        while (code != null){
            if (code.contains("0103")){
                VtoC = packageConstructor.getPackageServiceResponse("Service","Send",new Source("SERVER",SERIP),new Destination(User,UserIP),"0104",privateKey,publicKey);
                send(VtoC.getBytes());
            } else if (code.contains("0100")){
                VtoC = packageConstructor.getPackageServiceResponse("Service","Send",new Source("SERVER",SERIP),new Destination(User,UserIP),"1000",privateKey,publicKey);
                send(VtoC.getBytes());
            }
        }






    }

    public String ServerRec() throws IllegalBlockSizeException, NoSuchAlgorithmException, IOException, BadPaddingException, NoSuchPaddingException, InvalidKeyException, SQLException, ClassNotFoundException {
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
        if (operation.equals("Request")){
            ServerClientVerify(content);
        }else if (operation.contains("Send")){
            ServerMailSend(content);
        }else if (operation.contains("CheckRequest")){
            ServerMailList(content);
        }else if(operation.contains("ChangePWD")){
            ChangePWD(content);
        }else {
            return "0105 client发送到Server的报文operation获取异常";
        }

        return "接收报文正常";
    }

    @Override
    public void run() {
        try {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ServerRec();
        } catch (IllegalBlockSizeException | InvalidKeyException | NoSuchPaddingException | BadPaddingException | IOException | NoSuchAlgorithmException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



}
