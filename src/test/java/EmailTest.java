import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ContainerNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import iotpackage.Tools;
import iotpackage.constructor.CipherConstructor;
import iotpackage.constructor.PackageConstructor;
import iotpackage.constructor.PackageParser;
import iotpackage.data.TS;
import iotpackage.data.autheticator.Authenticator;
import iotpackage.data.ciphertext.Ciphertext;
import iotpackage.data.fuction.Email;
import iotpackage.data.fuction.User.Receiver;
import iotpackage.data.fuction.User.Sender;
import iotpackage.data.fuction.emailList.EmailList;
import iotpackage.data.fuction.emailList.ReceiveList;
import iotpackage.data.fuction.emailList.SendList;
import iotpackage.destination.Destination;
import iotpackage.source.Source;
import org.junit.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;

public class EmailTest {
    @Test
    public void constructEmailInSafety() throws IOException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        String senderAccount="testUser1";
        String senderNickname="NickName";
        String receiverAccount="testUser2";
        String receiverNickname="NickName2";
        String title="the test email";
        String type="text";
        String context="aniuehaghakjhhfiahf";
        Sender sender=new Sender(senderAccount,senderNickname);
        Receiver receiver=new Receiver(receiverAccount,receiverNickname);
        //用例
        Email email=new Email("1",sender,receiver,title,
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()),
                type,context);
        email.printEmail();


        CipherConstructor cipherConstructor=new CipherConstructor("123456789");
        String cipherEmail=cipherConstructor.getPackageEmailToGson(email);
        System.out.println(cipherEmail);
        System.out.println(cipherConstructor.getCipherOfEmail(email,cipherConstructor.getCipherKey()));
        String emailSendJson=new PackageConstructor().getPackageEmailSendInSafety("Verify","Request",
                new Source("accoutTO","192.168.1.7"),new Destination("coueg","123547890"),
                "0005","123456789",email,"5",
                "12345678","");
        //System.oemailSendJson);
        PackageParser packageParser=new PackageParser(emailSendJson);
        Ciphertext  ciphertext=packageParser.getCiphertext();
        ciphertext.printCiphertext();

        String ciphertextPlaint=  packageParser.getCipherPlaintext("123456789", "");

        System.out.println(ciphertextPlaint);
        packageParser.getEmailThroughDecryt(ciphertextPlaint,"12345678", "").printEmail();;
    }

    @Test
    public void constructEmailSend() throws IOException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        String senderAccount="testUser1";
        String senderNickname="NickName";
        String receiverAccount="testUser2";
        String receiverNickname="NickName2";
        String title="the test email";
        String type="text";
        String context="aniuehaghakjhhfiahf";
        Sender sender=new Sender(senderAccount,senderNickname);
        Receiver receiver=new Receiver(receiverAccount,receiverNickname);
        //用例
        Email email=new Email("1",sender,receiver,title,
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()),
                type,context);
        email.printEmail();


        CipherConstructor cipherConstructor=new CipherConstructor("123456789");
        String cipherEmail=cipherConstructor.getPackageEmailToGson(email);
        System.out.println(cipherEmail);
        System.out.println(cipherConstructor.getCipherOfEmail(email,cipherConstructor.getCipherKey()));
        String emailSendJson=new PackageConstructor().getPackageEmailSend("Verify","Request",
                new Source("accoutTO","192.168.1.7"),new Destination("coueg","123547890"),
                "0005","123456789",email,"");
        //System.oemailSendJson);
        PackageParser packageParser=new PackageParser(emailSendJson);
        Ciphertext  ciphertext=packageParser.getCiphertext();
        ciphertext.printCiphertext();

        String ciphertextPlaint=  packageParser.getCipherPlaintext("123456789", "");
        Tools.jsonFormat(ciphertextPlaint);
        // System.out.println(ciphertextPlaint);
        //packageParser.getEmailThroughDecryt(ciphertextPlaint,"12345678", "").printEmail();;
    }


    @Test
    public void constructEmail() throws IOException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        String senderAccount="testUser1";
        String senderNickname="NickName";
        String receiverAccount="testUser2";
        String receiverNickname="NickName2";
        String title="the test email";
        String type="text";
        String context="aniuehaghakjhhfiahf";
        Sender sender=new Sender(senderAccount,senderNickname);
        Receiver receiver=new Receiver(receiverAccount,receiverNickname);
        //用例
        Email email=new Email("1",sender,receiver,title,
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()),
                type,context);
        email.printEmail();


        CipherConstructor cipherConstructor=new CipherConstructor("123456789");
        String cipherEmail=cipherConstructor.getPackageEmailToGson(email);
        System.out.println(cipherEmail);
        System.out.println(cipherConstructor.getCipherOfEmail(email,cipherConstructor.getCipherKey()));
        String emailSendJson=new PackageConstructor().getPackageEmailSendInSafety("Verify","Request",
                new Source("accoutTO","192.168.1.7"),new Destination("coueg","123547890"),
                "0005","123456789",email,"5",
                "12345678","");
        //System.oemailSendJson);
        PackageParser packageParser=new PackageParser(emailSendJson);
        Ciphertext  ciphertext=packageParser.getCiphertext();
        ciphertext.printCiphertext();

        String ciphertextPlaint=  packageParser.getCipherPlaintext("123456789", "");

        System.out.println(ciphertextPlaint);
        packageParser.getEmailThroughDecryt(ciphertextPlaint,"12345678", "").printEmail();;
    }

    @Test
    public void EmailCheck() throws JsonProcessingException {
        String emailSendJson=new PackageConstructor().getPackageEmailCheck("Verify","Request",
                new Source("accoutTO","192.168.1.7"),new Destination("coueg","123547890"),
                "0005","123456789","");
        Tools.jsonFormat(emailSendJson);
    }

    @Test
    public void EmailListCreateToJson() throws JsonProcessingException {
        EmailList emailList=new EmailList();
        String senderAccount="testUser1";
        String senderNickname="NickName";
        String receiverAccount="testUser2";
        String receiverNickname="NickName2";
        String title="the test email";
        String type="text";
        String context="aniuehaghakjhhfiahf";
        Sender sender=new Sender(senderAccount,senderNickname);
        Receiver receiver=new Receiver(receiverAccount,receiverNickname);
        //用例
        Email email=new Email("1",sender,receiver,title,
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()),
                type,context);
        //   email.printEmail();

        emailList.addEmail(email);
        emailList.addEmail(email);
        System.out.println(emailList.getListNumber());
        for(int i=0;i< emailList.getListNumber();++i){
            System.out.println();
            emailList.getEmailAtIndex(i).printEmail();
        }

