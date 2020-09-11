package client;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//import APP.Application;
//import Client.PrepareConn;
//import Databean.User;
//import Message.*;
//import Security.DES.Des;

public class Chat extends JFrame implements ActionListener{

    /**
     *
     */
    static byte[] receiveBuffer = new byte[8216];
    private static final long serialVersionUID = 1L;
    private JLabel back;
    private JLabel jt1=new JLabel();
    private JLabel jt2=new JLabel();
    private JTextField jt = new JTextField();		//创建带有初始化文本的文本框对象
    //private JPasswordField jp=new JPasswordField(20);
    private JButton xa=new JButton();
    //private JButton xb=new JButton();

    public Chat(){
        this.setResizable(false); 		//不能修改大小
        this.getContentPane().setLayout(null);
        this.setTitle("会话");
        this.setSize(450,350);

        //设置运行位置，是对话框居中
        Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((int)(screenSize.width-350)/2,(int)(screenSize.height-600)/2+45);

        back=new JLabel();

        back.setBounds(-0, 0, 450, 350);

        jt.setForeground(Color.gray);
        jt.setBounds(95, 100, 200, 100);
        jt.setFont(new Font("Serif",Font.PLAIN,12));


        jt1.setBounds(40, 90, 80, 60);
        jt1.setFont(new Font("黑体",Font.PLAIN,16));
        jt1.setForeground(Color.BLACK);
        jt1.setText("内容:");




        xa.setText("发送");
        xa.setFont(new Font("Dialog",0,12));
        xa.setBounds(95, 200, 150, 30);
        xa.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        xa.setBackground(getBackground());
        xa.setBackground(Color.white);
        Border b = new LineBorder(Color.white, 2);
        xa.setBorder(b);
        xa.setVisible(true);

        xa.addActionListener(this);
        this.getContentPane().add(jt);
        this.getContentPane().add(jt1);
        this.getContentPane().add(jt2);
        this.getContentPane().add(xa);
        this.getContentPane().add(back);
        this.setVisible(true);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub


    }

}
