import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import iotpackage.data.TS;
import iotpackage.data.autheticator.Authenticator;
import iotpackage.destination.Destination;
import iotpackage.source.Source;
import org.junit.Test;

import iotpackage.*;

public class Map2Json {
    class Album {
        private String title;
        public Album(String title) {
            this.title = title;
        }
        public String getTitle() {
            return title;
        }
    }

    @Test
    public void authenticatorConstructTest() throws IOException {
        TS ts=new TS(1,"20200101");
        Destination id=new Destination("TGS","192.168.3.1");
        Source id2=new Source("2","192.168.3.1");
        Authenticator authenticator=new Authenticator(id,id2,ts);
        ObjectMapper mapper = new ObjectMapper();
        //String test=new String();
       String test= mapper.writeValueAsString(authenticator);
        System.out.println(test);
       // System.out.println(mapper);
    }

    /*
    * {
        "name": "aaa",
        "email": "aaa@aa.com",
        "age": 24,
        "pets": {
            "petName": "kitty",
            "petAge": 3
        },
        "skills": [ "java","linux","mysql"]
    }
    * */
    @Test
    public void jsonCreate(){

    }
}