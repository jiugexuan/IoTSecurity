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
            String test = DESUtil.getDecryptString("ilPDWude1iUdSXOK3VkVytqz6V0oZerxkkC7zcTgCT+OLBHT+AA+Q9khRy8JSTaMqjEERU69nvWv4tHerfiLl5GZ4Ed6VEvNoRJvOAfs2yww33VQ75kIbfur7K1vL4+26ijnYDLwAGWKQIXOuQEFgcfRKTTvb+hQ5tQCCr0Wq2L22pU+0FGPtE2bxzm55w9Nk57Y81TIvDr/ofpLW4l9U305vWZDI9KL8Nwt/HAJTyPRcFGh+JuSRGdryEdrpGtK9Lp9LezFnBSd5bOka35tPg3AYhLFUjPnrkpTRktK7/Pw3C38cAlPI17DJYztobR6bBcQS1o22bo=","tickkey AS TGS");
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
