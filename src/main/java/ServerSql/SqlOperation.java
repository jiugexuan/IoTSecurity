package ServerSql;

import java.sql.*;

public class SqlOperation {

    static Statement stmt = null;
    static ResultSet rs = null;
    static Connection conn = null;
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String USER = "root";
    static final String PASS = "123456";
    static String DB_URL = "jdbc:mysql://47.115.12.18:3306/mailsystem";

    static void OpenConn() {
        stmt = null;
        rs = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            System.out.println("\n dataconn 数据库连接成功");
        } catch (Exception e) {
            System.err.println("\n dataconn 数据库连接失败 " + e.getMessage());
        }
    }

    public static ResultSet executeQuery(String sql) {
        stmt = null;
        rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.err.println("查询数据" + e.getMessage());
        }
        return rs;
    }

    public static int executeUpdate(String sql) {
        stmt = null;
        rs = null;
        try {
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            stmt.executeUpdate(sql);
            // conn.commit();
        } catch (SQLException e) {
            System.err.println("\n dataconn 更新数据" + e.getMessage());
            return 0;
        }
        return 1;
    }

    static void CloseConn() {
        try {
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.err.println("\n dataconn 连接释放");
        }
    }



}
