// 
// 
// 

package servlet;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.PrintWriter;
import java.sql.SQLException;
import db.Db;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(description = "\u67e5\u8be2\u6559\u5e08", urlPatterns = { "/IdVerify" })
public class IdVerify extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
    
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        final String id = request.getParameter("id");
        final PrintWriter out = response.getWriter();
        String res = "";
        final Db db = new Db();
        String sql = "";
        ResultSet rs = null;
        PreparedStatement ps = null;
        if (id != "" && id != null) {
            final long Tid = Long.parseLong(id);
            sql = "select * from teacher where empnum = ?";
            ps = db.getPs(sql);
            try {
                ps.setLong(1, Tid);
                rs = ps.executeQuery();
                if (rs.next()) {
                    res = "1";
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            out.print(res);
            out.flush();
            out.close();
        }
    }
}
