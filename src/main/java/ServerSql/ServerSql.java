package ServerSql;

import AS.mysql.DataConn;
import client.UI.Send;
import iotpackage.data.fuction.emailList.*;
import iotpackage.data.fuction.User.*;
import iotpackage.data.fuction.*;

import java.awt.desktop.ScreenSleepEvent;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Vector;

public class ServerSql  {
    static Statement stmt = null;
    static ResultSet rs = null;
    static Connection conn = null;
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String USER = "root";
    static final String PASS = "123456";
    static String DB_URL = "jdbc:mysql://127.0.0.1:3306/mailsystem";


/**
* 创建用户发送邮件表
* 若成功返回0100
* 若失败返回0103
* */
public  static String creatSendTable(String sendname){
    String tablename=sendname+"send";
    String creatsql = "create table "+tablename+"(id int(11),rev varchar(255)," +
            "title varchar(255),content varchar(255),ctime varchar(255))charset=utf8;";
    try {
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
        System.out.println("\n dataconn 数据库连接成功");
        stmt = conn.createStatement();
        if(0 == stmt.executeUpdate(creatsql))
        {
            System.out.println("成功创建表！");;
        }
        else
        {
            System.out.println("创建表失败！");
            return "0103";
        }
        stmt.close();
        conn.close();
        System.out.println("//关闭资源");
    } catch (Exception e) {
        e.printStackTrace();
    }
    return "0100";
    }
    /**
     * 创建用户接受邮件表
     * 若成功返回0100
     * 若失败返回0103
     * */
    public  static String creatRevTable(String Revname){
        String tablename=Revname+"rev";
        String creatsql = "create table "+tablename+"(id int(11),send varchar(255)," +
                "title varchar(255),content varchar(255),ctime varchar(255))charset=utf8;";
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            System.out.println("\n dataconn 数据库连接成功");
            stmt = conn.createStatement();
            if(0 == stmt.executeUpdate(creatsql))
            {
                System.out.println("成功创建表！");;
            }
            else
            {
                System.out.println("创建表失败！");
                return "0103";
            }
            stmt.close();
            conn.close();
            System.out.println("//关闭资源");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0100";
    }
    /**
     * 发送邮件存入表中
     * 若需要输入发送方和接受方账户
     * 若失败返回0104
     * 成功返回1000
     * */
        public static String sendMail(String emailId, Sender send, Receiver rev, String title, String content){
        String ctime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
        String sql2 = "select username from user where username='" +rev.getAccount()+ "'";
        SqlOperation.OpenConn();
        rs =  SqlOperation.executeQuery(sql2);
        if (rs == null) {
            System.err.println("\n 接收方不存在！");
            return "0104";
        }else{
           String tablename=send.getAccount()+"send";
           String sql3 = "insert into "+tablename+"(id,rev,title,content,ctime) values ('"+emailId+"','" + rev.getAccount() + "','" + title + "','"+content+"','"+ctime+"')";
            int re =  SqlOperation.executeUpdate(sql3);
            while(re == 0) {
                System.err.println("\n 发送失败，请重试！");
                return "0104";
            }
        }
        return "1000";
    }
    /**
     * 接收邮件存入表中
     * 若需要输入发送方和接受访账户
     * 若失败返回0104
     * 成功返回1000
     * */
    public static String revMail(String emailId,Receiver rev,Sender send,String title,String content){
        String ctime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
        String sql2 = "select username from user where username='" +send.getAccount()+ "'";
        SqlOperation.OpenConn();
        rs =  SqlOperation.executeQuery(sql2);
        if (rs == null) {
            System.err.println("\n 接收方不存在！");
            return "0104";
        }else{
            String tablename=rev.getAccount()+"rev";
            String sql3 = "insert into "+tablename+"(id,send,title,content,ctime) values ('"+emailId+"','" + send.getAccount() + "','" + title + "','"+content+"','"+ctime+"')";
            int re =  SqlOperation.executeUpdate(sql3);
            while(re == 0) {
                System.err.println("\n 接收失败，请重试！");
                return "0104";
            }
        }
        return "1000";
    }
    /**
     * 查看收件箱
     * @param rev 收件方
     * @param emailList 存放收件箱所有邮件
     * */

