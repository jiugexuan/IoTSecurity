package access;

public class IPInTheItem {
    public String UserIP = "192.168.43.88";
    public String ASIP = "192.168.43.117";
    public String TGSIP = "192.168.43.236";
    public String SERIP = "192.168.43.52";

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
