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

@WebServlet(description = "\u67e5\u770b\u8bba\u6587\u8be6\u7ec6\u4fe1\u606f", urlPatterns = { "/PaperInfo" })
public class PaperInfo extends HttpServlet
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
        final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        String json = "";
        final int paperid = Integer.parseInt(request.getParameter("paperid"));
        final String sql = "select * from TeacherQueryPaper where id = " + paperid;
        final Db db = new Db();
        PreparedStatement ps = db.getPs(sql);
        final List<QueryPaper> paperList = new ArrayList<QueryPaper>();
        final ResJson resjson = new ResJson();
        resjson.setCode(0);
        resjson.setCount(0);
        resjson.setMsg("");
        try {
            ps = db.getPs(sql);
            ps = db.getPs(sql);
            final ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                final QueryPaper paper = new QueryPaper();
                paper.setPaperid(rs.getInt(1));
                paper.setTitle(rs.getString(2));
                paper.setFirstauthor(rs.getString(3));
                paper.setPubtime(rs.getDate(4));
                paper.setPubarea(rs.getString(5));
                paper.setIstrans(rs.getString(6));
                paper.setAuditflag(rs.getString(7));
                paper.setSourcename(rs.getString(8));
                paper.setPubpartname(rs.getString(9));
                paper.setSubpartname(rs.getString(10));
                paper.setJournalname(rs.getString(11));
                paper.setFirstsubname(rs.getString(12));
                paper.setTeacherid(rs.getInt(13));
                paper.setProsourceid(rs.getInt(14));
                paper.setFirstsubid(rs.getInt(15));
                paper.setSubtypeid(rs.getInt(16));
                paper.setJournalid(rs.getInt(17));
                paper.setPubtypeid(rs.getInt(18));
                paper.setMajorname(rs.getString(19));
                paper.setMentorflag(rs.getString(20));
                paper.setLayout(rs.getString(21));
                paper.setFileurl(rs.getString(22));
                paperList.add(paper);
            }
            resjson.setData(paperList);
            json = gson.toJson((Object)resjson);
            rs.close();
            ps.close();
            db.getConnect().close();
            out.print(json);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        out.flush();
        out.close();
    }
}
