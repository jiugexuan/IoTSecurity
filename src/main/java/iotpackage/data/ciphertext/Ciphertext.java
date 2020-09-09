package iotpackage.data.ciphertext;

public class Ciphertext {
    /**密文内容**/
    String context;
    /**密文对应密钥ID**/
    String id;

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Ciphertext(String context, String id) {
        this.context = context;
        this.id = id;
    }
}
