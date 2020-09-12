import securityalgorithm.DESUtil;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/*
* @author Sun
*
* */
public class Main {
    public static void main(String[] args) {
        try {
            String test = DESUtil.getDecryptString("iXP4aua8VCZ9JVGfUlA4JH8dvRypOEr1CGLNxkAciAsZLMoCk0nH26nfvQ9zDfR32U6R83OTierZf6ykq8Px1T0jUjk47UMgiSW7f6StWe78LC/olzyzD1Nczs0LLR7fZU4rJiDXnnaE9+CNWww0ODH7/xE1JYEaIndfgiQZWXujk4YuMCQM8kmMqoiZpgZD","1234578");
            System.out.println(test);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException badPaddingException) {
            badPaddingException.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
    }


}
