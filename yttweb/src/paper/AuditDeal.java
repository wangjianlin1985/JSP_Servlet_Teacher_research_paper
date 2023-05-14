// 
// 
// 

package paper;

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

@WebServlet(description = "\u5ba1\u6838\u5904\u7406", urlPatterns = { "/AuditDeal" })
public class AuditDeal extends HttpServlet
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
        final JSONObject json = GetReader.receivePost(request);
        final int paperid = Integer.parseInt(json.getString("paperid"));
        final int auditorid = (int)request.getSession().getAttribute("id");
        final String time = json.getString("time");
        final String auditflag = json.getString("auditflag");
        final String views = json.getString("views");
        String sql = "insert into audit(paperid,auditorid,status,time,views) value(?,?,?,?,?)";
        PreparedStatement ps = db.getPs(sql);
        try {
            ps.setInt(1, paperid);
            ps.setInt(2, auditorid);
            ps.setString(3, auditflag);
            ps.setString(4, time);
            ps.setString(5, views);
            int row = ps.executeUpdate();
            if (row > 0) {
                sql = "update paper set auditflag = ? where id = ?";
                ps = db.getPs(sql);
                try {
                    ps.setString(1, auditflag);
                    ps.setInt(2, paperid);
                    row = ps.executeUpdate();
                    if (row > 0) {
                        out.print("1");
                    }
                    else {
                        out.print("2");
                    }
                    ps.close();
                    db.getConnect().close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else {
                out.print("2");
                ps.close();
                db.getConnect().close();
            }
        }
        catch (SQLException e2) {
            e2.printStackTrace();
        }
        out.flush();
        out.close();
    }
}
