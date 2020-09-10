package iotpackage.constructor;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import iotpackage.IoTKey;
import iotpackage.data.TS;
import iotpackage.data.ciphertext.Lifetime;
import iotpackage.data.ticket.Ticket;
import iotpackage.destination.Destination;
import iotpackage.source.Source;
import securityalgorithm.DESUtil;

public class CipherConstructor {
   /* ObjectNode CipherNode ;
    String context;
    String id;*/
    //对包加密的key
    String cipherKey;

    //JsonNodeFactory 实例，可全局共享
    private JsonNodeFactory jsonNodeFactory = JsonNodeFactory.instance;
    //JsonFactory 实例，线程安全
    private JsonFactory jsonFactory = new JsonFactory();

    /**工具函数*/
    //source节点添加
    void setSourceNode(ObjectNode parentNode, Source source){

        ObjectNode sourceNode = jsonNodeFactory.objectNode();
        sourceNode.put("Id",source.getId());
        sourceNode.put("IP",source.getIp());
        parentNode.set("Source",sourceNode);
    }

    //destination节点添加
    void setDestionationNode(ObjectNode parentNode, Destination destination){
        ObjectNode destinationNode=jsonNodeFactory.objectNode();
        destinationNode.put("Id",destination.getId());
        destinationNode.put("IP",destination.getIp());
        parentNode.set("Destination",destinationNode);
    }

    //Key节点添加
    void setKeyNode(ObjectNode parentNode,IoTKey key){
        ObjectNode iotKeyNode=jsonNodeFactory.objectNode();
        iotKeyNode.put("Id",key.getId());
        iotKeyNode.put("Context",key.getContext());
        parentNode.set("Key",iotKeyNode);
    }

    //时间戳节点添加
    void setTSNode(ObjectNode parentNode,TS ts){
        ObjectNode TSNode=jsonNodeFactory.objectNode();
        TSNode.put("Id",ts.getId());
        TSNode.put("Context",ts.getContext());
        parentNode.set("TS",TSNode);

    }

    //Lifetime节点添加
    void setLifetimeNode(ObjectNode parentNode, Lifetime lifetime){
        ObjectNode TSNode=jsonNodeFactory.objectNode();
        TSNode.put("Id",lifetime.getId());
        TSNode.put("Context",lifetime.getContext());
        parentNode.set("Lifetime",TSNode);

    }


    public String getPackageTikectToGson(Ticket ticket) throws JsonProcessingException {
        ObjectNode rootNode = jsonNodeFactory.objectNode();
        //rootNode.put("Id",tgs.getId())
        setKeyNode(rootNode,ticket.getKey());
        setSourceNode(rootNode,ticket.getId());
        setDestionationNode(rootNode,ticket.getAd());
        setTSNode(rootNode,ticket.getTs());
        setLifetimeNode(rootNode,ticket.getLifetime());
        //ObjectMapper objectMapper = new ObjectMapper();

        //String signContext= objectMapper.writeValueAsString(rootNode);
        return new ObjectMapper().writeValueAsString(rootNode);
    };

    public String getCipherOfTicket(Ticket ticket,String ticketKey) throws JsonProcessingException {
        return DESUtil.getEncryptString(getPackageTikectToGson(ticket),ticketKey) ;
    }

    /****AS->C*****/
    public String constructCipherOfAStoC(IoTKey ioTKey, String idTGS, TS ts,
                                         Lifetime lifetime, Ticket tgs,String ticketKey) throws JsonProcessingException {

        ObjectNode rootNode = jsonNodeFactory.objectNode();
        setKeyNode(rootNode,ioTKey);
        rootNode.put("IDTGS",idTGS);
        setTSNode(rootNode,ts);
        setLifetimeNode(rootNode,lifetime);
        rootNode.put("Ticket",getCipherOfTicket(tgs,ticketKey));

       // return new ObjectMapper().writeValueAsString(rootNode);
        return DESUtil.getEncryptString(new ObjectMapper().writeValueAsString(rootNode),cipherKey);
    }

     public CipherConstructor() {

    }

    public CipherConstructor(String cipherKey) {
        this.cipherKey=cipherKey;
    }


}
