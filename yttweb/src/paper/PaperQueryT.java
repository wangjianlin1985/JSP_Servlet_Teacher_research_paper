// 
// 
// 

package paper;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import com.google.gson.Gson;
import java.io.PrintWriter;
import java.util.List;
import beans.ResJson;
import java.util.ArrayList;
import java.sql.SQLException;
import db.Db;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(description = "\u6559\u5e08\u67e5\u8be2\u8bba\u6587\u7684\u5904\u7406", urlPatterns = { "/PaperQueryT" })
public class PaperQueryT extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
    
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final String methodname = request.getParameter("methodname");
        if (methodname != null) {
            final String s;
            switch (s = methodname) {
                case "deletesingle": {
                    this.deleteSingle(request, response);
                    return;
                }
                default:
                    break;
            }
            this.queryList(request, response);
        }
        else {
            this.queryList(request, response);
        }
    }
    
    private void queryList(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        final PrintWriter out = response.getWriter();
        final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        String json = "";
        int numbers = 0;
        final Db db = new Db();
        final int tid = (int)request.getSession().getAttribute("id");
        final String title = request.getParameter("title");
        final String pubtime = request.getParameter("pubtime");
        final String firstauthor = request.getParameter("firstauthor");
        final String subtypeid = request.getParameter("subtypeid");
        final String firstsubid = request.getParameter("firstsubid");
        final String pubtypeid = request.getParameter("pubtypeid");
        final String prosourceid = request.getParameter("prosourceid");
        final String pubarea = request.getParameter("pubarea");
        final String journalid = request.getParameter("journalid");
        final String layout = request.getParameter("layout");
        final String auditflag = request.getParameter("auditflag");
        final int limit = Integer.parseInt(request.getParameter("limit"));
        final int page = Integer.parseInt(request.getParameter("page"));
        final int offset = limit * (page - 1);
        String sql = "";
        final String sqlf = "select * from TeacherQueryPaper where teacherid = " + tid;
        final String sqle = " limit " + offset + "," + limit;
        String sql2 = "select count(*) numbers from TeacherQueryPaper where teacherid = " + tid;
        String str = "";
        if (title != "" && title != null) {
            str = String.valueOf(str) + " and title like '%" + title + "%'";
        }
        if (firstauthor != "" && firstauthor != null) {
            str = String.valueOf(str) + " and firstauthor like '%" + firstauthor + "%'";
        }
        if (subtypeid != "" && subtypeid != null) {
            str = String.valueOf(str) + " and subtypeid like '%" + subtypeid + "%'";
        }
        if (firstsubid != "" && firstsubid != null) {
            str = String.valueOf(str) + " and firstsubid like '%" + firstsubid + "%'";
        }
        if (pubtypeid != "" && pubtypeid != null) {
            str = String.valueOf(str) + " and pubtypeid like '%" + pubtypeid + "%'";
        }
        if (prosourceid != "" && prosourceid != null) {
            str = String.valueOf(str) + " and prosourceid like '%" + prosourceid + "%'";
        }
        if (pubarea != "" && pubarea != null) {
            str = String.valueOf(str) + " and pubarea like '%" + pubarea + "%'";
        }
        if (pubarea != "" && pubarea != null) {
            str = String.valueOf(str) + " and pubarea like '%" + pubarea + "%'";
        }
        if (journalid != "" && journalid != null) {
            str = String.valueOf(str) + " and journalid like '%" + journalid + "%'";
        }
        if (layout != "" && layout != null) {
            str = String.valueOf(str) + " and layout like '%" + layout + "%'";
        }
        if (auditflag != "" && auditflag != null) {
            str = String.valueOf(str) + " and auditflag like '%" + auditflag + "%'";
        }
        if (pubtime != "" && pubtime != null) {
            final String[] aa = pubtime.split(" - ");
            str = String.valueOf(str) + " and (pubtime > '" + aa[0] + "' and pubtime < '" + aa[1] + " ')";
        }
        sql2 = String.valueOf(sql2) + str;
        sql = String.valueOf(sqlf) + str + sqle;
        try {
            final PreparedStatement ps = db.getPs(sql2);
            final ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                out.print("<script>alert('\u672a\u77e5\u9519\u8bef');window.history.go(-1);</script>");
                return;
            }
            numbers = rs.getInt("numbers");
            rs.close();
            ps.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        final List<QueryPaper> paperList = new ArrayList<QueryPaper>();
        final ResJson resjson = new ResJson();
        resjson.setCode(0);
        resjson.setCount(numbers);
        resjson.setMsg("");
        try {
            PreparedStatement ps2 = db.getPs(sql);
            ps2 = db.getPs(sql);
            final ResultSet rs2 = ps2.executeQuery();
            while (rs2.next()) {
                final QueryPaper paper = new QueryPaper();
                paper.setPaperid(rs2.getInt(1));
                paper.setTitle(rs2.getString(2));
                paper.setFirstauthor(rs2.getString(3));
                paper.setPubtime(rs2.getDate(4));
                paper.setPubarea(rs2.getString(5));
                paper.setIstrans(rs2.getString(6));
                paper.setAuditflag(rs2.getString(7));
                paper.setSourcename(rs2.getString(8));
                paper.setPubpartname(rs2.getString(9));
                paper.setSubpartname(rs2.getString(10));
                paper.setJournalname(rs2.getString(11));
                paper.setFirstsubname(rs2.getString(12));
                paper.setTeacherid(rs2.getInt(13));
                paper.setProsourceid(rs2.getInt(14));
                paper.setFirstsubid(rs2.getInt(15));
                paper.setSubtypeid(rs2.getInt(16));
                paper.setJournalid(rs2.getInt(17));
                paper.setPubtypeid(rs2.getInt(18));
                paper.setMajorname(rs2.getString(19));
                paper.setMentorflag(rs2.getString(20));
                paper.setLayout(rs2.getString(21));
                paper.setFileurl(rs2.getString(22));
                paperList.add(paper);
            }
            resjson.setData(paperList);
            json = gson.toJson((Object)resjson);
            rs2.close();
            ps2.close();
            db.getConnect().close();
            out.print(json);
        }
        catch (SQLException e2) {
            e2.printStackTrace();
        }
    }
    
    private void deleteSingle(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        final PrintWriter out = response.getWriter();
        final Db db = new Db();
        final String sql = "delete from paper where id = ?";
        final int paperid = Integer.parseInt(request.getParameter("paperid"));
        int row = 0;
        final PreparedStatement ps = db.getPs(sql);
        try {
            ps.setInt(1, paperid);
            row = ps.executeUpdate();
            if (row != 0) {
                out.print("1");
            }
            else {
                out.print("0");
            }
            ps.close();
            db.getConnect().close();
            out.flush();
            out.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
