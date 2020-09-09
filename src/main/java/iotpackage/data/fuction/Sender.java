package iotpackage.data.fuction;

public class Sender {
    /**用户账户**/
    String account;
    /**用户昵称**/
    String nickname;

    public Sender(String account, String nickname) {
        this.account = account;
        this.nickname = nickname;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
