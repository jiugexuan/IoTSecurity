package access;

public class IPInTheItem {

    public String UserIP = "127.0.0.1";
    public String ASIP = "127.0.0.1";
    public String TGSIP = "127.0.0.1";
    public String SERIP = "127.0.0.1";

    public IPInTheItem() {
    }

    public String getUserIP() {
        return UserIP;
    }

    public void setUserIP(String userIP) {
        UserIP = userIP;
    }

    public String getASIP() {
        return ASIP;
    }

    public void setASIP(String ASIP) {
        this.ASIP = ASIP;
    }

    public String getTGSIP() {
        return TGSIP;
    }

    public void setTGSIP(String TGSIP) {
        this.TGSIP = TGSIP;
    }

    public String getSERIP() {
        return SERIP;
    }

    public void setSERIP(String SERIP) {
        this.SERIP = SERIP;
    }
}
