package iotpackage.data.fuction.User;

public class User {
    /**用户账户**/
    String account;
    /**用户昵称**/
    String nickname;

    public User(String account, String nickname) {
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

    public void printUser(){
        System.out.println(">--------------");
        System.out.println("the User:");
        System.out.println(">>Account:"+getAccount());
        System.out.println(">>Nickname:"+getNickname());
        System.out.println(">--------------");

    }
}
