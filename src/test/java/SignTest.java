import com.fasterxml.jackson.core.JsonProcessingException;
import iotpackage.Tools;
import iotpackage.constructor.PackageConstructor;
import iotpackage.data.fuction.Email;
import iotpackage.data.fuction.User.Receiver;
import iotpackage.data.fuction.User.Sender;
import iotpackage.data.fuction.emailList.ReceiveList;
import iotpackage.data.fuction.emailList.SendList;
import iotpackage.destination.Destination;
import iotpackage.source.Source;
import org.junit.Test;
import securityalgorithm.RSAUtil;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Map;

public class SignTest {
    @Test
    public void signVerify() throws NoSuchAlgorithmException, IOException {
        Map<String,String> keyMap=RSAUtil.createKeys(1024,"你好");
        System.out.println();

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
                "0005","123456789",sendList,receiveList,keyMap.get("privateKey"),keyMap.get("publicKey"));

        Tools.jsonFormat(emailSendJson);
        System.out.println(Tools.signatureVerigy(emailSendJson));
    }
}
