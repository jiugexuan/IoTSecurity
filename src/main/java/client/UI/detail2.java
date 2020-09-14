package client.UI;

import javax.swing.*;

public class detail2  extends JFrame {
    private void initGUI(String title,String revuser,String context,String ctime) {
        setLayout(null);
        setBounds(350, 100, 400, 520);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("2020网安邮箱-邮件详情");
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);


        JLabel jLabel1 = new JLabel();
        jLabel1.setText("发件人");
        jLabel1.setBounds(30, 20, 50, 30);

        add(jLabel1);

        JTextField jTextField1 = new JTextField();
        jTextField1.setBounds(100,20,240,30);
        add(jTextField1);
        jTextField1.setText(revuser);
        jTextField1.setEditable(false);

        JLabel jLabel3 = new JLabel();
        jLabel3.setText("邮件标题");
        jLabel3.setBounds(30, 70, 50, 30);
        add(jLabel3);


        JTextField jTextField3 = new JTextField();
        jTextField3.setBounds(100,70,240,30);
        add(jTextField3);
        jTextField3.setText(title);
        jTextField3.setEditable(false);

        JLabel jLabel2 = new JLabel();
        jLabel2.setText("邮件内容");
        jLabel2.setBounds(30, 120, 50, 30);
        add(jLabel2);

        JTextArea jTextField2 = new JTextArea();
        jTextField2.setBounds(30,150,310,200);
        add(jTextField2);
        jTextField2.setEditable(false);
        jTextField2.setText(context);

        JLabel jLabel4= new JLabel();
        jLabel4.setText("邮件发送时间："+ctime);
        jLabel4.setBounds(30, 370, 400, 30);
        add(jLabel4);


        JButton jButton1 = new JButton();
        jButton1.setText("确认");
        jButton1.setBounds(30,420,310,40);
        add(jButton1);




    }

    public detail2(String title,String revuser,String context,String ctime)
    {
        super();
        initGUI(title,revuser, context, ctime);
    }

	/*
	public static void main(String[] args) {
		  SwingUtilities.invokeLater(new Runnable() {
		   public void run() {
		    detail inst = new detail("一个标题","user","66666666666666666666666666666","2020-05-01 18:00");
		   }
		  });
		 }
		 */




}
