// 
// 
// 

package pubpart;

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

@WebServlet(description = "\u671f\u520a\u7c7b\u578b\u6dfb\u52a0", urlPatterns = { "/PubAdd" })
public class PubAdd extends HttpServlet
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
        final int id = Integer.parseInt(json.getString("id"));
        final String pubpartname = json.getString("pubpartname");
        final int grade = json.getInt("grade");
        String sql = "select * from pubpart where id = ?";
        PreparedStatement ps = db.getPs(sql);
        try {
            ps.setInt(1, id);
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                out.print(1);
                rs.close();
                ps.close();
                db.getConnect().close();
            }
            else {
                sql = "insert into pubpart values(?,?,?)";
                ps = db.getPs(sql);
                ps.setInt(1, id);
                ps.setString(2, pubpartname);
                ps.setInt(3, grade);
                final int row = ps.executeUpdate();
                if (row > 0) {
                    out.print(2);
                }
                else {
                    out.print(3);
                }
                ps.close();
                db.getConnect().close();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        out.flush();
        out.close();
    }
}
