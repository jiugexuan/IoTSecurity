package client.UI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
                Send tempp = new Send();
            }
        });

        JButton jButton3 = new JButton();
        jButton3.setText("收件箱");
        jButton3.setBounds(20,140,200,40);
        add(jButton3);
        jButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Rev hhh = new Rev();
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
                changepwd hhhh = new changepwd();
            }
        });


        JButton jButton5 = new JButton();
        jButton5.setText("开发面板");
        jButton5.setBounds(260,200,100,25);
        add(jButton5);



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
