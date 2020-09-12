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
            String test = DESUtil.getDecryptString("iz/I3G1v06Sii3vuCqMsvws9w3mE6F4LbSkUWebqOvQMiGYQniSDwoKwAka4W6M+lXucrlPVRKCo44rIv39BVDd+P1tSKl2Th5oVEZqO2rul3/Qc4iDEJio/YLVoY5p913VG5/KqCY5PCh88eQXUfpX4rZmXDcvph9h74e69ASG5imcdeo83HmNSXHHbEi5Z","963852741");
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
