// 
// 
// 

package projectsource;

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

@WebServlet(description = "\u6dfb\u52a0\u9879\u76ee\u6765\u6e90", urlPatterns = { "/SourceAdd" })
public class SourceAdd extends HttpServlet
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
        final String sourcename = json.getString("sourcename");
        String sql = "select * from projectsource where id = ?";
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
                sql = "insert into projectsource values(?,?)";
                ps = db.getPs(sql);
                ps.setInt(1, id);
                ps.setString(2, sourcename);
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
