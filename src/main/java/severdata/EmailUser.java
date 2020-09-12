package severdata;

import iotpackage.IPInfo;

public class EmailUser extends IPInfo {
    String nickname;
    /**
     * 状态
     * 0 离线
     * 1 在线
     */
    boolean status;

    public EmailUser(String nickname, boolean status) {
        this.nickname = nickname;
        this.status = status;
    }

    public EmailUser(String id, String ip, String nickname, boolean status) {
        super(id, ip);
        this.nickname = nickname;
        this.status = status;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
