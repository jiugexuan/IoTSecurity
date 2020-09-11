import com.fasterxml.jackson.core.JsonProcessingException;
import iotpackage.constructor.PackageConstructor;
import iotpackage.data.TS;
import iotpackage.data.autheticator.Authenticator;
import iotpackage.destination.Destination;
import iotpackage.source.Source;
import org.junit.Test;

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
}
