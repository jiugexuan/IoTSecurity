package client.UI;

import access.IPInTheItem;
import client.ConnManger;
import client.SocketConn;
import iotpackage.constructor.PackageConstructor;
import iotpackage.constructor.PackageParser;
import iotpackage.data.ciphertext.Ciphertext;
import iotpackage.data.fuction.emailList.ReceiveList;
import iotpackage.data.fuction.emailList.SendList;
import iotpackage.destination.Destination;
import iotpackage.source.Source;
import securityalgorithm.DESUtil;
import securityalgorithm.RSAUtil;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Vector;

public class Send extends JFrame {

    IPInTheItem ipInTheItem=new IPInTheItem();

    //publicKey,privateKey
    public String UserIP = ipInTheItem.getUserIP();
    public String ASIP = ipInTheItem.getASIP();
    public String TGSIP = ipInTheItem.getTGSIP();
    public String SERIP = ipInTheItem.getSERIP();
//    Map<String,String> keyMap= RSAUtil.createKeys(1024,ipInTheItem.getUserIP());
//    String publicKey=keyMap.get("privateKey");
//    String privateKey=keyMap.get("publicKey");

    Vector<Vector<String>> data=new Vector<>();
    JTable jTable=new JTable();
    public static Object[][] data1={};
  /*  public static Object[][] data = {
            { "1", "xxmhhh", "关于网安课设的安排", "安排如下，我们一起完成开发即可",
                    "2020-05-23 18:00" },
            { "2", "xxmhhh", "关于网安课设的安排", "安排如下，我们一起完成开发即可",
                    "2020-05-23 18:00" },
            { "3", "xxmhhh", "关于网安课设的安排", "安排如下，我们一起完成开发即可",
                    "2020-05-23 18:00" },
            { "4", "xxmhhh", "关于网安课设的安排", "安排如下，我们一起完成开发即可",
                    "2020-05-23 18:00" },
            { "3", "xxmhhh", "关于网安课设的安排", "安排如下，我们一起完成开发即可",
                    "2020-05-23 18:00" },
            { "4", "xxmhhh", "关于网安课设的安排", "安排如下，我们一起完成开发即可",
                    "2020-05-23 18:00" }
    };*/

    private void initGUI() {
        setLayout(null);
        setBounds(350, 100, 600, 450);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("2020网安邮箱-已发箱");
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);


        DefaultTableModel model=new DefaultTableModel();
        jTable=new JTable(model);

        Vector<String> conName=new Vector<>();
        conName.add("邮件ID");
        conName.add("发件人");
        conName.add("邮件标题");
        conName.add("邮件正文");
        conName.add("发送时间");