//
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode array = mapper.createArrayNode();




        int j=0;
        while(j< emailList.getListNumber()){
            String test=new PackageConstructor().getPackageEmailToGson(emailList.getEmailAtIndex(j));
            System.out.println(test);
            array.add(test);
            j++;
        }

        System.out.println(new CipherConstructor().getPackageEmailListToGson(emailList));
    }

    @Test
    public void EmailLisPackage() throws IOException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {

        String senderAccount="testUser1";
        String senderNickname="NickName";
        String receiverAccount="testUser2";
        String receiverNickname="NickName2";
        String title="the test email";
        String type="text";
        String context="aniuehaghakjhhfiahf";
        Sender sender=new Sender(senderAccount,senderNickname);
        Receiver receiver=new Receiver(receiverAccount,receiverNickname);
        //用例
        Email email=new Email("1",sender,receiver,title,
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()),
                type,context);
        //   email.printEmail();

        Email email2=new Email("2",sender,receiver,title,
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()),
                type,context);

        SendList sendList=new SendList();
        sendList.addEmail(email);
        sendList.addEmail(email);
        ReceiveList receiveList=new ReceiveList();
        receiveList.addEmail(email2);
        receiveList.addEmail(email2);


        //造包
        String emailSendJson=new PackageConstructor().getPackageEmailListALL("Verify","Request",
                new Source("accoutTO","192.168.1.7"),new Destination("coueg","123547890"),
                "0005","123456789",sendList,receiveList,"");
        // Tools.jsonFormat(emailSendJson);
        String List=new PackageParser(emailSendJson).getCipherPlaintext("123456789",new String());

        System.out.println(List);
        ReceiveList receiveList1=new ReceiveList();
//        new PackageParser(emailSendJson).getEmailList(List,receiveList1);
        // receiveList1.printEmailList();
    }

    @Test
    public void hello() throws JsonProcessingException {
        String json = "{\"objects\" : [\"One\", \"Two\", \"Three\"]}";
        JsonNode arrNode = new ObjectMapper().readTree(json).get("objects");
        if (arrNode.isArray()) {
            for (JsonNode objNode : arrNode) {
                System.out.println(objNode);}
        }


    }
}
