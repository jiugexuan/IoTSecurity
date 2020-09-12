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
            String test = DESUtil.getDecryptString("zsx/TjDLqfHvlAgYIS263lo8qbX0tUDRYwqLDEoOpHvPEbtZG475V44dfEBhVy8acgkyMxiMQsEgYXeh5mKnBUH3XROgqSc1uaBBGop/uLsTSgkZQbe60BWlbkOsBZ0kMVpjZeANnybQ5YUPGXHAguHQrFV5KPXGf9miEnYugyYRvAUg6hesFcRkFC1CJabSdkQQF4h0b8jwx6h18iZcpBLVunHJ9+8Dp9D+/t1tFwsy/I7Lr3YRvD4zvnV7H4wILdgJyXImGP6j718eToQjuHmeeia2qJvmXwcDtdYtCeMo3rhaPLIN3Vr78nh17VRHtsNPq4CvGwW+zZFz+iBfNU8zSQg70aeSjSVgtJ/3IgNFi+EIVreMnx/08tCRQMopMdwmcP9mXWttWgl96/r8g8sXE5MAw3w2FKAltcxWstfxw7aJQ6ZGjdI4gnrLIKb7UHtFxo7R+i+ZId8gjh+O1lzb/WSMOj022EaIqT2+wLDU6Z8MSBFfscQAgSbDMqhfxJuRnbP/qSTvD2G4pdC+HO6DqKrwBAbmJRMF+hxNIC9xhEa8cdTsJla9pelLnUueNCiK1vzeKQxPWvXZZ3d3vA==","963852741");
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
