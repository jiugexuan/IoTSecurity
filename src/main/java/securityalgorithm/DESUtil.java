package securityalgorithm;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.*;


public class DESUtil {
    private static String seed=null;
    private static Key key;
    private static String CHARSETNAME = "UTF-8";
    private static String ALGORITHM = "DES";

    /**
     * @Decription 获取加密之后的信息
     * @param str str
     * @param getkey 密钥
     * @return java.lang.String
     */
    public static String getEncryptString(String str,String getkey) {
        //基于BASE64编码，接收byte[] 并转换成String
        seed=getkey;
        String KEY_STR = seed;
        Base64 base64 =new Base64();
        //  BASE64Encoder base64encoder = new BASE64Encoder();
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
            return base64.encodeToString(doFinal);
        } catch (Exception e) {
            // handle exception
            throw new RuntimeException(e);
        }
    }

//    /**
//     * @param: str
//     * @return: java.lang.String
//     * @Description: 获取解密之后的信息
//     */
//    public static String getDecryptString(String str,String getkey) {
//        seed=getkey;
//        String KEY_STR = seed;
//        //基于BASE64编码，接收byte[] 并转换成String
//        Base64 base64 =new Base64();
//        try {
//            KeyGenerator generator = KeyGenerator.getInstance(ALGORITHM);
//            //运用SHA1安全策略
//            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
//            //设置上密匙种子
//            secureRandom.setSeed(KEY_STR.getBytes());
//            //初始化基于SHA1的算法对象
//            generator.init(secureRandom);
//            //生成密匙对象
//            key = generator.generateKey();
//            //将字符串decode成byte[]
//            byte[] bytes = base64.decode(str);
//            //获取解密对象
//            Cipher cipher = Cipher.getInstance(ALGORITHM);
//            //初始化解密信息
//            cipher.init(Cipher.DECRYPT_MODE, key);
//            //解密
//            byte[] doFinal = cipher.doFinal(bytes);
//            //返回解密之后的信息
//            return new String(doFinal, CHARSETNAME);
//        } catch (Exception e) {
//            handle exception
//            throw new RuntimeException(e);
//        }
    /**
     * @param: str
     * @return: java.lang.String
     * @Description: 获取解密之后的信息
     */
    public static String getDecryptString(String str,String getkey) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        seed=getkey;
        String KEY_STR = seed;
        //基于BASE64编码，接收byte[] 并转换成String
        Base64 base64 =new Base64();
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
        byte[] bytes = base64.decode(str);
        //获取解密对象
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        //初始化解密信息
        cipher.init(Cipher.DECRYPT_MODE, key);
        //解密
        byte[] doFinal = cipher.doFinal(bytes);
        //返回解密之后的信息
        return new String(doFinal, CHARSETNAME);

    }


}