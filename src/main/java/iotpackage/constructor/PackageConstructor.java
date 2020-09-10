package iotpackage.constructor;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import iotpackage.IoTKey;
import iotpackage.data.ciphertext.Lifetime;
import iotpackage.data.ticket.Ticket;
import iotpackage.destination.Destination;
import iotpackage.source.Source;

import iotpackage.data.*;

import java.io.IOException;
import java.security.PublicKey;

public class PackageConstructor {

    //JsonNodeFactory 实例，可全局共享
    private JsonNodeFactory jsonNodeFactory = JsonNodeFactory.instance;
    //JsonFactory 实例，线程安全
    private JsonFactory jsonFactory = new JsonFactory();

    public PackageConstructor() {
        this.jsonNodeFactory = JsonNodeFactory.instance;
        this.jsonFactory  = new JsonFactory();
    }

    /**工具函数*/
    //source节点添加
    void setSourceNode(ObjectNode parentNode,Source source){

        ObjectNode sourceNode = jsonNodeFactory.objectNode();
        sourceNode.put("Id",source.getId());
        sourceNode.put("IP",source.getIp());
        parentNode.set("Source",sourceNode);
    }

    //destination节点添加
    void setDestionationNode(ObjectNode parentNode,Destination destination){
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
    void setLifetime(ObjectNode parentNode, Lifetime lifetime){
        ObjectNode TSNode=jsonNodeFactory.objectNode();
        TSNode.put("Id",lifetime.getId());
        TSNode.put("Context",lifetime.getContext());
        parentNode.set("Lifetime",TSNode);

    }

    /**Ticket json格式生成****/
    /**
     * TGS /V
     * ***/
    public String getPackageTikectToGson(Ticket ticket) throws JsonProcessingException {
        ObjectNode rootNode = jsonNodeFactory.objectNode();
        //rootNode.put("Id",tgs.getId())
        setKeyNode(rootNode,ticket.getKey());
        setSourceNode(rootNode,ticket.getId());
        setDestionationNode(rootNode,ticket.getAd());
        setTSNode(rootNode,ticket.getTs());
        setLifetime(rootNode,ticket.getLifetime());
        //ObjectMapper objectMapper = new ObjectMapper();

        //String signContext= objectMapper.writeValueAsString(rootNode);
        return new ObjectMapper().writeValueAsString(rootNode);
    };


    /***服务认证阶段***/
    /***C to AS
     * @param process
     * @param operation
     * @param source
     * @param destination
     * @param code
     * @param idC 用户IP地址
     * @param idTGS TGS的IP地址
     * @param ts 为时间戳 ,如2020-6-15 10:00:00
     * ****/
    public String  getPackageCtoASLogin(String process, String operation, Source source, Destination destination, String code, String idC, String idTGS, TS ts, String publickey) throws IOException {
        ObjectNode rootNode = jsonNodeFactory.objectNode();
        ObjectNode infoNode = jsonNodeFactory.objectNode();

        ObjectNode signNode = jsonNodeFactory.objectNode();

        infoNode.put("Process",process);
        infoNode.put("Operation",operation);

        //source节点添加
        setSourceNode(infoNode,source);

        //destination节点添加
        setDestionationNode(infoNode,destination);

        //Data字段
        ObjectNode dataNode = jsonNodeFactory.objectNode();
        dataNode.put("Code",code);
        dataNode.put("Idc",idC);
        dataNode.put("IdTGS",idTGS);
        //时间戳节点添加
        setTSNode(dataNode,ts);

        infoNode.set("Data",dataNode);
        rootNode.set("Info",infoNode);

        //sign签名
        ObjectMapper objectMapper = new ObjectMapper();

        String signContext= objectMapper.writeValueAsString(infoNode);

        //TODO
        //签名算法
       // String signResult=signContext;
        //
        signNode.put("Context","");
        signNode.put("PublicKey",publickey);

        rootNode.set("Sign",signNode);

       // rootNode.put("1","2");



        return objectMapper.writeValueAsString(rootNode);
      //  String result = "";
        /*JsonGenerator generator =
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeTree(generator, rootNode);*/
    }

    /***AS to C
     * @param process
     * @param operation
     * @param source
     * @param destination
     * @param code
     * @param idC 用户IP地址
     * @param idTGS TGS的IP地址
     * @param ts 为时间戳 ,如2020-6-15 10:00:00
     * ****/
    public String  getPackageAStoCLogin(String process, String operation, Source source, Destination destination, String code, String idC, String idTGS, TS ts, String publickey) throws IOException {
        ObjectNode rootNode = jsonNodeFactory.objectNode();
        ObjectNode infoNode = jsonNodeFactory.objectNode();

        ObjectNode signNode = jsonNodeFactory.objectNode();

        infoNode.put("Process",process);
        infoNode.put("Operation",operation);

        //source节点添加
        setSourceNode(infoNode,source);

        //destination节点添加
        setDestionationNode(infoNode,destination);

        //Data字段
        ObjectNode dataNode = jsonNodeFactory.objectNode();
        dataNode.put("Code",code);
        dataNode.put("Idc",idC);
        dataNode.put("IdTGS",idTGS);
        //时间戳节点添加
        setTSNode(dataNode,ts);

        infoNode.set("Data",dataNode);
        rootNode.set("Info",infoNode);


        //sign签名
        ObjectMapper objectMapper = new ObjectMapper();

        String signContext= objectMapper.writeValueAsString(infoNode);

        //TODO
        //签名算法
        // String signResult=signContext;
        //
        signNode.put("Context","");
        signNode.put("PublicKey",publickey);

        rootNode.set("Sign",signNode);

        // rootNode.put("1","2");



        return objectMapper.writeValueAsString(rootNode);
        //  String result = "";
        /*JsonGenerator generator =
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeTree(generator, rootNode);*/
    }


}



