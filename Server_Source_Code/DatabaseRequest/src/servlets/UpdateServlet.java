package servlets;

import Util.JDBCUtils;
import jdk.nashorn.internal.scripts.JD;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/Update")
public class UpdateServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,PUT,DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        String sql = request.getParameter("sql");
        //        sql = "INSERT INTO customers(NAME, email, birth) VALUES('TestDB','testdb@123.com', '1999-01-01')";
        if(sql == null || "".equals(sql)){
            response.getWriter().write("[]");
        }else {
            Integer changeItem = update(sql);
            System.out.println(changeItem);
            response.getWriter().write("[{\"change\":"+ changeItem + "}]");
        }
    }
    public int update(String sql, Object... args) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JDBCUtils.getConnection();
            // 1. 預編譯sql語句，返回PreparedStatement的實例
            ps = conn.prepareStatement(sql);
            // 2. 填充佔位符
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            // 3. 執行: 回傳數據庫引響行數
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 4. 關閉資源
            JDBCUtils.closeResource(conn, ps);
        }
        return 0;
    }
}
