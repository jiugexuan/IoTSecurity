package AS;
import AS.mysql.DBExcute;
import AS.mysql.DataConn;
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

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;


public class AS implements Runnable {

    final static int MAX_SIZE = 4096;
    private Socket socket;
    public AS(Socket socket){
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
        //  System.out.print("hhh\n");
        byte[] bytes = new byte[4096];
        //接收到的报文
        //接收
        receive(bytes);
        System.out.println("接受的数据为："+new String(bytes));
        String content= new String(bytes);
        PackageParser packageParser= null;

        try {
            packageParser = new PackageParser(content);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (packageParser.getProcess().contains("Register")){

            System.out.println("\n注册 AS收到账户 "+packageParser.getAccount());
            System.out.println("\n注册 AS收到密码"+packageParser.getPassword());
            System.out.println("\n注册 AS收到昵称"+packageParser.getNickName());


            try {
                String code = DBExcute.register(packageParser.getAccount(), packageParser.getPassword(), packageParser.getNickName(),packageParser.getSecurityQuestion(),packageParser.getSecurityAnswer());
                System.out.print(code);
                send(code.getBytes());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        } else {
            System.out.println("AS Idc:"+packageParser.getIdC());

            try {
                String code = DBExcute.logIn(packageParser.getIdC());
                System.out.print("\n AS 返回code："+code);
                if (code.equals("0102")) {
                    send(code.getBytes());
                }else {
                    Source source=new Source("AS","127.0.0.1");
                    Destination destination=new Destination("user1","127.3.4.1");
                    TS ts = new TS(1);
                    Lifetime lifetime = new Lifetime("TGS","54000");
                    IoTKey ioTKey = new IoTKey("CandAS",code);
                    Ticket ticketTgs = new Ticket(ioTKey,source,destination,ts,lifetime);

                    String AStoC = null;
                    PackageConstructor packageConstructor = new PackageConstructor();
                    try {
                        AStoC = packageConstructor.getPackageAStoCLogin("Verify","Response",source,destination,"0100","12.0.1.5.",code,ioTKey,"127.0.0.3",ts,lifetime,ticketTgs,"65","tickkey AS TGS","");
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