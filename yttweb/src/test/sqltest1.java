// 
// 
// 

package test;

import java.io.IOException;
import javax.servlet.ServletException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.io.PrintWriter;
import java.sql.SQLException;
import db.Db;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet({ "/sqltest1" })
public class sqltest1 extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        final PrintWriter out = response.getWriter();
        final String json = "";
        final int numbers = 0;
        final Db db = new Db();
        final String sql = "select * from teacher where name = '\u738b\u7ea2'";
        final PreparedStatement ps = db.getPs(sql);
        try {
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println(rs.getString("name"));
            }
            else {
                System.out.println("\u6ca1\u6709\u67e5\u5230\uff01");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
