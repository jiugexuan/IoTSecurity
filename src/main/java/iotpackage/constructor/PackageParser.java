package iotpackage.constructor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import iotpackage.IoTKey;
import iotpackage.data.TS;
import iotpackage.data.ciphertext.Ciphertext;
import iotpackage.data.ciphertext.Lifetime;
import iotpackage.data.ticket.Ticket;
import iotpackage.destination.Destination;
import iotpackage.source.Source;
import securityalgorithm.DESUtil;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class PackageParser {
    ObjectMapper objectMapper;
    String json;
    JsonNode rootNode;
    JsonNode infoNode;
    JsonNode signNode;
    JsonNode sourceNode;
    JsonNode destinationNode;
    JsonNode dataNode;
    //= objectMapper.readTree(jsonString);

    public PackageParser() {
         this.objectMapper = new ObjectMapper();
    }

    public PackageParser(String json) throws IOException {
        this();
        this.json= json;
        this.rootNode=objectMapper.readTree(this.json);
        this.infoNode=rootNode.get("Info");
        this.signNode=rootNode.get("Sign");
        this.sourceNode=infoNode.get("Source");
        this.destinationNode=infoNode.get("Destination");
        this.dataNode=infoNode.get("Data");
    }

    public String getInfoJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(infoNode);
    }


    public String getDataJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(dataNode);
    }

    public String getProcess(){
        if(this.json==null){
            return null ;
        }
       // JsonNode parentNode=rootNode.get("Info");
        return infoNode.get("Process").asText();

       // return ;
    }

    public String getOperation(){
        if(this.json==null){
            return null ;
        }
        // JsonNode parentNode=rootNode.get("Info");
        return infoNode.get("Operation").asText();
        // return ;
    }

    public void getSource(Source source){
        if(this.json==null){
            return;
        }
        source.setId(sourceNode.get("Id").asText());
        source.setIp(sourceNode.get("IP").asText());
    }

    public void getDestination(Destination destination){
        if(this.json==null){
            return;
        }
        destination.setId(destinationNode.get("Id").asText());
        destination.setIp(destinationNode.get("IP").asText());
    }

    public String getCode(){
        return dataNode.get("Code").asText();
    };

    public String getSignPublicKey(){
        return signNode.get("PublicKey").asText();
    };

    public String getSignContext(){
        return signNode.get("Context").asText();
    };


    /******注册**********/

    public String getAccount(){
        return dataNode.get("Account").asText();
    };

    public String getPassword(){
        return dataNode.get("Password").asText();
    };

    public String getNickName(){
        return dataNode.get("Nickname").asText();
    };

    public String getSecurityQuestion(){
        return dataNode.get("SecurityQuestion").asText();
    };

    public String getSecurityAnswer(){
        return dataNode.get("SecurityAnswer").asText();
    };

    /******登入*******/
    /*****C to AS*****/
    /*****AS to C*****/
    public Ciphertext getCiphertext(){
        return new Ciphertext(dataNode.get("Ciphertext").get("Context").asText(),dataNode.get("Ciphertext").get("Id").asText());
    }
    public String getIdC(){
        return dataNode.get("Idc").asText();
    }
    /***注意jsonc串**/
    @Deprecated
    public Ticket getTicket(String json,String ticketKey) throws IOException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        JsonNode jsonNode=objectMapper.readTree(json);
      //  String plaintext=jsonNode.get("Ticket").asText();
        String plaintext= DESUtil.getDecryptString(jsonNode.get("Ticket").asText(),ticketKey) ;

        System.out.println(plaintext);
        jsonNode=objectMapper.readTree(plaintext);
        JsonNode keyNode=jsonNode.get("Key");
        JsonNode sourceNode=jsonNode.get("Source");
        JsonNode destinationNode=jsonNode.get("Destination");
        JsonNode tsNode=jsonNode.get("TS");
        JsonNode lifetimeNode=jsonNode.get("Lifetime");

        return new Ticket(
                new IoTKey(keyNode.get("Id").asText(),keyNode.get("Context").asText()),
                new Source(sourceNode.get("Id").asText(),sourceNode.get("IP").asText()),
                new Destination(destinationNode.get("Id").asText(),destinationNode.get("IP").asText()),
                new TS(Integer.parseInt(tsNode.get("Id").asText()),tsNode.get("Context").asText()),
                new Lifetime(lifetimeNode.get("Id").asText(),lifetimeNode.get("Context").asText())
                );

                //,
     //return "";
        //return new Ciphertext(dataNode.get("Ciphertext").get("Context").asText(),dataNode.get("Ciphertext").get("Id").asText());
    }

    public String getTicketInSafety(String json,String ticketID) throws IOException {
        JsonNode jsonNode=objectMapper.readTree(json);
        //  String plaintext=jsonNode.get("Ticket").asText();
        JsonNode ticketNode=jsonNode.get("Ticket");
        ticketID=ticketNode.get("Id").asText();
        return ticketNode.get("Context").asText();
    }

    public Ticket getTicket(String json,String ticketKey,String ticketID) throws IOException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        JsonNode jsonNode=objectMapper.readTree(json);
        JsonNode ticketNode=jsonNode.get("Ticket");
        String plaintext = null;
        System.out.println("TODO\n");
        String testString=ticketNode.get("Context").asText();
        System.out.println("TODO\n");
        plaintext = DESUtil.getDecryptString(ticketNode.get("Context").asText(),ticketKey);
        ticketID=ticketNode.get("Id").asText();
        System.out.println(plaintext);
        jsonNode=objectMapper.readTree(plaintext);
        JsonNode keyNode=jsonNode.get("Key");
        JsonNode sourceNode=jsonNode.get("Source");
        JsonNode destinationNode=jsonNode.get("Destination");
        JsonNode tsNode=jsonNode.get("TS");
        JsonNode lifetimeNode=jsonNode.get("Lifetime");

        return new Ticket(
                new IoTKey(keyNode.get("Id").asText(),keyNode.get("Context").asText()),
                new Source(sourceNode.get("Id").asText(),sourceNode.get("IP").asText()),
                new Destination(destinationNode.get("Id").asText(),destinationNode.get("IP").asText()),
                new TS(Integer.parseInt(tsNode.get("Id").asText()),tsNode.get("Context").asText()),
                new Lifetime(lifetimeNode.get("Id").asText(),lifetimeNode.get("Context").asText())
        );

        //,
        //return "";
        //return new Ciphertext(dataNode.get("Ciphertext").get("Context").asText(),dataNode.get("Ciphertext").get("Id").asText());
    }


}
