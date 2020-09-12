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

        ticketText.getKey().printIoTKey();

        String Auth = ticketText.getKey().getContext();

        try {
            authenticator = packageParser.getAuthenticator(info,Auth,"");
        } catch (IOException | IllegalBlockSizeException | InvalidKeyException | BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }

        authenticator.printfAuthenticator();

    }
}