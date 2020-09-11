package iotpackage.constructor;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import iotpackage.IoTKey;
import iotpackage.data.autheticator.Authenticator;
import iotpackage.data.ciphertext.Ciphertext;
import iotpackage.data.ciphertext.Lifetime;
import iotpackage.data.ticket.Ticket;
import iotpackage.destination.Destination;
import iotpackage.source.Source;

import iotpackage.data.*;
import securityalgorithm.DESUtil;
import securityalgorithm.MD5Util;
import securityalgorithm.RSAUtil;


public class PackageConstructor {

    //JsonNodeFactory 实例，可全局共享
    private JsonNodeFactory jsonNodeFactory;
    //JsonFactory 实例，线程安全
    private JsonFactory jsonFactory ;

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
    void setLifetimeNode(ObjectNode parentNode, Lifetime lifetime){
        ObjectNode lifetimeNode=jsonNodeFactory.objectNode();
        lifetimeNode.put("Id",lifetime.getId());
        lifetimeNode.put("Context",lifetime.getContext());
        parentNode.set("Lifetime",lifetimeNode);

    }

    //密文节点添加
    void setCipherNode(ObjectNode parentNode,Ciphertext ciphertext){
        ObjectNode ciphertextNode=jsonNodeFactory.objectNode();

        ciphertextNode.put("Context",ciphertext.getContext());
        ciphertextNode.put("Id",ciphertext.getId());
        parentNode.set("Ciphertext",ciphertextNode);

    }

    //Ticket节点添加
    //Ticket 保密v
    void setTicketNode(ObjectNode parentNode,String ticketContext,String ticketID){
        ObjectNode ticketNode=jsonNodeFactory.objectNode();
        ticketNode.put("Id",ticketID);
        ticketNode.put("Context",ticketContext);

        parentNode.set("Ticket",ticketNode);

    }

    //凭证节点添加
    // Authenticator 加密状态
    void setAuthenticatorNode(ObjectNode parentNode,String authenticatorContext,String authenticatorID){
        ObjectNode authenticatorNode=jsonNodeFactory.objectNode();
        authenticatorNode.put("Id",authenticatorContext);
        authenticatorNode.put("Context",authenticatorID);

        parentNode.set("Authenticator", authenticatorNode);
    };
   /* void setTicketNode(ObjectNode parentNode,Ticket TGS,ticket){
        ObjectNode ciphertextNode=jsonNodeFactory.objectNode();

        ciphertextNode.put("Context",ciphertext.getContext());
        ciphertextNode.put("Id",ciphertext.getId());
        parentNode.set("Ciphertext",ciphertextNode);

    }*/

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
        setLifetimeNode(rootNode,ticket.getLifetime());
        //ObjectMapper objectMapper = new ObjectMapper();

