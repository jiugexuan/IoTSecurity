package client.UI;

import Server.Server;
import access.IPInTheItem;
import client.ConnManger;
import client.SocketConn;
import com.fasterxml.jackson.core.JsonProcessingException;
import iotpackage.constructor.PackageConstructor;
import iotpackage.constructor.PackageParser;
import iotpackage.data.TS;
import iotpackage.data.fuction.Email;
import iotpackage.data.fuction.User.Receiver;
import iotpackage.data.fuction.User.Sender;
import iotpackage.destination.Destination;
import iotpackage.source.Source;
import securityalgorithm.RSAUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Map;

public class Writer extends JFrame {
    IPInTheItem ipInTheItem=new IPInTheItem();
//    Map<String,String> keyMap= RSAUtil.createKeys(1024,ipInTheItem.getUserIP());
//    String publicKey=keyMap.get("privateKey");
//    String privateKey=keyMap.get("publicKey");
    //publicKey,privateKey
    public String UserIP = ipInTheItem.getUserIP();
    public String ASIP = ipInTheItem.getASIP();
    public String TGSIP = ipInTheItem.getTGSIP();
    public String SERIP = ipInTheItem.getSERIP();

    private void initGUI(String user,String Kcv) {
        setLayout(null);
        setBounds(350, 100, 400, 490);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("2020网安邮箱-写邮件");
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);


        JLabel jLabel1 = new JLabel();
        jLabel1.setText("收件人");
        jLabel1.setBounds(30, 20, 50, 30);
        add(jLabel1);

        JTextField jTextField1 = new JTextField();
        jTextField1.setBounds(100,20,240,30);
        add(jTextField1);

        JLabel jLabel3 = new JLabel();
        jLabel3.setText("邮件标题");
        jLabel3.setBounds(30, 70, 70, 30);
        add(jLabel3);

        JTextField jTextField3 = new JTextField();
        jTextField3.setBounds(100,70,240,30);
        add(jTextField3);

        JLabel jLabel2 = new JLabel();
        jLabel2.setText("邮件内容");
        jLabel2.setBounds(30, 120, 70, 30);
        add(jLabel2);

        JTextArea jTextField2 = new JTextArea();
        jTextField2.setBounds(30,150,310,200);
        add(jTextField2);




        JButton jButton1 = new JButton();
        jButton1.setText("确认发送");
        jButton1.setBounds(30,390,310,40);
        add(jButton1);
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PackageConstructor packageConstructor = new PackageConstructor();
                ConnManger cm = new ConnManger("SERVER",SERIP);
                SocketConn conn = cm.getConn();
                TS ts = new TS(1);
                Email email = new Email(ts.getContext(),new Sender(user,"飞翔的企鹅"),new Receiver(jTextField1.getText(),""),jTextField3.getText(),ts.getContext(),"text",jTextField2.getText());
                String content = null;
                try {
                    content = packageConstructor.getPackageEmailSend("Service","Send",new Source(user,"127.0.0.1"),new Destination("SERVER",SERIP),"0000",Kcv,email,"","");
                } catch (JsonProcessingException jsonProcessingException) {
                    jsonProcessingException.printStackTrace();
                }
                conn.send(content.getBytes());
                System.out.println("邮件发送："+content);

                byte[] reciveBuffer = new byte[2024];
                conn.receive(reciveBuffer);
                PackageParser packageParser = null;
                try {
                    packageParser = new PackageParser(new String(reciveBuffer));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                String code = packageParser.getCode();
                System.out.println("\ncode"+code);
                if (code.contains("0104")){
                    JOptionPane.showMessageDialog(null, "发送错误，接收方不存在或网络故障，请重试");
                }else if (code.contains("1000")){
                    JOptionPane.showMessageDialog(null, "发送成功");
                    setVisible(false);
                }
            }
        });




    }

    public Writer(String user,String Kcv)
    {
        super();
        initGUI(user,Kcv);
    }

	/*
	public static void main(String[] args) {
		  SwingUtilities.invokeLater(new Runnable() {
		   public void run() {
		    write inst = new write();
		   }
		  });
		 }
		 */

}
