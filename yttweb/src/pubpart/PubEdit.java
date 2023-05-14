// 
// 
// 

package pubpart;

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

@WebServlet(description = "\u4fee\u6539\u671f\u520a\u7b49\u7ea7\u5bf9\u5e94\u5206\u6570", urlPatterns = { "/PubEdit" })
public class PubEdit extends HttpServlet
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
        System.out.println(id);
        final int grade = json.getInt("grade");
        final String sql = "update pubpart set grade = ? where id = ?";
        final PreparedStatement ps = db.getPs(sql);
        try {
            ps.setInt(1, grade);
            ps.setInt(2, id);
            final int row = ps.executeUpdate();
            if (row > 0) {
                out.print("1");
            }
            else {
                out.print("2");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        out.flush();
        out.close();
    }
}
