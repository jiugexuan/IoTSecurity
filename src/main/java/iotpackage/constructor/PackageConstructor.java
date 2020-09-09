package iotpackage.constructor;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
        sourceNode.put("ID",source.getId());
        sourceNode.put("IP",source.getIp());
        parentNode.set("Source",sourceNode);
    }

    //destination节点添加
    void setDestionationNode(ObjectNode parentNode,Destination destination){
        ObjectNode destinationNode=jsonNodeFactory.objectNode();
        destinationNode.put("ID",destination.getId());
        destinationNode.put("IP",destination.getIp());
        parentNode.set("Destination",destinationNode);
    }

    //时间戳节点添加
    void setTSNode(ObjectNode parentNode,TS ts){
        ObjectNode TSNode=jsonNodeFactory.objectNode();
        TSNode.put("ID",ts.getId());
        TSNode.put("Context",ts.getContext());
        parentNode.set("TS",TSNode);

    }

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
    public String  packageCtoAS(String process, String operation, Source source, Destination destination, String code, String idC, String idTGS, TS ts, String publickey) throws IOException {
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
        String signResult=signContext;
        signNode.put("Context",signResult);
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