        model.setDataVector(data,conName);
//        MyTableModel myModel = new MyTableModel();// myModel存放表格的数据
//        final JTable table = new JTable(myModel);// 表格对象table的数据来源是myModel对象
        jTable.setPreferredScrollableViewportSize(new Dimension(600, 370));// 表格的显示尺寸
        jTable.getColumnModel().getColumn(0).setPreferredWidth(4);
        jTable.getColumnModel().getColumn(1).setPreferredWidth(4);
        jTable.addMouseListener(new MouseListener() {

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("12346789");
                // 点击几次，这里是双击事件
                if (e.getClickCount() == 1) {
                    System.out.println("16789");
                    int row = jTable.getSelectedRow();
                    System.out.println(row);
                    String revuser= jTable.getValueAt(row, 1).toString();//读取你获取行号的某一列的值（也就是字段）
                    System.out.println(revuser);
                    String ctime = jTable.getValueAt(row, 4).toString();
                    String title = jTable.getValueAt(row, 2).toString();
                    String context = jTable.getValueAt(row, 3).toString();
                    detail temp = new detail(title,revuser,context,ctime);
                }
            }


            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub

            }

        });
        // 产生一个带滚动条的面板
        JScrollPane scrollPane = new JScrollPane(jTable);
        scrollPane.setBounds(0, 30, 600, 370);
        add(scrollPane);




    }

    public String reciveMail(String User ,String Kcv) throws IOException {
        PackageConstructor packageConstructor = new PackageConstructor();
        Source source = new Source("SERVER",SERIP);
        Destination destination = new Destination(User,UserIP);
        String checkMail = packageConstructor.getPackageEmailCheck("Service","CheckRequest",source,destination,"0000",User,"","");
        ConnManger cm = new ConnManger("SERVER",SERIP);
        SocketConn conn = cm.getConn();
        conn.send(checkMail.getBytes());

        byte[] reciveBuffer = new byte[8024];

        conn.receive(reciveBuffer);
        String recContent = new String(reciveBuffer);
        PackageParser packageParser = new PackageParser(recContent);
        Ciphertext DeText = packageParser.getCiphertext();
        String DeMailList = DeText.getContext();
        String mailList = null;
        try {
            mailList = DESUtil.getDecryptString(DeMailList,Kcv);
        } catch (IOException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        System.out.println("mail"+mailList);
        return mailList;
    }

    class MyTableModel extends AbstractTableModel {
        //表格中第一行所要显示的内容存放在字符串数组columnNames中
        final String[] columnNames = {"邮件ID", "收件人", "邮件标题", "邮件正文",
                "发送时间"};
        //表格中各行的内容保存在二维数组data中


//下述方法是重写AbstractTableModel中的方法，其主要用途是被JTable对象调用，以便在表格中正确的显示出来。程序员必须根据采用的数据类型加以恰当实现。

        //获得列的数目
        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        //获得行的数目
        @Override
        public int getRowCount() {
            return data1.length;
        }

        //获得某列的名字，而目前各列的名字保存在字符串数组columnNames中
        @Override
        public String getColumnName(int col) {
            return columnNames[col];
        }

        //获得某行某列的数据，而数据保存在对象数组data中
        @Override
        public Object getValueAt(int row, int col) {
//JOptionPane.showMessageDialog(null,"本软件是地大数理学院java课程设计制作，软件相册路径为C盘下的showimg文件夹，请手动创建","Message",1);
            //return data[row][col];
            return null;
        }

        //判断每个单元格的类型
        @Override
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

    }

    public Send(String User ,String Kcv) throws IOException {
        super();
        String emailList = null;
        emailList = reciveMail(User,Kcv);
        SendList sendList = new SendList();
        new PackageParser().getEmailList(emailList,sendList);
        sendList.printSendList();
        int num = sendList.getListNumber();
        for(int i=0;i<num;i++){
            Vector<String> dataEmail=new Vector<>();
            dataEmail.add(sendList.getEmailAtIndex(i).getId());
            dataEmail.add(sendList.getEmailAtIndex(i).getSender().getAccount());

            dataEmail.add( sendList.getEmailAtIndex(i).getTitle());
            dataEmail.add( sendList.getEmailAtIndex(i).getContext());
            dataEmail.add( sendList.getEmailAtIndex(i).getTime());
            data.add(dataEmail);

            // j table.addRow(item);
            //data[]
        }

//        for (int i = 0; i < num ; i++){
//            data[i][0] = sendList.getEmailAtIndex(i).getId();
//            data[i][1] = sendList.getEmailAtIndex(i).getSender().getAccount();
//            data[i][2] = sendList.getEmailAtIndex(i).getTitle();
//            data[i][3] = sendList.getEmailAtIndex(i).getContext();
//            data[i][4] = sendList.getEmailAtIndex(i).getTime();
//        }

        initGUI();
    }

	/*
	public static void main(String[] args) {
		  SwingUtilities.invokeLater(new Runnable() {
		   public void run() {
		    send inst = new send();
		   }
		  });
		 }
		 */


}

//把要显示在表格中的数据存入字符串数组和Object数组中

