package Util;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JDBCUtils {
    public static Connection getConnection() throws Exception {
        // 1. 讀取配置文件4個基本訊息
//        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
//        Properties prop = new Properties();
//        prop.load(is);
//        String user = prop.getProperty("username");
//        String password = prop.getProperty("password");
//        String url = prop.getProperty("url");
//        String driverClass = prop.getProperty("driverClassName");

//        String user = "root";
//        String password = "root";
//        String url = "jdbc:mysql://localhost:3306/test?rewriteBatchedStatements=true&useUnicode=true&characterEncoding=utf-8";
//        String driverClass = "com.mysql.cj.jdbc.Driver";

        String user = "410977003";
        String password = "410977003";
        String url = "jdbc:mysql://140.127.74.186/410977003?rewriteBatchedStatements=true&useUnicode=true&characterEncoding=utf-8";
        String driverClass = "com.mysql.cj.jdbc.Driver";

        // 2. 加載 Driver
        Class.forName(driverClass);
        /* 加載 mysql Driver時, 自動的註冊了Driver
          static {
                try {
                    DriverManager.registerDriver(new Driver());
                } catch (SQLException var1) {
                    throw new RuntimeException("Can't register driver!");
                }
            }
        * */

        // 3. 連接Sql
        Connection cnn = DriverManager.getConnection(url, user, password);
        return cnn;
    }

    public static void closeResource(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeResource(PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeResource(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeResource(Connection conn, PreparedStatement ps) {
        JDBCUtils.closeResource(conn);
        JDBCUtils.closeResource(ps);
    }
    public static void closeResource(PreparedStatement ps, ResultSet rs) {
        JDBCUtils.closeResource(ps);
        JDBCUtils.closeResource(rs);
    }
    public static void closeResource(Connection conn, PreparedStatement ps, ResultSet rs) {
        JDBCUtils.closeResource(conn, ps);
        JDBCUtils.closeResource(rs);
    }
}
