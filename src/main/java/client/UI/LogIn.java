package client.UI;

import client.ConnManger;
import client.SocketConn;
import com.fasterxml.jackson.core.JsonProcessingException;
import iotpackage.constructor.PackageConstructor;
import iotpackage.constructor.PackageParser;
import iotpackage.data.TS;
import iotpackage.data.autheticator.Authenticator;
import iotpackage.data.ciphertext.Ciphertext;
import iotpackage.destination.Destination;
import iotpackage.source.Source;
import securityalgorithm.DESUtil;
import securityalgorithm.MD5Util;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class LogIn extends JFrame {



    private void initGUI() {

        setLayout(null);
        setBounds(350, 100, 400, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("2020网安邮箱-登录");
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);


        JLabel jLabel1 = new JLabel();
        jLabel1.setText("用户账户");
        jLabel1.setBounds(30, 20, 70, 30);
        add(jLabel1);

        final JTextField jTextField1 = new JTextField();
        jTextField1.setBounds(100,20,240,30);
        add(jTextField1);

        JLabel jLabel2 = new JLabel();
        jLabel2.setText("用户密码");
        jLabel2.setBounds(30, 70, 360, 30);
        add(jLabel2);

        final JPasswordField jTextField2 = new JPasswordField();
        jTextField2.setBounds(100,70,240,30);
        add(jTextField2);

        JButton jButton1 = new JButton();
        jButton1.setText("登录");
        jButton1.setBounds(30,130,150,30);
        add(jButton1);
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usr = "18671752026";//jTextField1.getText().toString();    //获取文本框内容
                char[] password= jTextField2.getPassword();
                String userKey = "18671752026";//String.valueOf(password);    //获取密码框内容

                if (usr.equals("") || userKey.equals("")) {
                    JOptionPane.showMessageDialog(null, "登入信息不能为空!");
                } else {
                    PackageConstructor packageConstructor=new PackageConstructor();
                    Source source=new Source("user1","127.0.0.1");
                    Destination destination=new Destination("AS","127.0.0.1");
                    TS ts = new TS(1);
                    String content = null;
                    try {
                        content = packageConstructor.getPackageCtoASLogin("Verify","Request",source,destination,"0000", usr, "127.0.0.1",ts,"");
                    } catch (JsonProcessingException jsonProcessingException) {
                        jsonProcessingException.printStackTrace();
                    }
                    System.out.print("\n 客户端发送："+content);

                    ConnManger cm = new ConnManger("as");
                    SocketConn conn = cm.getConn();
                    conn.send(content.getBytes());

                    byte[] receiveBuffer = new byte[2048];
                    conn.receive(receiveBuffer);
                    String rec = new String(receiveBuffer);

                    System.out.println("\n as返回消息："+ rec);
                    System.out.print("\n 正在验证返回报文");

                    String errorID = "0102";

                    if (rec.contains(errorID)){
                        JOptionPane.showMessageDialog(null, "无用户ID");
                        System.out.print("\n 无用户ID，登入失败");
                    }else {
                        PackageParser packageParser = null;
                        try {
                            packageParser = new PackageParser(rec);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        System.out.print("\n 提取出加密ciphertext:"+packageParser.getCiphertext().getContext());
                        System.out.print("\n ciphertext: ");

                        Ciphertext ciphertext = packageParser.getCiphertext();

                        String cipText = "";

                        try {
                            //md5密钥加密
                            cipText = DESUtil.getDecryptString(ciphertext.getContext(),MD5Util.md5(userKey) );
                        } catch (IOException | NoSuchPaddingException | InvalidKeyException | NoSuchAlgorithmException | IllegalBlockSizeException ioException) {
                            ioException.printStackTrace();
                        } catch (BadPaddingException badPaddingException) {
                            System.out.print("\n 密钥错误 \n");
                            //badPaddingException.printStackTrace();
                            JOptionPane.showMessageDialog(null, "密码错误");
                            jTextField2.setText("");
                            return;
                        }
                            System.out.println("\n"+cipText);
                        try {
                            System.out.println(packageParser.getTicketInSafety(cipText,new String()));
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        String tgsContent = null;
                        try {
                            tgsContent = packageParser.getTicketInSafety(cipText,new String());
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }

                        String CtoTGS = null;
                            try {
                                //authkey"1234578"
                                CtoTGS = packageConstructor.getPackageCtoTGS("Verify","Request",source,destination,"0000","127.0.0.1:9120",tgsContent,"TGS", new Authenticator(new Destination("coueg","127.569.321"), new Source("accoutTO","192.168.1.7"),new TS(3)), "1234578","7","" );
                            } catch (JsonProcessingException jsonProcessingException) {
                                jsonProcessingException.printStackTrace();
                            }
                            System.out.print("\n 客户端发送："+ CtoTGS);

                            ConnManger cmTGS = new ConnManger("TGS");
                            SocketConn connTGS = cmTGS.getConn();
                            connTGS.send(CtoTGS.getBytes());

                            byte[] receiveTGStoC= new byte[2048];
                            connTGS.receive(receiveTGStoC);
                            String recTGS = new String(receiveBuffer);

                    }
                }

            }
        });

        JButton jButton2 = new JButton();
        jButton2.setText("注册");
        jButton2.setBounds(200,130,150,30);
        add(jButton2);
        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Reg temp = new Reg();
            }
        });







    }

    public LogIn()
    {
        super();
        initGUI();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LogIn inst = new LogIn();
            }
        });
    }
}
