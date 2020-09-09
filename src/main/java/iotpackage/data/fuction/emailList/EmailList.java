package iotpackage.data.fuction.emailList;

public class EmailList {
    //邮件个数
    int ListNumber;
    //邮件列表
    String context;

    public EmailList(int listNumber, String context) {
        ListNumber = listNumber;
        this.context = context;
    }

    public int getListNumber() {
        return ListNumber;
    }

    public void setListNumber(int listNumber) {
        ListNumber = listNumber;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