    public static String findRevAll(ReceiveList emailList,Receiver rev) throws SQLException, ClassNotFoundException {
        String tablename=rev.getAccount()+"rev";
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
        String sql = "select * from "+tablename;
         stmt=(Statement) conn.createStatement();
        ResultSet resultSet=stmt.executeQuery(sql);
        String sendname,title,content,ctime,id;
        Email email=null; Sender tmpsend=new Sender("","");
        while (resultSet.next()) {
            id =resultSet.getString("Id");
            sendname = resultSet.getString("send");
            title = resultSet.getString("title");
            content= resultSet.getString("content");
            ctime=resultSet.getString("ctime");
            tmpsend.setAccount(sendname);
            email=new Email(id,tmpsend,rev,title,ctime,"txt",content);
            emailList.addEmail(email);
            System.out.println(sendname+'\t'+title+'\t'+content+'\t'+ctime);
        }
        return "1002";
    }

    /**
     * 查看收件箱
     * @param sender 发件方
     * @param emailList 存放发件箱所有邮件
     * */

    public static String findSendAll(SendList emailList,Sender sender) throws SQLException, ClassNotFoundException {
        String tablename=sender.getAccount()+"send";
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
        String sql = "select * from "+tablename;
        stmt=(Statement) conn.createStatement();
        ResultSet resultSet=stmt.executeQuery(sql);
        String revname,title,content,ctime,id;
        Email email=null; Receiver tmprev=new Receiver("","");
        while (resultSet.next()) {
            id =resultSet.getString("Id");
            revname = resultSet.getString("rev");
            title = resultSet.getString("title");
            content= resultSet.getString("content");
            ctime=resultSet.getString("ctime");
            tmprev.setAccount(revname);
            email=new Email(id,sender,tmprev,title,ctime,"txt",content);
            emailList.addEmail(email);
            System.out.println(revname+'\t'+title+'\t'+content+'\t'+ctime);
        }
        return "1002";
    }
    /**
     * 搜索指定id的发送的邮件
     * @param sender 发件方
     * @param id 邮件id
     * */
  public static  Email findSendById (String id,Sender sender) throws ClassNotFoundException, SQLException {
      String tablename=sender.getAccount()+"send";
      Class.forName(JDBC_DRIVER);
      conn = DriverManager.getConnection(DB_URL,USER,PASS);
      //String sql = "select * from "+tablename;
      String sql ="select * from "+tablename+" where id='" +id+ "'";
      stmt=(Statement) conn.createStatement();
      Email email=null; ResultSet resultSet=stmt.executeQuery(sql);
      while (resultSet.next()) {
          String revname, title, content, ctime;
          Receiver tmprev = new Receiver("", "");
          revname = resultSet.getString("rev");
          title = resultSet.getString("title");
          content = resultSet.getString("content");
          ctime = resultSet.getString("ctime");
          tmprev.setAccount(revname);
          email = new Email(id,sender, tmprev, title, ctime, "txt", content);
          System.out.println(revname + '\t' + title + '\t' + content + '\t' + ctime);
      }
      return email;

  }

    /**
     * 搜索指定id的接受的邮件
     * @param receiver 接受方
     * @param id 邮件id
     * */
    public static  Email findRevById (String id,Receiver receiver) throws ClassNotFoundException, SQLException {
                String tablename=receiver.getAccount()+"rev";
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
        //String sql = "select * from "+tablename;
        String sql ="select * from "+tablename+" where id='" +id+ "'";
        stmt=(Statement) conn.createStatement();
        Email email=null; ResultSet resultSet=stmt.executeQuery(sql);
        while (resultSet.next()) {
            String revname, title, content, ctime;
            Sender tmpsend = new Sender("", "");
            revname = resultSet.getString("send");
            title = resultSet.getString("title");
            content = resultSet.getString("content");
            ctime = resultSet.getString("ctime");
            tmpsend.setAccount(revname);
            email = new Email(id,tmpsend,receiver, title, ctime, "txt", content);
            System.out.println(revname + '\t' + title + '\t' + content + '\t' + ctime);
        }
        return email;

    }


    public static void main(String []args) throws SQLException, ClassNotFoundException {
   // creatRevTable("18671752026");
    //revMail("18671752026","18671752028","hello","nice to meet you");

        ReceiveList emailList =new ReceiveList();
        Receiver receiver=new Receiver("18671752026","");
        Sender send=new Sender("18671752026","");
/*
        findRevAll(emailList,receiver);
        Vector<Email> a=emailList.getEmailList();
        Iterator<Email> it = a.iterator();
        while(it.hasNext()){
           // System.out.println(a.);
            Email tt= it.next();
            tt.printEmail();
        }
*/
       Email email2= findRevById("1",receiver);
       email2.printEmail();
       // sendMail("1",send,receiver,"hi","fine thanks");

    }
}
