package iotpackage.data.fuction.emailList;

import iotpackage.data.fuction.Email;

import java.util.Vector;

public class EmailList {
    //邮件个数
    int listNumber;
    //邮件列表
    Vector<Email> emailList;

    public EmailList() {
        listNumber=0;
        emailList=new Vector<Email>();
    }

    public EmailList(Vector<Email> emailList) {
        this.emailList = emailList;
        this.listNumber=emailList.size();
    }

    public int getListNumber() {
        return listNumber;
    }

    public Vector<Email> getEmailList() {
        return emailList;
    }

    public void setListNumber(int listNumber) {
        this.listNumber = listNumber;
    }

    public void setEmailList(Vector<Email> emailList) {
        this.emailList = emailList;
        this.listNumber=emailList.size();
    }

    public void addEmail(Email email){
        this.listNumber++;
        this.emailList.add(email);
    }

    public Email getEmailAtIndex(int i){
        return emailList.get(i);
    }

    public void printEmailList(){
        System.out.println(this.getClass().getSimpleName());
        for(int i=0;i<this.getListNumber();i++){
            this.getEmailAtIndex(i).printEmail();
        }
    }
}
