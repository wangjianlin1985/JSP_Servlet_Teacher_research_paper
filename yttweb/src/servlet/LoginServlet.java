// 
// 
// 

package servlet;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONObject;
import java.io.PrintWriter;
import java.sql.SQLException;
import db.Db;
import db.GetReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet({ "/LoginServlet" })
public class LoginServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
    
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        final PrintWriter out = response.getWriter();
        final JSONObject json = GetReader.receivePost(request);
        final int username = json.getInt("username");
        final String password = json.getString("password");
        final String access = json.getString("access");
        final Db db = new Db();
        String sql = "";
        String resdata = "";
        final HttpSession session = request.getSession();
        if (access.equals("1")) {
            sql = "select * from admin where account= ?";
            final PreparedStatement ps = db.getPs(sql);
            try {
                ps.setInt(1, username);
                final ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    if (password.equals(rs.getString("password"))) {
                        resdata = "{\"success\": \"true\", \"msg\": \"1\"}";
                        session.setAttribute("username", (Object)rs.getString("name"));
                        session.setAttribute("access", (Object)access);
                        session.setAttribute("id", (Object)rs.getInt("account"));
                    }
                    else {
                        resdata = "{\"success\": \"false\", \"msg\": \"\u7ba1\u7406\u5458\u5bc6\u7801\u9519\u8bef\"}";
                    }
                }
                else {
                    resdata = "{\"success\": \"false\", \"msg\": \"\u4e0d\u5b58\u5728\u8be5\u7ba1\u7406\u5458\"}";
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            sql = "select * from teacher where empNum= ?";
            final PreparedStatement ps = db.getPs(sql);
            try {
                ps.setInt(1, username);
                final ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    if (password.equals(rs.getString("password"))) {
                        session.setAttribute("username", (Object)rs.getString("name"));
                        session.setAttribute("access", (Object)access);
                        session.setAttribute("id", (Object)rs.getInt("empnum"));
                        resdata = "{\"success\": \"true\", \"msg\": \"2\"}";
                    }
                    else {
                        resdata = "{\"success\": \"false\", \"msg\": \"\u6559\u5e08\u5bc6\u7801\u9519\u8bef\"}";
                    }
                }
                else {
                    resdata = "{\"success\": \"false\", \"msg\": \"\u4e0d\u5b58\u5728\u8be5\u6559\u5e08\"}";
                }
                rs.close();
                ps.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        out.print(resdata);
        out.flush();
        out.close();
    }
}
