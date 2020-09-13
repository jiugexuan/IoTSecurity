package client;

import access.IPInTheItem;
import client.ConnManger;
import client.LogIn;
import client.SocketConn;
import com.fasterxml.jackson.core.JsonProcessingException;
import iotpackage.IPInfo;
import iotpackage.constructor.PackageConstructor;
import iotpackage.destination.Destination;
import iotpackage.source.Source;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Reg extends JFrame {
    IPInTheItem ipInTheItem=new IPInTheItem();
    public String UserIP = ipInTheItem.getUserIP();
    public String ASIP = ipInTheItem.getASIP();

    private void initGUI() {
        //创建Random类对象
        Random random = new Random();
        //产生随机数
        int codenumber = random.nextInt(9989 - 1234 + 1) + 1234;


        setLayout(null);
        setBounds(350, 100, 400, 580);
        setTitle("2020网安邮箱-注册");
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);


        JLabel jLabel1 = new JLabel();
        jLabel1.setText("用户账户*");
        jLabel1.setBounds(30, 20, 70, 30);
        add(jLabel1);

        final JTextField jTextField1 = new JTextField();
        jTextField1.setBounds(100,20,240,30);
        add(jTextField1);

        JLabel jLabel2 = new JLabel();
        jLabel2.setText("用户密码*");
        jLabel2.setBounds(30, 70, 360, 30);
        add(jLabel2);

        final JPasswordField jTextField2 = new JPasswordField();
        jTextField2.setBounds(100,70,240,30);
        add(jTextField2);

        JLabel jLabel3 = new JLabel();
        jLabel3.setText("昵称*");
        jLabel3.setBounds(30, 120, 360, 30);
        add(jLabel3);

        final JTextField jTextField3 = new JTextField();
        jTextField3.setBounds(100,120,240,30);
        add(jTextField3);

        final JLabel jLabel6 = new JLabel();
        jLabel6.setText("安全问题");
        jLabel6.setBounds(30, 270, 360, 30);
        add(jLabel6);

        final JTextField jTextField6 = new JTextField();
        jTextField6.setBounds(100,270,240,30);
        add(jTextField6);

        JLabel jLabel7 = new JLabel();
        jLabel7.setText("问题答案");
        jLabel7.setBounds(30, 320, 360, 30);
        add(jLabel7);

        final JTextField jTextField7 = new JTextField();
        jTextField7.setBounds(100,320,240,30);
        add(jTextField7);

        JLabel jLabel4 = new JLabel();
        jLabel4.setText("验证码："+codenumber);
        jLabel4.setBounds(30, 170, 200, 30);
        add(jLabel4);

        JLabel jLabel5 = new JLabel();
        jLabel5.setText("输入验证码");
        jLabel5.setBounds(30, 220, 70, 30);
        add(jLabel5);

        JTextField jTextField4 = new JTextField();
        jTextField4.setBounds(100,220,240,30);
        add(jTextField4);

        final JButton jButton1 = new JButton();
        jButton1.setText("注册");
        jButton1.setBounds(30,440,310,30);
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usr = jTextField1.getText().toString();    //获取文本框内容
                char[] password= jTextField2.getPassword();
                String userKey = String.valueOf(password);    //获取密码框内容
                String nick = jTextField3.getText().toString();
                String question = jTextField6.getText();
                String answer = jTextField7.getText();
                if (usr.equals("") || userKey.equals("")|| nick.equals("")) {
                    JOptionPane.showMessageDialog(null, "*信息不能为空!");
                    setVisible(false);// 本窗口隐藏,
                } else {
                    PackageConstructor packageConstructor=new PackageConstructor();
                    Source source=new Source(usr,UserIP);
                    Destination destination=new Destination("AS",ASIP);
                    String content = "";
                    try {
                        content = packageConstructor.getPackageCtoASRegist("Register","Request",source,destination,"0000", usr, userKey, nick, question, answer , "");
                    } catch (JsonProcessingException jsonProcessingException) {
                        jsonProcessingException.printStackTrace();
                    }
                    ConnManger cm = new ConnManger("as",ASIP);
                    SocketConn conn = cm.getConn();
                    conn.send(content.getBytes());
                    System.out.print(content);
                    byte[] receiveBuffer = new byte[2048];
                    conn.receive(receiveBuffer);
                    String rec = new String(receiveBuffer);
                    System.out.println("\n as返回消息："+ rec);
                    System.out.print("注册提交...");
                    setVisible(false);// 本窗口隐藏,
                    if (rec.contains("0103")){
                        JOptionPane.showMessageDialog(null, "用户ID已存在数据库中");
                        System.out.print("\n 用户ID已存在数据库中");
                    }else if (rec.contains("0100")){
                        JOptionPane.showMessageDialog(null, "注册成功");
                        System.out.print("\n 注册成功");
                    }else if (rec.contains("0104")){
                        JOptionPane.showMessageDialog(null, "注册失败");
                        System.out.print("\n 注册失败");
                    }
                }
                try {
                    LogIn login = new LogIn();
                } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                    noSuchAlgorithmException.printStackTrace();
                }
            }
        });
        add(jButton1);
    }

    public Reg()
    {
        super();
        initGUI();
    }

}
