package client.UI;

import com.fasterxml.jackson.core.JsonProcessingException;
import iotpackage.data.fuction.User.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class UI extends JFrame {

    private void initGUI(String user,String Kcv) {
        setLayout(null);
        setBounds(350, 100, 430, 280);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("2020网安邮箱");
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        JButton jButton1 = new JButton();
        jButton1.setText("写邮件");
        jButton1.setBounds(20,20,200,40);
        add(jButton1);
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("UI 传送 的 Kcv"+Kcv);
                Writer tempp = new Writer(user,Kcv);
            }
        });

        JButton jButton2 = new JButton();
        jButton2.setText("已发箱");
        jButton2.setBounds(20,80,200,40);
        add(jButton2);
        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Send tempp = new Send(user,Kcv);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        JButton jButton3 = new JButton();
        jButton3.setText("收件箱");
        jButton3.setBounds(20,140,200,40);
        add(jButton3);
        jButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Rev hhh = new Rev(user ,Kcv);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        JLabel jLabel1 = new JLabel();
        jLabel1.setText("开发小组：孙友轩 崔祥森 张浩然 夏星明");
        jLabel1.setBounds(20, 200, 360, 30);
        add(jLabel1);

        JLabel jLabel2 = new JLabel();
        jLabel2.setText("飞翔的企鹅");
        jLabel2.setBounds(260, 30, 360, 30);
        add(jLabel2);

        JLabel jLabel3 = new JLabel();
        jLabel3.setText(user);
        jLabel3.setBounds(260, 60, 360, 30);
        add(jLabel3);

        JButton jButton4 = new JButton();
        jButton4.setText("修改密码");
        jButton4.setBounds(260,100,100,25);
        add(jButton4);
        jButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    try {
                        changepwd hhhh = new changepwd(user,Kcv);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                    noSuchAlgorithmException.printStackTrace();
                }
            }
        });






    }

    public UI(String user,String Kcv)
    {
        super();
        initGUI(user,Kcv);
    }

	/*
	public static void main(String[] args) {
		  SwingUtilities.invokeLater(new Runnable() {
		   public void run() {
		    UI inst = new UI();
		   }
		  });
		 }
	*/
}
