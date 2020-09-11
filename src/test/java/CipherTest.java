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
import org.junit.Test;
import securityalgorithm.DESUtil;
import securityalgorithm.MD5Util;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class CipherTest {

    @Test
    public void ticketDES() throws JsonProcessingException {
        Ticket tgs=new Ticket(new IoTKey("AccountAndTGS","2"),new Source("1","2"),new Destination("2","1"),new TS(1),new Lifetime("TGS","540000"));
        CipherConstructor cipherConstructor=new CipherConstructor();
        String jsontgs=cipherConstructor.getPackageTikectToGson(tgs);
        System.out.println(jsontgs);
        String result=cipherConstructor.getCipherOfTicket(tgs,"aleijgajgl");
        System.out.println(result);
        try {
            if(new String(DESUtil.getDecryptString(result,"aleijgajgl")).contentEquals(jsontgs) ){
                System.out.println(true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void AStoCPackageTest() throws IOException {

        String pulickey="MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCR52yBgDacpdlQ4m6kaKzKEvfG" +
                "58U6JBYJNVtI11Sevqcpx5PfZAMkqIV1HoOj0LDKcuiDzBLTtFv4bbUVvtl79C-J_t2YVt-HblV-aNtBGhL2-7B4P" +
                "PZfYwcRkV5kGUhtrTP-ZWT9KOYH6k8UIFenB8Q2YNxtvuhMrKjBiLFxQcNyBPJKLd5JPpCu-NH34yqag5F6tTRITD" +
                "XT8h_HpAj6HEpglENyZoaSf1MZKvVKl7UBH7fFpQabtCJrEEqtiuB4SqwVKrDg7a_Zv2z9eC6WvIugyjNhIa6WDyR" +
                "DSmHviD-fc5ieT2dvMmb3G1fDVWM8TysUgV2FaIxKcz9ESSg_AgMBAAECggEACDJ6VCRB7TccKIEwOihvXMMIDLLE" +
                "z-lrG4VV5ZfDd0-60IwX8LsLuimFpfja6H83D4i5K35xqFc_u4teWyRnz6D-csBQ7tgiotc-EmYNu3CdlCa72WWDa" +
                "gSERyZlqTYKba8Hdsl3jQmZMuSlILpYclfrXCrA96F8J6KjsIvffD9h5a_N15Kpai1OpBh4y5o2_CHkUGoOQEGYzo" +
                "-EkUOXesVA8V698rd45SWcwXmu6ZyNpFjmeicT2q1IWiB9FP-njg7_ybPW44qVYrIZrEOaVajWU2XBxyFaxXBqa2p" +
                "yGwInazcL_2Ff5Hzx7SUbUmSSoDF3FTqd_cGkuYS4IKSlAQKBgQDEGy8TCbmudPGJXptxnBWHZyib-2mKxWVanHAT" +
                "Kpd51xkRRt5P6yyh_cwLK5tBa9PADo0BxDQxB3pyPn75yR1OlNCuDFcwB2-AH9VCvbEq_p8PCmdy54JwmVvCiVjH5" +
                "JYn8XTDgX20xqCbbuFZAh3gmG895uE518c9oRw_8m_a_wKBgQC-dx3vbOqXyLWKe_Nayo9M0spPlWErXex70HIeW6" +
                "6lOKd3fiFZWqE81nAfzfch6V7qdLDUG3mYIkKnu5i9qzmp9wt0UkXng98uqfWKulXIIUfmQLjiV8DC3k3hqyTOpLj" +
                "pR3rI_C9VFYfBGMWS4gqGV2NiNtLy2BE2hjKW7xDywQKBgC-yfOygfO0VvHmw7RXg5MueJ55os-7wvAoh6pB-14Dm" +
                "9E3jcsb1aFGuLSa6YHS1CTe5UkqPsjIKo5mN4cxXQi2OvILYtYwVAuWi2Hf3M1RFweIioa6s6GkCo_LY_SMBUhQ9A" +
                "i1bHXwOo5mqilzfyuyJpuTjdvxeozM2MGzNRq95AoGACxBiVcku63hGS3Ad19VSc7T--ZaE2X8QQLUAHBFZWNGssL" +
                "1L9KPWH_GY-8_8HiUvVVFIAFpEOvkqhBHaspHivKPUL4Kj-unnKg_HarKeTwzX32E9HDDayrcdMRG_Bp38-9giItC" +
                "7cybYBviIaZrl353t8QOcR20TuuYaxDfI9UECgYBCkTYl6V2KzZaIDEKIVqkWwVnlaCd8BoHAH40BACZYEF1VQDV7" +
                "NqvyPEuG0TMoEsUbaQsLgeygUoqeH6puxGhNu3ZmbeguCIL4lmRxotiqsZNq2XAb-VC7Wc3BVukPiEjzyyhSkmpUY" +
                "4ltylX4TyWjSDBV_LyDeSeSRQgJ21QFfA";

        String cipherKey="testCipherKey";
        CipherConstructor cipherConstructor=new CipherConstructor(cipherKey);
        //Ticket tgs
        String ticketKey="aleijgajgl";
        Ticket tgs=new Ticket(new IoTKey("AccountAndTGS","2"),new Source("1","2"),new Destination("2","1"),new TS(1),new Lifetime("TGS","540000"));

       // String jsontgs=cipherConstructor.getPackageTikectToGson(tgs);
      //  System.out.println(jsontgs);
       // String result=cipherConstructor.getCipherOfTicket(tgs,ticketKey);
        //System.out.println(result);
//        if(new String(DESUtil.getDecryptString(result,ticketKey)).contentEquals(jsontgs) ){
//            System.out.println(true);
//        }

       /* Source source=new Source("user1","127.0.0.1");
        Destination destination=new Destination("AS","127.3.4.1");
        IoTKey key=new IoTKey("AccountAndTGS","12345678957");*/

        PackageConstructor packageConstructor=new PackageConstructor();
        Source source=new Source("AS","127.0.0.1");
        Destination destination=new Destination("C","127.3.4.1");
        String passwordmd5= MD5Util.md5("1234567");
        //Date data=new Data();
//        public String  getPackageAStoCLogin(String process, String operation, Source source,
//                Destination destination, String code, String idC,
//                String cipherKey,IoTKey CandTGS, String idTGS, TS ts, Lifetime lifetime,
//                Ticket tgs,String ticketKey,String publickey) throws JsonProcessingException {
        String test=packageConstructor.getPackageAStoCLogin("Verify","Response",source,destination,"0000",
                destination.getIp(),passwordmd5,new IoTKey("CandTGS","jaoejeao"),"192.367.2",new TS(2),
                new Lifetime("j","540000"),tgs,"heloo",ticketKey,pulickey);
        System.out.println(test);

        PackageParser packageParser=new PackageParser(test);

        //
        Ciphertext ciphertext=packageParser.getCiphertext();
        ciphertext.printCiphertext();

        String plaintext= null;
        try {
            plaintext = DESUtil.getDecryptString(ciphertext.getContext(),passwordmd5);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        System.out.println(plaintext);

        String ticketID= "";
        Ticket ticket= null;
        try {
            ticket = packageParser.getTicket(plaintext,ticketKey,ticketID);
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        System.out.println();
        ticket.printfTicket();
       // System.out.println();
        //解析包

        //String test=cipherConstructor.constructCipherOfAStoC(key,"192.168.37",new TS(2),new Lifetime("2","54000"),tgs,ticketKey);
    //    System.out.println(test);
      //  System.out.println(DESUtil.getDecryptString(test,cipherKey));
    }

    @Test
    public void authenticator(){
      //  System.out.println(new CipherConstructor().getPackageAuthenticatorToGson());
    }
}
