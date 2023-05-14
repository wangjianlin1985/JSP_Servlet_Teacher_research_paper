// 
// 
// 

package journal;

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

@WebServlet(description = "\u6dfb\u52a0\u671f\u520a", urlPatterns = { "/JourAdd" })
public class JourAdd extends HttpServlet
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
        final int id = json.getInt("id");
        final String journalname = json.getString("journalname");
        final int partid = json.getInt("partid");
        String sql = "select * from journal where id = ?";
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
                sql = "insert into journal values(?,?,?)";
                ps = db.getPs(sql);
                ps.setInt(1, id);
                ps.setString(2, journalname);
                ps.setInt(3, partid);
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
