package client.UI;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author 19710
 */
public class Rev extends JFrame {
    private void initGUI() {
        setLayout(null);
        setBounds(350, 100, 600, 450);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("2020网安邮箱-收件箱");
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        MyTableModel1 myModel = new MyTableModel1();// myModel存放表格的数据
        final JTable table = new JTable(myModel);// 表格对象table的数据来源是myModel对象
        table.setPreferredScrollableViewportSize(new Dimension(600, 370));// 表格的显示尺寸
        table.getColumnModel().getColumn(0).setPreferredWidth(4);
        table.getColumnModel().getColumn(1).setPreferredWidth(4);
        table.addMouseListener(new MouseListener() {

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // 点击几次，这里是双击事件
                if (e.getClickCount() == 2) {
                    int row = table.getSelectedRow();
                    System.out.println(row);
                    String revuser= table.getValueAt(row, 1).toString();//读取你获取行号的某一列的值（也就是字段）
                    System.out.println(revuser);
                    String ctime = table.getValueAt(row, 4).toString();
                    String title = table.getValueAt(row, 2).toString();
                    String context = table.getValueAt(row, 3).toString();
                    detail2 temp = new detail2(title,revuser,context,ctime);
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
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 30, 600, 370);
        add(scrollPane);




    }

    public Rev()
    {
        super();
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
class MyTableModel1 extends AbstractTableModel {
    //表格中第一行所要显示的内容存放在字符串数组columnNames中
    final String[] columnNames = {"邮件ID", "发件人", "邮件标题", "邮件正文",
            "发送时间"};
    //表格中各行的内容保存在二维数组data中
    final Object[][] data = {
            { "1", "xxmhhh", "关于网安课设的安排", "安排如下，我们一起完成开发即可",
                    "2020-05-23 18:00" },
            { "2", "xxmhhh", "关于网安课设的安排", "安排如下，我们一起完成开发即可",
                    "2020-05-23 18:00" },
            { "3", "xxmhhh", "关于网安课设的安排", "安排如下，我们一起完成开发即可",
                    "2020-05-23 18:00" },
            { "4", "xxmhhh", "关于网安课设的安排", "安排如下，我们一起完成开发即可",
                    "2020-05-23 18:00" },
            { "5", "xxmhhh", "关于网安课设的安排", "安排如下，我们一起完成开发即可",
                    "2020-05-23 18:00" },
            { "6", "xxmhhh", "关于网安课设的安排", "安排如下，我们一起完成开发即可",
                    "2020-05-23 18:00" },
            { "7", "xxmhhh", "关于网安课设的安排", "安排如下，我们一起完成开发即可",
                    "2020-05-23 18:00" },
            { "8", "xxmhhh", "关于网安课设的安排", "安排如下，我们一起完成开发即可",
                    "2020-05-23 18:00" },
            { "9", "xxmhhh", "关于网安课设的安排", "安排如下，我们一起完成开发即可",
                    "2020-05-23 18:00" },
            { "10", "xxmhhh", "关于网安课设的安排", "安排如下，我们一起完成开发即可",
                    "2020-05-23 18:00" },
            { "11", "xxmhhh", "关于网安课设的安排", "安排如下，我们一起完成开发即可",
                    "2020-05-23 18:00" },
            { "12", "xxmhhh", "关于网安课设的安排", "安排如下，我们一起完成开发即可",
                    "2020-05-23 18:00" },
            { "13", "xxmhhh", "关于网安课设的安排", "安排如下，我们一起完成开发即可",
                    "2020-05-23 18:00" },
            { "14", "xxmhhh", "关于网安课设的安排", "安排如下，我们一起完成开发即可",
                    "2020-05-23 18:00" },
            { "15", "xxmhhh", "关于网安课设的安排", "安排如下，我们一起完成开发即可",
                    "2020-05-23 18:00" },
            { "16", "xxmhhh", "关于网安课设的安排", "安排如下，我们一起完成开发即可",
                    "2020-05-23 18:00" },
            { "17", "xxmhhh", "关于网安课设的安排", "安排如下，我们一起完成开发即可",
                    "2020-05-23 18:00" },
            { "18", "xxmhhh", "关于网安课设的安排", "安排如下，我们一起完成开发即可",
                    "2020-05-23 18:00" },
            { "19", "xxmhhh", "关于网安课设的安排", "安排如下，我们一起完成开发即可",
                    "2020-05-23 18:00" },
            { "20", "xxmhhh", "关于网安课设的安排", "安排如下，我们一起完成开发即可",
                    "2020-05-23 18:00" },
            { "21", "xxmhhh", "关于网安课设的安排", "安排如下，我们一起完成开发即可",
                    "2020-05-23 18:00" },
            { "22", "xxmhhh", "关于网安课设的安排", "安排如下，我们一起完成开发即可",
                    "2020-05-23 18:00" },
            { "23", "xxmhhh", "关于网安课设的安排", "安排如下，我们一起完成开发即可",
                    "2020-05-23 18:00" },
            { "24", "xxmhhh", "关于网安课设的安排", "安排如下，我们一起完成开发即可",
                    "2020-05-23 18:00" },
            { "25", "xxmhhh", "关于网安课设的安排", "安排如下，我们一起完成开发即可",
                    "2020-05-23 18:00" },
            { "26", "xxmhhh", "关于网安课设的安排", "安排如下，我们一起完成开发即可",
                    "2020-05-23 18:00" },
            { "27", "xxmhhh", "关于网安课设的安排", "安排如下，我们一起完成开发即可",
                    "2020-05-23 18:00" },
            { "28", "xxmhhh", "关于网安课设的安排", "安排如下，我们一起完成开发即可",
                    "2020-05-23 18:00" },
            { "29", "xxmhhh", "关于网安课设的安排", "安排如下，我们一起完成开发即可",
                    "2020-05-23 18:00" },
            { "30", "xxmhhh", "关于网安课设的安排", "安排如下，我们一起完成开发即可",
                    "2020-05-23 18:00" },
    };

//下述方法是重写AbstractTableModel中的方法，其主要用途是被JTable对象调用，以便在表格中正确的显示出来。程序员必须根据采用的数据类型加以恰当实现。

    //获得列的数目
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    //获得行的数目
    @Override
    public int getRowCount() {
        return data.length;
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
        return data[row][col];
    }

    //判断每个单元格的类型
    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

}
