package client.UI;

import javax.swing.*;

public class Writer extends JFrame {
    private void initGUI() {
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




    }

    public Writer()
    {
        super();
        initGUI();
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
