package severdata;

import iotpackage.data.fuction.Email;

import java.util.Vector;

/**
 * @author 19710
 */
public class EmailUserList {
    Vector<EmailUser> emailUserList;

    public EmailUserList(Vector<EmailUser> emailUsers) {
        this.emailUserList = emailUsers;
    }

    public int getEmailUserListNumber(){
        return this.emailUserList.size();
    }

    public Vector<EmailUser> getEmailUserList() {
        return emailUserList;
    }

    public void setEmailUserList(Vector<EmailUser> emailUserList) {
        this.emailUserList = emailUserList;
    }

    public void addEmailUser(EmailUser emailUser){
        this.emailUserList.add(emailUser);
    }

    public boolean getEmailUserByAccount(String acconut, EmailUser User){
        for(int i=0;i<this.getEmailUserListNumber();i++){
            if(this.emailUserList.elementAt(i).getId().equals(acconut)){
                User=emailUserList.elementAt(i);
                        return true;
            }

        }
        return false;
    }
}
