package iotpackage.sign;

public class Sign {
    /**
     * 数字签名的内容
     */
    String context;
    /**
     * 数字签名的公钥
     * */
    String publickey;

    public Sign(String context, String publickey) {
        this.context = context;
        this.publickey = publickey;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getPublickey() {
        return publickey;
    }

    public void setPublickey(String publickey) {
        this.publickey = publickey;
    }
}
