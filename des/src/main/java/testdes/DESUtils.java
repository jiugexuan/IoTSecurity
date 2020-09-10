package testdes;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DESUtils {
    private static String seed=null;
    private static Key key;
    private static String CHARSETNAME = "UTF-8";
    private static String ALGORITHM = "DES";

    /**
     * @param: str
     * @return: java.lang.String
     * @Description: 获取加密之后的信息
     */
    public static String getEncryptString(String str,String getkey) {
        //基于BASE64编码，接收byte[] 并转换成String
        seed=getkey;
        String KEY_STR = seed;
        BASE64Encoder base64encoder = new BASE64Encoder();
        try {
            KeyGenerator generator = KeyGenerator.getInstance(ALGORITHM);
            //运用SHA1安全策略
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            //设置上密匙种子
            secureRandom.setSeed(KEY_STR.getBytes());
            //初始化基于SHA1的算法对象
            generator.init(secureRandom);
            //生成密匙对象
            key = generator.generateKey();
            //按UTF8编码
            byte[] bytes = str.getBytes(CHARSETNAME);
            //获取加密对象
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            //初始化密码信息
            cipher.init(Cipher.ENCRYPT_MODE, key);
            //加密
            byte[] doFinal = cipher.doFinal(bytes);
            //byte[] to encode 好的String并返回
            return base64encoder.encode(doFinal);
        } catch (Exception e) {
            // TODO: handle exception
            throw new RuntimeException(e);
        }
    }

    /**
     * @param: str
     * @return: java.lang.String
     * @Description: 获取解密之后的信息
     */
    public static String getDecryptString(String str,String getkey) {
        seed=getkey;
        String KEY_STR = seed;
        //基于BASE64编码，接收byte[] 并转换成String
        BASE64Decoder base64decoder = new BASE64Decoder();
        try {
            KeyGenerator generator = KeyGenerator.getInstance(ALGORITHM);
            //运用SHA1安全策略
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            //设置上密匙种子
            secureRandom.setSeed(KEY_STR.getBytes());
            //初始化基于SHA1的算法对象
            generator.init(secureRandom);
            //生成密匙对象
            key = generator.generateKey();
            //将字符串decode成byte[]
            byte[] bytes = base64decoder.decodeBuffer(str);
            //获取解密对象
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            //初始化解密信息
            cipher.init(Cipher.DECRYPT_MODE, key);
            //解密
            byte[] doFinal = cipher.doFinal(bytes);
            //返回解密之后的信息
            return new String(doFinal, CHARSETNAME);
        } catch (Exception e) {
            // TODO: handle exception
            throw new RuntimeException(e);
        }
    }

}