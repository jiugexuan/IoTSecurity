package iotpackage.data.fuction.emailList;

import iotpackage.data.fuction.Email;

import java.util.Vector;

public class ReceiveList extends EmailList {
    public ReceiveList() {
    }

    public ReceiveList(Vector<Email> emailsList){
        super(emailsList);
    }

    public void printReciveList(){
      printEmailList();
    }
}
