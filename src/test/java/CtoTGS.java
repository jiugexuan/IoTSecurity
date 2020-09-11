import com.fasterxml.jackson.core.JsonProcessingException;
import iotpackage.IoTKey;
import iotpackage.constructor.PackageConstructor;
import iotpackage.constructor.PackageParser;
import iotpackage.data.TS;
import iotpackage.data.autheticator.Authenticator;
import iotpackage.data.ciphertext.Lifetime;
import iotpackage.data.ticket.Ticket;
import iotpackage.destination.Destination;
import iotpackage.source.Source;
import org.junit.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class CtoTGS {
    @Test
    public void testCtoTGS() throws JsonProcessingException {

        String test=new PackageConstructor().getPackageCtoTGS(
                "Verify","Request",
                new Source("accoutTO","192.168.1.7"),new Destination("coueg","123547890"),
                "0005","1923.364.157","","5",
                new Authenticator(new Destination("coueg","127.569.321"), new Source("accoutTO","192.168.1.7"),new TS(3)),
                "1234578","7",
                ""
        );
        System.out.println(
                test
        );
    }

    @Test
    public void testTGStoC() throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {

        String test=new PackageConstructor().getPackageTGStoC(
                "Verify","Responce",
                new Source("accoutTO","192.168.1.7"),new Destination("coueg","123547890"),
                "0005",
                "1923.364.157",
                new IoTKey("12","456"),
                "192.168.4.3",
                new TS(4),
                new Ticket(new IoTKey("AccountAndTGS","2"),new Source("1","2"),new Destination("2","1"),new TS(2),new Lifetime("TGS","540000")),
                "CAndV",
                "1235794",
                ""
        );
        System.out.println(
                test
        );

        PackageParser packageParser=new PackageParser(test);
        packageParser.getCiphertext().printCiphertext();
        System.out.println(packageParser.getCipherPlaintext("1923.364.157",new String()));
    }
}
