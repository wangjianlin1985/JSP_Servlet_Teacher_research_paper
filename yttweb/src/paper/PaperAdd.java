// 
// 
// 

package paper;

import java.sql.PreparedStatement;
import net.sf.json.JSONObject;
import com.google.gson.Gson;
import java.io.PrintWriter;
import java.sql.SQLException;
import db.Db;
import db.GetReader;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(description = "\u6559\u5e08\u6dfb\u52a0\u8bba\u6587", urlPatterns = { "/PaperAdd" })
public class PaperAdd extends HttpServlet
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
        PaperBean paper = new PaperBean();
        final JSONObject json = GetReader.receivePost(request);
        paper = (PaperBean)gson.fromJson(json.toString(), (Class)PaperBean.class);
        final String teachername = request.getSession().getAttribute("username").toString();
        final int tid = (int)request.getSession().getAttribute("id");
        final String auditflag = "\u672a\u5ba1\u6838";
        String mentorflag;
        if (teachername.equals(paper.getFirstauthor())) {
            mentorflag = "\u5426";
        }
        else {
            mentorflag = "\u662f";
        }
        String res = "";
        final Db db = new Db();
        PreparedStatement ps = null;
        String sql = "";
        sql = "insert into paper(title,firstauthor,pubtime,pubtypeid,journalid,subtypeid,firstsubid,prosourceid,teacherid,pubarea,istrans,layout,fileurl,mentorflag,auditflag) value(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
        ps = db.getPs(sql);
        try {
            ps.setString(1, paper.getTitle());
            ps.setString(2, paper.getFirstauthor());
            ps.setDate(3, paper.getPubtime());
            ps.setInt(4, paper.getPubtypeid());
            ps.setInt(5, paper.getJournalid());
            ps.setInt(6, paper.getSubtypeid());
            ps.setInt(7, paper.getFirstsubid());
            ps.setInt(8, paper.getProsourceid());
            ps.setInt(9, tid);
            ps.setString(10, paper.getPubarea());
            ps.setString(11, paper.getIstrans());
            ps.setString(12, paper.getLayout());
            ps.setString(13, paper.getFileurl());
            ps.setString(14, mentorflag);
            ps.setString(15, auditflag);
            final int row = ps.executeUpdate();
            if (row > 0) {
                res = "1";
            }
            else {
                res = "2";
            }
            ps.close();
            db.getConnect().close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        out.print(res);
        out.flush();
        out.close();
    }
}
