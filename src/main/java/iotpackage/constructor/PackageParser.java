package iotpackage.constructor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import iotpackage.IoTKey;
import iotpackage.Tools;
import iotpackage.data.TS;
import iotpackage.data.autheticator.Authenticator;
import iotpackage.data.ciphertext.Ciphertext;
import iotpackage.data.ciphertext.Lifetime;
import iotpackage.data.fuction.Email;
import iotpackage.data.fuction.User.Receiver;
import iotpackage.data.fuction.User.Sender;
import iotpackage.data.fuction.emailList.EmailList;
import iotpackage.data.ticket.Ticket;
import iotpackage.destination.Destination;
import iotpackage.source.Source;
import org.apache.commons.text.StringEscapeUtils;
import securityalgorithm.DESUtil;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Vector;

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

    /***
     * 获得密文结果
     * @param cipherKey
     * @param cipherID
     * @return
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidKeyException
     */
    public String getCipherPlaintext(String cipherKey,String cipherID) throws NoSuchPaddingException, NoSuchAlgorithmException, IOException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        Ciphertext ciphertext=getCiphertext();
        cipherID=ciphertext.getId();
        return DESUtil.getDecryptString(ciphertext.getContext(),cipherKey);
    }

    public String getKey(String json) throws JsonProcessingException {
        JsonNode jsonNode=objectMapper.readTree(json);
        JsonNode keyNode=jsonNode.get("Key");
        return keyNode.get("Context").asText();
    }

    public String getTS(String json) throws JsonProcessingException {
        JsonNode jsonNode=objectMapper.readTree(json);
        JsonNode tsNode=jsonNode.get("TS");
        return tsNode.get("Context").asText();
    }


    public String getIdC(){
        return dataNode.get("IdC").asText();
    }
    /***注意json串**/
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

    //ticket获取
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
        String plaintext = null;;
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

    //Authenticator获取
    public String getAuthenticatorInSafety(String json,String authenticatorID) throws IOException {
        JsonNode jsonNode=objectMapper.readTree(json);
        //  String plaintext=jsonNode.get("Ticket").asText();
        JsonNode authenticatorNode=jsonNode.get("Authenticator");
        authenticatorID=authenticatorNode.get("Id").asText();
        return authenticatorNode.get("Context").asText();
    }
    public Authenticator getAuthenticator(String json,String authenticatorKey,String authenticatorID) throws IOException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        JsonNode jsonNode=objectMapper.readTree(json);
        JsonNode ticketNode=jsonNode.get("Authenticator");
        String plaintext = null;
        plaintext = DESUtil.getDecryptString(ticketNode.get("Context").asText(),authenticatorKey);
        authenticatorID=ticketNode.get("Id").asText();
        System.out.println(plaintext);
        jsonNode=objectMapper.readTree(plaintext);
        JsonNode destinationNode=jsonNode.get("Destination");
        JsonNode sourceNode=jsonNode.get("Source");
        JsonNode tsNode=jsonNode.get("TS");


        return new Authenticator(
                new Destination(destinationNode.get("Id").asText(),destinationNode.get("IP").asText()),
                new Source(sourceNode.get("Id").asText(),sourceNode.get("IP").asText()),
                new TS(Integer.parseInt(tsNode.get("Id").asText()),tsNode.get("Context").asText())
        );

    }


    public Email getEmailThroughDecryt(String json, String emailKey, String emailID) throws IOException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        JsonNode jsonNode=objectMapper.readTree(json);
        JsonNode ticketNode=jsonNode.get("Email");
        String plaintext = null;
        plaintext = DESUtil.getDecryptString(ticketNode.get("Context").asText(),emailKey);
        // authenticat=ticketNode.get("Id").asText();
        System.out.println(plaintext);
        jsonNode=objectMapper.readTree(plaintext);
        JsonNode senderNode=jsonNode.get("Sender");
        JsonNode receiveNode=jsonNode.get("Receiver");
        //JsonNode tsNode=jsonNode.get("TS");

        return new Email(
                jsonNode.get("Id").asText(),
                new Sender(senderNode.get("Account").asText(),senderNode.get("Nickname").asText()),
                new Receiver(receiveNode.get("Account").asText(),receiveNode.get("Nickname").asText()),
                jsonNode.get("Title").asText(),
                jsonNode.get("Time").asText(),
                jsonNode.get("Type").asText(),
                jsonNode.get("Context").asText()
        );


    }

    //TODO 解析出错
    public EmailList getEmailList(String json,EmailList emailList) throws JsonProcessingException {
        JsonNode arrNode =  new ObjectMapper().readTree(json).get(emailList.getClass().getSimpleName());

        //JsonNode jsonNode = objectMapper.readTree(json);
        //JsonNode ticketNode = jsonNode.get();

        // String str1 = "{\"resourceId\":\"dfead70e4ec5c11e43514000ced0cdcaf\",\"properties\":{\"process_id\":\"process4\",\"name\":\"\",\"documentation\":\"\",\"processformtemplate\":\"\"}}";

        // ；      unescapeJavaScript(json);

       // String standardizationjson = StringEscapeUtils.unescapeJson(objectMapper.writeValueAsString(arrNode)) ;

       // String tmp = StringEscapeUtils.unescapeJava(objectMapper.writeValueAsString(arrNode));
        //String tmp2 = StringEscapeUtils.unescapeJava(objectMapper.writeValueAsString(tmp));
      //  JsonNode jsonNode=new ObjectMapper().readTree(tmp2);
        //Tools.jsonFormat(jsonNode.asText());
        // String tmp = StringEscapeUtils.unescapeJava(objectMapper.writeValueAsString(arrNode));
        // ObjectMapper objectMapper = new ObjectMapper();
        // ObjectMapper mapper = new ObjectMapper();
        //Vector<Email> lendReco = mapper.readValue(objectMapper.writeValueAsString(arrNode),new TypeReference<Vector<Email>>() { });
        //  EmailList list = objectMapper.readValue(json,EmailList.class);
            //JsonNode jsonNode=arrNode.get(0);
//System.out.println(tmp);
        JsonNode jsonNode;
        for(int i=0;i<arrNode.size();i++){
            jsonNode=arrNode.get(i);
         //   String standardizationjson = StringEscapeUtils.unescapeJson(objectMapper.writeValueAsString(jsonNode)) ;
            Tools.jsonFormat(new ObjectMapper().writeValueAsString(jsonNode));
            //Email email=getEmailFromGsonWithoutID(standardizationjson);
            JsonNode senderNode=jsonNode.get("Sender");
            JsonNode receiveNode=jsonNode.get("Receiver");

             Email email=new Email(
                     jsonNode.get("Id").asText(),
                     new Sender(senderNode.get("Account").asText(),senderNode.get("Nickname").asText()),
                     new Receiver(receiveNode.get("Account").asText(),receiveNode.get("Nickname").asText()),
                     jsonNode.get("Title").asText(),
                     jsonNode.get("Time").asText(),
                     jsonNode.get("Type").asText(),
                     jsonNode.get("Context").asText()
             );
          // email.printEmail();
            emailList.addEmail(email);
        }

        return emailList;
    }

    public Email getEmailFromGson(String json) throws JsonProcessingException {
        JsonNode jsonNode=objectMapper.readTree(json).get("Email");
        JsonNode senderNode=jsonNode.get(Sender.class.getSimpleName());
        JsonNode receiveNode=jsonNode.get(Receiver.class.getSimpleName());
        return new Email(
                jsonNode.get("Id").asText(),
                new Sender(senderNode.get("Account").asText(),senderNode.get("Nickname").asText()),
                new Receiver(receiveNode.get("Account").asText(),receiveNode.get("Nickname").asText()),
                jsonNode.get("Title").asText(),
                jsonNode.get("Time").asText(),
                jsonNode.get("Type").asText(),
                jsonNode.get("Context").asText()
        );
    }

    public Email getEmailFromGsonWithoutID(String json) throws JsonProcessingException {
        JsonNode jsonNode=objectMapper.readTree(json);
        JsonNode senderNode=jsonNode.get(Sender.class.getSimpleName());
        JsonNode receiveNode=jsonNode.get(Receiver.class.getSimpleName());
        return new Email(
                jsonNode.get("Id").asText(),
                new Sender(senderNode.get("Account").asText(),senderNode.get("Nickname").asText()),
                new Receiver(receiveNode.get("Account").asText(),receiveNode.get("Nickname").asText()),
                jsonNode.get("Title").asText(),
                jsonNode.get("Time").asText(),
                jsonNode.get("Type").asText(),
                jsonNode.get("Context").asText()
        );
    }
}
