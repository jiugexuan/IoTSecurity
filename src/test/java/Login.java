import com.fasterxml.jackson.core.JsonProcessingException;
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
                "Verify","Responce",
                new Source("accoutTO","192.168.1.7"),new Destination("coueg","123547890"),
                "0005",
                "hello",
                "",
                ""
        );

        Tools.jsonFormat(test);
        System.out.println(new PackageParser(test).getNickName());
    }
}
