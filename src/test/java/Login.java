import com.fasterxml.jackson.core.JsonProcessingException;
import iotpackage.IPInfo;
import iotpackage.IoTKey;
import iotpackage.Tools;
import iotpackage.constructor.PackageConstructor;
import iotpackage.constructor.PackageParser;
import iotpackage.data.TS;
import iotpackage.data.ciphertext.Lifetime;
import iotpackage.data.ticket.Ticket;
import iotpackage.destination.Destination;
import iotpackage.source.Source;
import org.junit.Test;

import java.io.IOException;

public class Login {
    @Test
    public void loginTest() throws IOException {
        String test=new PackageConstructor().getPackageServiceResponse(
                "Verify","Response",
                new Source("source","192.168.1.7"),new Destination("destination","123547890"),
                "0005",
                "hello",
                "",
                ""
        );

        Tools.jsonFormat(test);
        System.out.println(new PackageParser(test).getNickName());
        Source jk= new PackageParser(test).getSource();
        Destination jk2=new PackageParser(test).getDestination();
        String test2=new PackageConstructor().getPackageServiceResponse(
                "Verify","Response",
                jk2.changeToSource(),
                jk.changeToDestination(),
                "0005",
                "hello",
                "",
                ""
        );
        Tools.jsonFormat(test2);

        String test3=new PackageConstructor().getPackageServiceResponse(
                "Verify","Response",
                jk2.changeToSource(),
                jk.changeToDestination(),
                "0005",
                "hello",
                "",
                ""
        );
        Tools.jsonFormat(test3);
    }
}
