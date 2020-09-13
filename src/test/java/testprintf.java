import iotpackage.Tools;
import iotpackage.data.TS;
import org.junit.Test;

public class testprintf {
    @Test
    public void printfElement(){
        TS ts=new TS(1);
    }

    @Test
    public void testString(){
        String hello="1";
        Tools.setString(hello);
        System.out.println(hello);
    }
}
