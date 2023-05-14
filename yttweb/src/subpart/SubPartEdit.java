// 
// 
// 

package subpart;

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

@WebServlet(description = "\u5b66\u79d1\u95e8\u7c7b\u4fe1\u606f\u7f16\u8f91", urlPatterns = { "/SubPartEdit" })
public class SubPartEdit extends HttpServlet
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
        final int oldid = Integer.parseInt(json.getString("oldid"));
        final int id = Integer.parseInt(json.getString("id"));
        final String subpartname = json.getString("subpartname");
        Label_0362: {
            if (id != oldid) {
                String sql = "select * from subpart where id= ?";
                PreparedStatement ps = db.getPs(sql);
                try {
                    ps.setInt(1, id);
                    final ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        out.print("1");
                        rs.close();
                        break Label_0362;
                    }
                    rs.close();
                    sql = "update subpart set id = ?,subpartname = ? where id = ?";
                    ps = db.getPs(sql);
                    try {
                        ps.setInt(1, id);
                        ps.setString(2, subpartname);
                        ps.setInt(3, oldid);
                        final int row = ps.executeUpdate();
                        if (row > 0) {
                            out.print("");
                        }
                        else {
                            out.print("3");
                        }
                        ps.close();
                        db.getConnect().close();
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break Label_0362;
                }
                catch (SQLException e2) {
                    e2.printStackTrace();
                    break Label_0362;
                }
            }
            String sql = "update subpart set id = ?,subpartname = ? where id = ?";
            PreparedStatement ps = db.getPs(sql);
            try {
                ps.setInt(1, id);
                ps.setString(2, subpartname);
                ps.setInt(3, oldid);
                final int row2 = ps.executeUpdate();
                if (row2 > 0) {
                    out.print("2");
                }
                else {
                    out.print("3");
                }
                ps.close();
                db.getConnect().close();
            }
            catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        out.flush();
        out.close();
    }
}