        //String signContext= objectMapper.writeValueAsString(rootNode);
        return new ObjectMapper().writeValueAsString(rootNode);
    };


    /***服务器响应代码****/
    /***service to C
     * @param process 进程代号
     * @param operation 操作代号
     * @param source 发送方
     * @param destination 接受方
     * @param code 操作码
     * @param publickey 验证公钥
     * */
    public String getPackageServiceResponse(String process, String operation, Source source, Destination destination, String code,String publickey) throws JsonProcessingException {
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


        infoNode.set("Data",dataNode);
        rootNode.set("Info",infoNode);


        if(publickey.length()==0){
            signNode.put("Context","");

        }else{
            //签名是加密
            String signContext= RSAUtil.privateEncrypt(MD5Util.md5(new ObjectMapper().writeValueAsString(infoNode)) ,publickey);
            signNode.put("Context",signContext);
        }

        signNode.put("PublicKey",publickey);
        rootNode.set("Sign",signNode);
        // ObjectMapper objectMapper = new ObjectMapper();
        return new ObjectMapper().writeValueAsString(rootNode);
    }

    /***用户注册****/
    /***C to AS
     * @param process 进程代号
     * @param operation 操作代号
     * @param source 发送方
     * @param destination 接受方
     * @param code 操作码
     * @param account 用户账户
     * @param password 密码
     *
     * @param nickname 昵称
     * @param securityQuestion 安全问题
     * @param securityAnswer 安全问题的答案
     * ****/
        public String getPackageCtoASRegist(String process, String operation,
                                            Source source, Destination destination,
                                            String code,
                                            String account,String password,
                                            String nickname,String securityQuestion,
                                            String securityAnswer,String publickey) throws JsonProcessingException {
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
            dataNode.put("Account",account);
            //TODO password加密
           // String passwordEn= DESUtil.getEncryptString(password,deskey);
            dataNode.put("Password", MD5Util.md5(password));
            dataNode.put("Nickname",nickname);
            dataNode.put("SecurityQuestion",securityQuestion);
            dataNode.put("SecurityAnswer",securityAnswer);

            infoNode.set("Data",dataNode);
            rootNode.set("Info",infoNode);

            if(publickey.length()==0){
                signNode.put("Context","");

            }else{
                //签名是加密
                String signContext= RSAUtil.privateEncrypt(MD5Util.md5(new ObjectMapper().writeValueAsString(infoNode)) ,publickey);
                signNode.put("Context",signContext);
            }

            signNode.put("PublicKey",publickey);
            rootNode.set("Sign",signNode);
           // ObjectMapper objectMapper = new ObjectMapper();
            return new ObjectMapper().writeValueAsString(rootNode);
    };

    /***服务认证阶段***/
    /***C to AS
     * @param process 进程代号
     * @param operation 操作代号
     * @param source 发送方
     * @param destination 接受方
     * @param code 操作码
     * @param idC 用户IP地址
     * @param idTGS TGS的IP地址
     * @param ts 为时间戳 ,如2020-6-15 10:00:00
     * ****/
    public String  getPackageCtoASLogin(String process, String operation,
                                        Source source, Destination destination,
                                        String code,
                                        String idC, String idTGS, TS ts,
                                        String publickey) throws JsonProcessingException {
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
        if(publickey.length()==0){
            signNode.put("Context","");

        }else{
            //签名是加密
            String signContext= RSAUtil.privateEncrypt(MD5Util.md5(new ObjectMapper().writeValueAsString(infoNode)) ,publickey);
            signNode.put("Context",signContext);
        }


       // String signResult=signContext;
        //

        signNode.put("PublicKey",publickey);

        rootNode.set("Sign",signNode);

       // rootNode.put("1","2");



        return new ObjectMapper().writeValueAsString(rootNode);
      //  String result = "";
        /*JsonGenerator generator =
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeTree(generator, rootNode);*/
    }

    @Deprecated
    /***AS to C
     *
     * @param process 进程代号
     * @param operation 操作代号
     * @param source 发送方
     * @param destination 接受方
     * @param code 操作码
     * @param idC 用户的ip地址
     * @param cipherKey 加密密文的密钥 为用户的password的md5的值
     * @param CandTGS 由AS生成的C和TGS交互的密钥，ticket tgs中有一份
     * @param idTGS tgs的ip地址
     * @param ts 时间戳
     * @param lifetime 有效时间
     * @param tgs tgs的票据
     * @param ticketKey 加密ticket的票据的key
     * @param publickey 签名公钥
     * ****/
    public String  getPackageAStoCLogin(String process, String operation,
                                        Source source, Destination destination,
                                        String code,
                                        String idC,
                                        String cipherKey,
                                        IoTKey CandTGS, String idTGS, TS ts, Lifetime lifetime,
                                        Ticket tgs,
                                        String ticketKey,
                                        String publickey) throws JsonProcessingException {
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

        CipherConstructor cipherConstructor=new CipherConstructor(cipherKey);
        setCipherNode(dataNode,new Ciphertext(cipherConstructor.constructCipherOfAStoC(CandTGS,idTGS,ts,lifetime,tgs,ticketKey),"AStoCLoginResponse"));

        infoNode.set("Data",dataNode);
        rootNode.set("Info",infoNode);


        //sign签名
        ObjectMapper objectMapper = new ObjectMapper();

        //签名算法
        if(publickey.length()==0){
            signNode.put("Context","");

        }else{
            //签名是加密
            String signContext= RSAUtil.privateEncrypt(MD5Util.md5(new ObjectMapper().writeValueAsString(infoNode)) ,publickey);
            signNode.put("Context",signContext);
        }
        signNode.put("PublicKey",publickey);

        rootNode.set("Sign",signNode);

        // rootNode.put("1","2");



        return objectMapper.writeValueAsString(rootNode);
    }

    /***AS to C
     *
     * @param process 进程代号
     * @param operation 操作代号
     * @param source 发送方
     * @param destination 接受方
     * @param code 操作码
     * @param idC 用户的ip地址
     * @param cipherKey 加密密文的密钥 为用户的password的md5的值
     * @param CandTGS 由AS生成的C和TGS交互的密钥，ticket tgs中有一份
     * @param idTGS tgs的ip地址
     * @param ts 时间戳
     * @param lifetime 有效时间
     * @param tgs tgs的票据
     * @param ticketID tgs的ID
     * @param ticketKey 加密ticket的票据的key
     * @param publickey 签名公钥
     * ****/
    public String  getPackageAStoCLogin(String process, String operation,
                                        Source source, Destination destination,
                                        String code,
                                        String idC,
                                        String cipherKey,
                                        IoTKey CandTGS, String idTGS, TS ts, Lifetime lifetime,
                                        Ticket tgs,
                                        String ticketID,
                                        String ticketKey,
                                        String publickey) throws JsonProcessingException {
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

        CipherConstructor cipherConstructor=new CipherConstructor(cipherKey);
        setCipherNode(dataNode,new Ciphertext(cipherConstructor.constructCipherOfAStoC(CandTGS,idTGS,ts,lifetime,tgs,ticketID,ticketKey),"AStoCLoginResponse"));

        infoNode.set("Data",dataNode);
        rootNode.set("Info",infoNode);


        //sign签名
        ObjectMapper objectMapper = new ObjectMapper();

        //签名算法
        if(publickey.length()==0){
            signNode.put("Context","");

        }else{
            //签名是加密
            String signContext= RSAUtil.privateEncrypt(MD5Util.md5(new ObjectMapper().writeValueAsString(infoNode)) ,publickey);
            signNode.put("Context",signContext);
        }
        signNode.put("PublicKey",publickey);

        rootNode.set("Sign",signNode);

        // rootNode.put("1","2");



        return objectMapper.writeValueAsString(rootNode);
    }

    /***C to TGS
     * @param process 进程代号
     * @param operation 操作代号
     * @param source 发送方
     * @param destination 接受方
     * @param code 操作码
     * @param idV 用户的访问的V的IP
     * @param tgsContext tgs的票据
     * @param tgsticketID ticket ID号
     * @param authenticator 有效证明
     * @param authenticatorID 有效证明id
     * @param authenticator  用户授权
     * @param publickey 签名公钥
     * ****/
    public String  getPackageCtoTGS(String process, String operation,
                                       Source source, Destination destination,
                                       String code,
                                       String idV,
                                       String tgsContext,
                                       String tgsticketID,
                                       Authenticator authenticator,
                                       String authenticatorKey,
                                       String authenticatorID,
                                       String publickey
                                       ) throws JsonProcessingException {

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
        dataNode.put("IdV",idV);
        //setTicket
        setTicketNode(dataNode,tgsContext,tgsticketID);
        setAuthenticatorNode(dataNode,DESUtil.getEncryptString(
                new CipherConstructor().getPackageAuthenticatorToGson(authenticator),authenticatorKey),
                authenticatorID);

        infoNode.set("Data",dataNode);
        rootNode.set("Info",infoNode);


        //sign签名
        //签名算法
        if(publickey.length()==0){
            signNode.put("Context","");

        }else{
            //签名是加密
            String signContext= RSAUtil.privateEncrypt(MD5Util.md5(new ObjectMapper().writeValueAsString(infoNode)) ,publickey);
            signNode.put("Context",signContext);
        }
        signNode.put("PublicKey",publickey);


        rootNode.set("Sign",signNode);
        return new ObjectMapper().writeValueAsString(rootNode);
    }

}



