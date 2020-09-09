import iotpackage.constructor.PackageConstructor;

import iotpackage.data.TS;
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
       String test=packageConstructor.packageCtoAS("Verify","Request",source,destination,"0000","192.168.7.2","178.69.3.1",new TS(1),"ABCJDLJO");
       System.out.println(test);
    }

    @Test
   public void testTS() {
        TS ts=new TS(1);
        System.out.println(ts.getContext());
    }
    @Test
    public void hello2(){

    }
    @Test
   public void hello3(){

    }
}

