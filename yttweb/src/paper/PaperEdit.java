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

@WebServlet(description = "\u6559\u5e08\u4fee\u6539\u8bba\u6587", urlPatterns = { "/PaperEdit" })
public class PaperEdit extends HttpServlet
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
        final int paperid = Integer.parseInt(json.getString("id"));
        final String title = json.getString("title");
        final String firstauthor = json.getString("firstauthor");
        final String pubtime = json.getString("time");
        final int subpartid = json.getInt("subtypeid");
        final int firstsubid = json.getInt("firstsubid");
        final int pubpartid = json.getInt("pubtypeid");
        final int journalid = json.getInt("journalid");
        final int prosourceid = json.getInt("prosourceid");
        final String layout = json.getString("layout");
        final String pubarea = json.getString("pubarea");
        final String istrans = json.getString("istrans");
        final String auditflag = "\u672a\u5ba1\u6838";
        final String sql = "update paper set title = ?,firstauthor = ?,pubtime = ?,subtypeid = ?, firstsubid = ?,pubtypeid = ?,journalid = ? ,prosourceid= ?,layout= ?,pubarea = ?,istrans= ? ,auditflag = ? where id = ?";
        final PreparedStatement ps = db.getPs(sql);
        try {
            ps.setString(1, title);
            ps.setString(2, firstauthor);
            ps.setString(3, pubtime);
            ps.setInt(4, subpartid);
            ps.setInt(5, firstsubid);
            ps.setInt(6, pubpartid);
            ps.setInt(7, journalid);
            ps.setInt(8, prosourceid);
            ps.setString(9, layout);
            ps.setString(10, pubarea);
            ps.setString(11, istrans);
            ps.setString(12, auditflag);
            ps.setInt(13, paperid);
            final int row = ps.executeUpdate();
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
        out.flush();
        out.close();
    }
}
