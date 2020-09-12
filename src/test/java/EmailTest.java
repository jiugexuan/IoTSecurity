import iotpackage.data.fuction.Email;
import iotpackage.data.fuction.User.Receiver;
import iotpackage.data.fuction.User.Sender;
import org.junit.Test;

import java.text.SimpleDateFormat;

public class EmailTest {
    @Test
    public void constructEmail(){
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


    }
}
