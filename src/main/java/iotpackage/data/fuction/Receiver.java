package iotpackage.data.fuction;

public class Receiver {
    /**用户账户**/
    String account;
    /**用户昵称**/
    String nickname;

    public Receiver(String account, String nickname) {
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
