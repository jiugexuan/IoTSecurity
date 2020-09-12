import com.fasterxml.jackson.core.JsonProcessingException;
import iotpackage.constructor.CipherConstructor;
import iotpackage.constructor.PackageConstructor;
import iotpackage.constructor.PackageParser;
import iotpackage.data.TS;
import iotpackage.data.autheticator.Authenticator;
import iotpackage.data.ciphertext.Ciphertext;
import iotpackage.data.fuction.Email;
import iotpackage.data.fuction.User.Receiver;
import iotpackage.data.fuction.User.Sender;
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
        Email email=new Email(sender,receiver,title,
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()),
                type,context);
        email.printEmail();


        CipherConstructor cipherConstructor=new CipherConstructor("123456789");
        String cipherEmail=cipherConstructor.getPackageEmailToGson(email);
        System.out.println(cipherEmail);
        System.out.println(cipherConstructor.getCipherOfEmail(email,cipherConstructor.getCipherKey()));
        String emailSendJson=new PackageConstructor().getPackageCtoVEmailSend("Verify","Request",
                new Source("accoutTO","192.168.1.7"),new Destination("coueg","123547890"),
                "0005","123456789",email,"5",
                "12345678","");
        //System.oemailSendJson);
        PackageParser packageParser=new PackageParser(emailSendJson);
        Ciphertext  ciphertext=packageParser.getCiphertext();
        ciphertext.printCiphertext();

          String ciphertextPlaint=  packageParser.getCipherPlaintext("123456789", "");

          System.out.println(ciphertextPlaint);
          packageParser.getEmail(ciphertextPlaint,"12345678", "").printEmail();;




    }
}
