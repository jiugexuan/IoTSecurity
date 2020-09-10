package iotpackage.constructor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import iotpackage.destination.Destination;
import iotpackage.source.Source;

import java.io.IOException;

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


}
