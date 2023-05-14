// 
// 
// 

package servlet;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.io.PrintWriter;
import net.sf.json.JSONArray;
import java.sql.SQLException;
import java.util.ArrayList;
import db.Db;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(description = "\u6559\u5e08\u67e5\u770b\u8bba\u6587\u7684\u5ba1\u6838\u60c5\u51b5", urlPatterns = { "/TeacherShow" })
public class TeacherShow extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
    
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        final PrintWriter out = response.getWriter();
        final int tid = (int)request.getSession().getAttribute("id");
        final Db db = new Db();
        final ArrayList<Integer> list = new ArrayList<Integer>();
        final int[] num = new int[10];
        String sql = "select count(*) numbers from paper where teacherid = ? ";
        final String[] str = { "\u5ba1\u6838\u901a\u8fc7", "\u672a\u901a\u8fc7", "\u672a\u5ba1\u6838" };
        PreparedStatement ps = db.getPs(sql);
        try {
            ps.setInt(1, tid);
            final ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                out.print("<script>alert('\u672a\u77e5\u9519\u8bef');window.history.go(-1);</script>");
                return;
            }
            list.add(rs.getInt("numbers"));
            rs.close();
            ps.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < str.length; ++i) {
            sql = "select count(*) numbers from paper where teacherid = ? and auditflag = ? ";
            ps = db.getPs(sql);
            try {
                ps.setInt(1, tid);
                ps.setString(2, str[i]);
                final ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    out.print("<script>alert('\u672a\u77e5\u9519\u8bef');window.history.go(-1);</script>");
                    return;
                }
                list.add(rs.getInt("numbers"));
                rs.close();
                ps.close();
            }
            catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        final JSONArray arry = JSONArray.fromObject((Object)list);
        out.print(arry);
        out.flush();
        out.close();
    }
}
