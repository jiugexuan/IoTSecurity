package client;

import client.UI.UI;
import com.fasterxml.jackson.core.JsonProcessingException;
import iotpackage.constructor.PackageConstructor;
import iotpackage.constructor.PackageParser;
import iotpackage.data.TS;
import iotpackage.data.autheticator.Authenticator;
import iotpackage.data.ciphertext.Ciphertext;
import iotpackage.data.ticket.Ticket;
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
    public String UserIP = "127.0.0.1";
    public String ASIP = "127.0.0.1";
    public String TGSIP = "127.0.0.1";
    public String SERIP = "127.0.0.1";
    public String Kctgs = "";
    public String Kcv = "";
    public String userAccount = "";


    public void CtoAS (){

    }



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
                userAccount = usr;
                char[] password= jTextField2.getPassword();
                String userKey = "18671752026";//String.valueOf(password);    //获取密码框内容
                if (usr.equals("") || userKey.equals("")) {
                    JOptionPane.showMessageDialog(null, "登入信息不能为空!");
                } else {
                    //登入 发报文到AS C->AS
                    PackageConstructor packageConstructor=new PackageConstructor();
                    Source source=new Source(userAccount,UserIP);
                    Destination destination=new Destination("AS",ASIP);
                    TS ts = new TS(1);
                    String content = null;
                    try {
                        content = packageConstructor.getPackageCtoASLogin("Verify","Request",source,destination,"0000", usr, TGSIP,ts,"","");
                    } catch (JsonProcessingException jsonProcessingException) {
                        jsonProcessingException.printStackTrace();
                        JOptionPane.showMessageDialog(null, "登入错误，请重试");
                        return;
                    }
                    System.out.print("\n 客户端发送："+content);

                    ConnManger cm = new ConnManger("as");
                    SocketConn conn = cm.getConn();
                    conn.send(content.getBytes());

                    //AS->C 接收
                    byte[] receiveBuffer = new byte[2048];
                    conn.receive(receiveBuffer);

                    try {
                        conn.close();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
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
                            JOptionPane.showMessageDialog(null, "登入错误，请重试");
                            return;
                        }
                        System.out.print("\n 提取出加密的ciphertext:"+packageParser.getCiphertext().getContext());
                        System.out.print("\n ciphertext: ");
                        Ciphertext ciphertext = packageParser.getCiphertext();
                        String cipText = "";
                        try {
                            //是用用户md5密钥解密的ciphertext
                            cipText = DESUtil.getDecryptString(ciphertext.getContext(),MD5Util.md5(userKey) );
                        } catch (IOException | NoSuchPaddingException | InvalidKeyException | NoSuchAlgorithmException | IllegalBlockSizeException ioException) {
                            ioException.printStackTrace();
                            JOptionPane.showMessageDialog(null, "登入错误，请重试");
                            return;
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
                            JOptionPane.showMessageDialog(null, "登入错误，请重试");
                            return;
                        }

                        //K c tgs
                        try {
                            Kctgs = packageParser.getKey(cipText);


                        } catch (JsonProcessingException jsonProcessingException) {
                            jsonProcessingException.printStackTrace();
                            JOptionPane.showMessageDialog(null, "登入错误，请重试");
                            return;
                        }
                        //C->TGS
                        String tgsContent = null;

                        try {


                            //解密AS发送报文，获得加密的ticket，发送给TGS，ticket用AS，TGS约定好的密码加密
                            tgsContent = packageParser.getTicketInSafety(cipText,new String());
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                            JOptionPane.showMessageDialog(null, "登入错误，请重试");
                            return;
                        }


                        String CtoTGS = null;
                            try {

                                CtoTGS = packageConstructor.getPackageCtoTGS("Verify","Request",source,new Destination("TGS",TGSIP),"0000",SERIP,tgsContent,"TGS", new Authenticator(new Destination("TGS",TGSIP), new Source(usr,UserIP),new TS(3)), Kctgs,"C to TGS","" );
                            } catch (JsonProcessingException jsonProcessingException) {
                                jsonProcessingException.printStackTrace();
                                JOptionPane.showMessageDialog(null, "登入错误，请重试");
                                return;
                            }
                            System.out.print("\n 客户端发送："+ CtoTGS);

                            ConnManger cmTGS = new ConnManger("TGS");
                            SocketConn connTGS = cmTGS.getConn();
                            connTGS.send(CtoTGS.getBytes());

                            //TGS -> C
                            byte[] receiveTGStoC= new byte[2048];
                            connTGS.receive(receiveTGStoC);
                            String recTGS = new String(receiveTGStoC);
                            System.out.print("\n从TGS收到报文："+recTGS);
                            PackageParser packageParserTGS= null;
                            Ticket ticketText = null;
                            String ciphercontent = null;
                            String DecryptCipher = null;

                            try {
                            packageParserTGS = new PackageParser(recTGS);
                            //cipher 在加密中的内容
                            ciphercontent = packageParserTGS.getCiphertext().getContext();
                            System.out.println("\n加密的cipher："+ciphercontent);
                            Ciphertext ciphertextTGS = packageParserTGS.getCiphertext();
                            ciphertextTGS.printCiphertext();
                            DecryptCipher = DESUtil.getDecryptString(ciphercontent,Kctgs);

                            System.out.print("\n解密后"+DecryptCipher);
                                try {
                                    Kcv = packageParserTGS.getKey(DecryptCipher);
                                    System.out.print("\n Kcv密码："+Kcv);
                                } catch (JsonProcessingException jsonProcessingException) {
                                    jsonProcessingException.printStackTrace();
                                    JOptionPane.showMessageDialog(null, "登入错误，请重试");
                                    return;
                                }


                        } catch (IOException | IllegalBlockSizeException | InvalidKeyException | BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException ee) {
                            ee.printStackTrace();
                                JOptionPane.showMessageDialog(null, "登入错误，请重试");
                                return;
                        }

                            //C -> Server

                            String ticketCtoV = null;
                        try {
                            ticketCtoV = packageParser.getTicketInSafety(DecryptCipher,"");
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }


                        String CtoSer = null;
                        System.out.print("\n Kcv密码："+Kcv);

                      try {
                            CtoSer = packageConstructor.getPackageCtoVVerify("Verify","Request",source,new Destination("Server",SERIP),"0000",ticketCtoV,"ticketV",new Authenticator(new Destination("Server",SERIP),source,new TS(5)),Kcv,"authenticator C","" );
                        } catch (JsonProcessingException jsonProcessingException) {
                            jsonProcessingException.printStackTrace();
                          JOptionPane.showMessageDialog(null, "登入错误，请重试");
                          return;
                        }
                        System.out.print("\n 客户端发送："+ CtoSer);

                        ConnManger cmSer = new ConnManger("SERVER");
                        SocketConn connSer = cmSer.getConn();
                        connSer.send(CtoSer.getBytes());

                        byte[] receiveSertoC= new byte[2048];
                        connSer.receive(receiveSertoC);
                        String recSer = new String(receiveSertoC);
                        System.out.print("\n从Server收到报文："+recSer);

                        PackageParser packageParserSer= null;
                        TS ts1 = null;

                        String info = null;
                        try {
                            packageParserSer = new PackageParser(recSer);
                            //cipher 在加密中的内容
                            String ciphercontentSer = packageParserSer.getCiphertext().getContext();
                            System.out.println("\n加密的cipher："+ciphercontentSer);
                            Ciphertext ciphertextSer = packageParserSer.getCiphertext();
                            ciphertextSer.printCiphertext();
                            String DecryptCipherSer = DESUtil.getDecryptString(ciphercontentSer,Kcv);
                            String time5 = packageParser.getTS(DecryptCipherSer);
                            System.out.print("\n解密后"+DecryptCipher);
                            System.out.print("\n解密后time5+1:"+time5);
                            if(time5 != null){
                                new UI(userAccount,Kcv);
                                setVisible(false);
                            }else {
                                JOptionPane.showMessageDialog(null, "登入错误，请重试");
                                return;
                            }

                        } catch (IOException | IllegalBlockSizeException | InvalidKeyException | BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException e2) {
                            e2.printStackTrace();
                            JOptionPane.showMessageDialog(null, "登入错误，请重试");
                            return;

                        }
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
