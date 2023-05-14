// 
// 
// 

package paper;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import com.google.gson.Gson;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import beans.ResJson;
import java.util.ArrayList;
import db.Db;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(description = "\u67e5\u8be2\u5ba1\u6838\u8be6\u60c5\u7684\u5904\u7406", urlPatterns = { "/AuditQuery" })
public class AuditQuery extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
    
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        final PrintWriter out = response.getWriter();
        final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        String json = "";
        final int numbers = 0;
        final Db db = new Db();
        final String id = request.getParameter("id");
        final String sql = "select * from auditquery where paperid = ? ";
        final List<AuditBean> AuditList = new ArrayList<AuditBean>();
        final ResJson resjson = new ResJson();
        resjson.setCode(0);
        resjson.setCount(numbers);
        resjson.setMsg("");
        try {
            final PreparedStatement ps = db.getPs(sql);
            ps.setString(1, id);
            final ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                final AuditBean auditBean = new AuditBean();
                auditBean.setTime(rs.getTimestamp(3));
                auditBean.setAuditor(rs.getString(5));
                auditBean.setAuditorid(rs.getInt(1));
                auditBean.setStatus(rs.getString(2));
                auditBean.setViews(rs.getString(4));
                AuditList.add(auditBean);
            }
            resjson.setData(AuditList);
            json = gson.toJson((Object)resjson);
            rs.close();
            ps.close();
            db.getConnect().close();
            out.print(json);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
