package client.UI;


import access.IPInTheItem;
import client.ConnManger;
import client.SocketConn;
import iotpackage.constructor.PackageConstructor;
import iotpackage.constructor.PackageParser;
import iotpackage.data.ciphertext.Ciphertext;
import iotpackage.destination.Destination;
import iotpackage.source.Source;
import securityalgorithm.DESUtil;
import securityalgorithm.RSAUtil;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Random;

public class changepwd  extends JFrame {
    IPInTheItem ipInTheItem=new IPInTheItem();

    //publicKey,privateKey
    public String UserIP = ipInTheItem.getUserIP();
    public String ASIP = ipInTheItem.getASIP();
    public String TGSIP = ipInTheItem.getTGSIP();
    public String SERIP = ipInTheItem.getSERIP();
    Map<String,String> keyMap= RSAUtil.createKeys(1024,ipInTheItem.getUserIP());
    String publicKey=keyMap.get("publicKey");
    String privateKey=keyMap.get("privateKey");
    private void initGUI() {
        //创建Random类对象
        Random random = new Random();
        //产生随机数
        int codenumber = random.nextInt(9989 - 1234 + 1) + 1234;
        setLayout(null);
        setBounds(350, 100, 400, 300);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("2020网安邮箱-修改密码");
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        JLabel jLabel1 = new JLabel();

        JLabel jLabel2 = new JLabel();
        jLabel2.setText("新密码");
        jLabel2.setBounds(30, 20, 360, 30);
        add(jLabel2);

        JPasswordField jTextField2 = new JPasswordField();
        jTextField2.setBounds(100,20,240,30);
        add(jTextField2);

        JLabel jLabel3 = new JLabel();
        jLabel3.setText("确认密码");
        jLabel3.setBounds(30, 70, 360, 30);
        add(jLabel3);

        JPasswordField jTextField3 = new JPasswordField();
        jTextField3.setBounds(100,70,240,30);
        add(jTextField3);

        JLabel jLabel4 = new JLabel();
        jLabel4.setText("验证码："+codenumber);
        jLabel4.setBounds(30, 120, 200, 30);
        add(jLabel4);

        JLabel jLabel5 = new JLabel();
        jLabel5.setText("输入验证码");
        jLabel5.setBounds(30, 170, 70, 30);
        add(jLabel5);

        JTextField jTextField4 = new JTextField();
        jTextField4.setBounds(100,170,240,30);
        add(jTextField4);

        JButton jButton1 = new JButton();
        jButton1.setText("修改密码");
        jButton1.setBounds(30,220,310,30);
        add(jButton1);
    }

    public void reciveChange(String User ,String Kcv) throws IOException {
        PackageConstructor packageConstructor = new PackageConstructor();
        Source source = new Source("SERVER", SERIP);
        Destination destination = new Destination(User, UserIP);
        String change = packageConstructor.getPackagePWDChange("Service", "ChangePWD", source, destination, "0000", User, privateKey, publicKey);
        ConnManger cm = new ConnManger("SERVER", SERIP);
        SocketConn conn = cm.getConn();
        conn.send(change.getBytes());
        byte[] reciveBuffer = new byte[8024];
        conn.receive(reciveBuffer);
    }

    public changepwd() throws NoSuchAlgorithmException {
        super();
        initGUI();
    }

	/*
	public static void main(String[] args) {
		  SwingUtilities.invokeLater(new Runnable() {
		   public void run() {
		   changepwd inst = new changepwd();
		   }
		  });
		 }*/



}
