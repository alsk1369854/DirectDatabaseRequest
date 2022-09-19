package servlets;

import Util.JDBCUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

@WebServlet("/Query")
public class QueryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,PUT,DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        String sql = request.getParameter("sql");
        //        sql = "SELECT id, name, email, birth FROM customers";
        if(sql == null || "".equals(sql)){
            response.getWriter().write("[]");
        }else {
            String queryValue = query(sql);
            System.out.println(queryValue);
            response.getWriter().write(queryValue);
        }
    }

    public String query(String sql, Object ...args){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);

            for(int i=0; i<args.length;i++){
                ps.setObject(i+1, args[i]);

            }

            rs = ps.executeQuery();
            // 獲取結果集的元數據: ResultSetMetaData
            ResultSetMetaData rsmd = rs.getMetaData();
            // 通過ResultSetMetaData獲取結果集的列數
            int columnCount = rsmd.getColumnCount();

            StringBuilder queryValue = new StringBuilder();
            queryValue.append('[');
            // 是否有查到東西
            while(rs.next()){
                queryValue.append('{');
                // 處理結果集一行數據中的每一個列
                for(int i=0; i<columnCount; i++){
                    // 獲取列值
                    Object columnValue = rs.getObject(i + 1);
                    // 獲取列別名(別名必須與java類屬性名一致)
                    String columnName = rsmd.getColumnLabel(i + 1);
                    // 通過反射: 給對象指定的columnName屬性，賦值為columnValue
//                    System.out.println(columnValue);

                    if(columnValue instanceof Integer){
                        queryValue.append("\"" + columnName + "\":" + columnValue);
                    }else{
                        queryValue.append("\"" + columnName + "\":\"" + columnValue + "\"");
                    }
                    if(i != columnCount-1) queryValue.append(',');
                }
                queryValue.append("},");
            }

            int temp = queryValue.lastIndexOf(",");
            if(temp != -1){
                queryValue = new StringBuilder(queryValue.substring(0,temp));
            }
            queryValue.append(']');

            return queryValue.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 關閉資源
            JDBCUtils.closeResource(conn,ps,rs);
        }
        return null;
    }
}
