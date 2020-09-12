package iotpackage.data.fuction;

import iotpackage.data.fuction.User.Receiver;
import iotpackage.data.fuction.User.Sender;

public class Email {
    String Id;
    /**邮件发送方**/
    Sender sender;
    /**邮件接受方**/
    Receiver receiver;
    /**邮件标题**/
    String title;
    /**邮件时间**/
    String time;
    /**邮件类型**/
    String type;
    /**邮件内容**/
    String context;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public Email(String Id, Sender sender, Receiver receiver, String title, String time, String type, String context) {
        this.Id=Id;
        this.sender = sender;
        this.receiver = receiver;
        this.title = title;
        this.time = time;
        this.type = type;
        this.context = context;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public void printEmail(){
        System.out.println("--------------");
        System.out.println("the Email:");
        System.out.println(">>Id:"+getId());
        System.out.println(">>Sender:");
        getSender().printSender();
        System.out.println(">>Receiver:");
        getReceiver().printReceiver();
        System.out.println(">>Title:"+getTitle());
        System.out.println(">>Time:"+getTime());
        System.out.println(">>Type:"+getType());
        System.out.println(">>context:"+getContext());
        System.out.println("--------------");

    }
}
