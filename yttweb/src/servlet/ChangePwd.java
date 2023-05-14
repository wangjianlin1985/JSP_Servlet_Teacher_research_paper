// 
// 
// 

package servlet;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import net.sf.json.JSONObject;
import java.io.PrintWriter;
import java.sql.SQLException;
import db.GetReader;
import db.Db;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(description = "\u4fee\u6539\u5bc6\u7801", urlPatterns = { "/ChangePwd" })
public class ChangePwd extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
    
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        final PrintWriter out = response.getWriter();
        final Db db = new Db();
        String sql = "";
        final JSONObject json = GetReader.receivePost(request);
        final int account = Integer.parseInt(json.getString("account"));
        final String oldpwd = json.getString("oldpwd");
        final String pwd1 = json.getString("pwd1");
        final String pwd2 = json.getString("pwd2");
        final String access = (String)request.getSession().getAttribute("access");
        if (access.equals("1")) {
            sql = "select * from admin where account = ?";
            PreparedStatement ps = db.getPs(sql);
            try {
                ps.setInt(1, account);
                final ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    if (oldpwd.equals(rs.getString("password"))) {
                        if (pwd1.equals(pwd2)) {
                            sql = "update admin set password = ? where account = ?";
                            ps = db.getPs(sql);
                            ps.setString(1, pwd1);
                            ps.setInt(2, account);
                            final int row = ps.executeUpdate();
                            if (row > 0) {
                                out.print("2");
                            }
                            else {
                                out.print("3");
                            }
                            rs.close();
                            ps.close();
                            db.getConnect().close();
                        }
                        else {
                            out.print("4");
                        }
                    }
                    else {
                        out.print('1');
                    }
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            sql = "select * from teacher where empnum = ?";
            PreparedStatement ps = db.getPs(sql);
            try {
                ps.setInt(1, account);
                final ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    if (oldpwd.equals(rs.getString("password"))) {
                        if (pwd1.equals(pwd2)) {
                            sql = "update teacher set password = ? where empnum = ?";
                            ps = db.getPs(sql);
                            ps.setString(1, pwd1);
                            ps.setInt(2, account);
                            final int row = ps.executeUpdate();
                            if (row > 0) {
                                out.print("2");
                            }
                            else {
                                out.print("3");
                            }
                            rs.close();
                            ps.close();
                            db.getConnect().close();
                        }
                        else {
                            out.print("4");
                        }
                    }
                    else {
                        out.print('1');
                    }
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        out.flush();
        out.close();
    }
}
