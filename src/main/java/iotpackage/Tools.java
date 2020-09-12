package iotpackage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import iotpackage.constructor.PackageParser;
import securityalgorithm.MD5Util;
import securityalgorithm.RSAUtil;

import java.io.IOException;

public class Tools {
    public static void jsonFormat(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Object obj = mapper.readValue(json, Object.class);
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj));
    }

    //TODO 测试
    public  static boolean signatureVerigy(String json) throws IOException {
        PackageParser packageParser=new PackageParser(json);
        String md5Info= MD5Util.md5(packageParser.getInfoJson());
        String signature= RSAUtil.publicDecrypt(packageParser.getSignContext(),packageParser.getSignPublicKey());
        return md5Info.equals(signature);
    }


}
