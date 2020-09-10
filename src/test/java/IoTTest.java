import com.fasterxml.jackson.core.JsonProcessingException;
import iotpackage.IoTKey;
import iotpackage.constructor.PackageConstructor;

import iotpackage.constructor.PackageParser;
import iotpackage.data.TS;
import iotpackage.data.ciphertext.Lifetime;
import iotpackage.data.ticket.Ticket;
import iotpackage.destination.Destination;
import iotpackage.source.Source;
import org.junit.Test;

import java.io.IOException;

public class IoTTest {
    @Test
    public void testPackageConstructor() throws IOException {
        PackageConstructor packageConstructor=new PackageConstructor();
        Source source=new Source("user1","127.0.0.1");
        Destination destination=new Destination("AS","127.3.4.1");
        //Date data=new Data();
       String test=packageConstructor.getPackageCtoASLogin("Verify","Request",source,destination,"0000","192.168.7.2","178.69.3.1",new TS(1),"");
       System.out.println(test);
        PackageParser packageParser=new PackageParser(test);
        System.out.println(packageParser.getProcess());
        System.out.println(packageParser.getOperation());
        Source testSource=new Source();
        packageParser.getSource(testSource);
        Destination testDE=new Destination();
        packageParser.getDestination(testDE);
        System.out.println("1");
    }

    @Test
   public void testTS() {
        TS ts=new TS(1);
        System.out.println(ts.getContext());
    }
    @Test
    public void testTGS() throws JsonProcessingException {
        Ticket tgs=new Ticket(new IoTKey("AccountAndTGS","2"),new Source("1","2"),new Destination("2","1"),new TS(1),new Lifetime("TGS","540000"));
        PackageConstructor packageConstructor=new PackageConstructor();
        System.out.println(packageConstructor.getPackageTikectToGson(tgs));
    }
    @Test
   public void Register() throws IOException {
        PackageConstructor packageConstructor=new PackageConstructor();
        Source source=new Source("user1","127.0.0.1");
        Destination destination=new Destination("AS","127.3.4.1");
        //Date data=new Data();
        String test=packageConstructor.getPackageCtoASRegist("Verify","Request",source,destination,"0000","test1","23498587","dalejo","who is you father","the 0/1","");
        System.out.println(test);

        PackageParser packageParser=new PackageParser(test);
        System.out.println(packageParser.getProcess());
        System.out.println(packageParser.getOperation());
        Source resultSource=new Source();
        packageParser.getSource(resultSource);
        resultSource.printSource();
        Destination resultDestionation=new Destination();
        packageParser.getDestination(resultDestionation);
        resultDestionation.printDetination();
        System.out.println(packageParser.getCode());
        System.out.println(packageParser.getAccount());
        System.out.println(packageParser.getPassword());
        System.out.println(packageParser.getNickName());
        System.out.println(packageParser.getSecurityQuestion());
        System.out.println(packageParser.getSecurityAnswer());
    }

    @Test
    public void Response() throws IOException {
        PackageConstructor packageConstructor=new PackageConstructor();
        Source source=new Source("user1","127.0.0.1");
        Destination destination=new Destination("AS","127.3.4.1");
        //Date data=new Data();
        String test=packageConstructor.getPackageServiceResponse("Verify","Request",source,destination,"0000","test1");
        System.out.println(test);

        PackageParser packageParser=new PackageParser(test);
        System.out.println(packageParser.getProcess());
        System.out.println(packageParser.getOperation());
        Source resultSource=new Source();
        packageParser.getSource(resultSource);
        resultSource.printSource();
        Destination resultDestionation=new Destination();
        packageParser.getDestination(resultDestionation);
        resultDestionation.printDetination();
        System.out.println(packageParser.getCode());
        System.out.println(packageParser.getSignContext());
        System.out.println(packageParser.getSignPublicKey());
    }
}

