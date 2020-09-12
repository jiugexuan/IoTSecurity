package iotpackage.data.fuction.User;

public class Receiver extends User{


    public Receiver(String account, String nickname) {
        super(account, nickname);
    }

    public void printReceiver(){
        System.out.println(">--------------");
        System.out.println("the Receiver:");
        System.out.println(">>Account:"+getAccount());
        System.out.println(">>Nickname:"+getNickname());
        System.out.println(">--------------");

    }
}
