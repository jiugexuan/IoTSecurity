package iotpackage.data.fuction.User;

public class Sender extends User{


    public Sender(String account, String nickname) {
        super(account, nickname);
    }

    public void printSender(){
        System.out.println(">--------------");
        System.out.println("the Sender:");
        System.out.println(">>Account:"+getAccount());
        System.out.println(">>Nickname:"+getNickname());
        System.out.println(">--------------");

    }
}
