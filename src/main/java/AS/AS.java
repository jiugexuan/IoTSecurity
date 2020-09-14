package AS;
import AS.mysql.DBExcute;
import AS.mysql.DataConn;
import access.IPInTheItem;
import com.fasterxml.jackson.core.JsonProcessingException;
import iotpackage.IoTKey;
import iotpackage.constructor.CipherConstructor;
import iotpackage.constructor.PackageConstructor;
import iotpackage.constructor.PackageParser;
import iotpackage.data.TS;
import iotpackage.data.ciphertext.Ciphertext;
import iotpackage.data.ciphertext.Lifetime;
import iotpackage.data.ticket.Ticket;
import iotpackage.destination.Destination;
import iotpackage.source.Source;
import securityalgorithm.RSAUtil;

import java.io.*;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Map;


public class AS implements Runnable {

    IPInTheItem ipInTheItem=new IPInTheItem();
    public String UserIP = ipInTheItem.getUserIP();
    public String ASIP = ipInTheItem.getASIP();
    public String TGSIP = ipInTheItem.getTGSIP();
    public String SERIP = ipInTheItem.getSERIP();
    public String Kctgs = "963852741";
    public String KeyTGS = "741852963";
    public String UserAccount = null;
    Map<String,String> keyMap= RSAUtil.createKeys(1024,ipInTheItem.getUserIP());
    String publicKey=keyMap.get("publicKey");
    String privateKey=keyMap.get("privateKey");

    final static int MAX_SIZE = 4096;
    private Socket socket;
    public AS(Socket socket) throws NoSuchAlgorithmException {
        this.socket=socket;
    }

    public void send(byte[] content){
        try {
            OutputStream socketOut = socket.getOutputStream();
            socketOut.write(content);
            socketOut.flush();
            // log.info("Messeag has been sent!");
            //FIXME
            socketOut.close();
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
            //FIXME
            socketIn.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            // log.warning("Fail to receive Messeag due to IOException!");
            e.printStackTrace();
        }
        return len;
    }
    @Override
    public void run() {
        //  System.out.print("hhh\n");
        byte[] bytes = new byte[4096];
        //接收到的报文
        //接收
        //C -> AS
        receive(bytes);
        System.out.println("接受的数据为："+new String(bytes));
        //报文
        String content= new String(bytes);
        PackageParser packageParser= null;

        Source sourceOfReceiverPackage=null;
        Destination destinationOfReceiverPackage=null;


        try {

            packageParser = new PackageParser(content);
            sourceOfReceiverPackage=packageParser.getSource();
        } catch (IOException e) {
            e.printStackTrace();
        }
           //判断注册报文或是登入报文
        if (packageParser.getProcess().contains("Register")){
            //注册
            System.out.println("\n注册 AS收到账户 "+packageParser.getAccount());
            System.out.println("\n注册 AS收到密码"+packageParser.getPassword());
            System.out.println("\n注册 AS收到昵称"+packageParser.getNickName());
            UserAccount = packageParser.getAccount();

            //TODO


            try {
                //注册插入数据库
                String code = DBExcute.register(packageParser.getAccount(), packageParser.getPassword(), packageParser.getNickName(),packageParser.getSecurityQuestion(),packageParser.getSecurityAnswer());
                System.out.print(code);
                send(code.getBytes());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            //登入
            System.out.println("AS IdC:"+packageParser.getIdC());

            try {
                String code = DBExcute.logIn(packageParser.getIdC());
                System.out.print("\n AS 返回code："+code);
                if (code.equals("0102")) {
                    send(code.getBytes());
                }else {
                    Source source=new Source("AS",ASIP);
                    //FIXME
                  //  Destination destination=new Destination(UserAccount,UserIP);
                    TS ts = new TS(2);
                    Lifetime lifetime = new Lifetime("2","54000");
                    IoTKey ioTKey = new IoTKey("KeyC TGS",Kctgs);
                    Ticket ticketTgs = new Ticket(ioTKey,source,sourceOfReceiverPackage.changeToDestination(),ts,lifetime);
                    String AStoC = null;
                    PackageConstructor packageConstructor = new PackageConstructor();
                    try {
                        //ticketkey keyTGS
                        //cipherkey K c 就是用户md5
                        AStoC = packageConstructor.getPackageAStoCLogin("Verify","Response",source,sourceOfReceiverPackage.changeToDestination(),"0100",UserIP,code, ioTKey,TGSIP,ts,lifetime,ticketTgs,"2",KeyTGS,privateKey,publicKey);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    System.out.print("\n AS 发送报文到客户端："+AStoC);
                    send(AStoC.getBytes());
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }

    }
}