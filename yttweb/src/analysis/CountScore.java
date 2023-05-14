// 
// 
// 

package analysis;

import java.util.List;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import com.google.gson.Gson;
import java.io.PrintWriter;
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

@WebServlet(description = "\u7edf\u8ba1\u6559\u5e08\u8bba\u6587\u5f97\u5206", urlPatterns = { "/CountScore" })
public class CountScore extends HttpServlet
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
        int numbers = 0;
        final Db db = new Db();
        final String major = request.getParameter("major");
        final String pubtime = request.getParameter("pubtime");
        final int limit = Integer.parseInt(request.getParameter("limit"));
        final int page = Integer.parseInt(request.getParameter("page"));
        final int offset = limit * (page - 1);
        String sql = "";
        final String sqlf = "select * from countgrade where 1=1  ";
        final String sqle = " limit " + offset + "," + limit;
        String sql2 = "select count(*) numbers from countgrade where 1=1  ";
        String str = "";
        if (major != "" && major != null) {
            str = String.valueOf(str) + " and  id = " + major;
        }
        if (pubtime != "" && pubtime != null) {
            final String[] aa = pubtime.split(" - ");
            if (aa[0].equals(aa[1])) {
                str = String.valueOf(str) + " and (pubtime >= '" + aa[0] + "' and pubtime <= '" + aa[1] + " ')";
            }
            else {
                str = String.valueOf(str) + " and (pubtime >='" + aa[0] + "' and pubtime < '" + aa[1] + " ')";
            }
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
        final List<Score> ScoreList = new ArrayList<Score>();
        final ScoreJson scorejson = new ScoreJson();
        scorejson.setCode(0);
        scorejson.setCount(numbers);
        scorejson.setMsg("");
        int grade1 = 0;
        int grade2 = 0;
        int grade3 = 0;
        int num1 = 0;
        int num2 = 0;
        int num3 = 0;
        try {
            final PreparedStatement ps2 = db.getPs(sql);
            final ResultSet rs2 = ps2.executeQuery();
            while (rs2.next()) {
                final Score score = new Score();
                score.setTeacherid(rs2.getInt(1));
                score.setName(rs2.getString(2));
                score.setMajorname(rs2.getString(3));
                score.setPubtime(rs2.getInt(4));
                score.setTopnum(rs2.getInt(6));
                score.setOnenum(rs2.getInt(8));
                score.setTwonum(rs2.getInt(10));
                num1 = rs2.getInt(6);
                num2 = rs2.getInt(8);
                num3 = rs2.getInt(10);
                final int p1 = rs2.getInt("partid1");
                final int p2 = rs2.getInt("partid2");
                final int p3 = rs2.getInt("partid3");
                final String sqlp = "select grade from pubpart where id = ?";
                PreparedStatement ps3 = db.getPs(sqlp);
                ps3.setInt(1, p1);
                ResultSet rs3 = ps3.executeQuery();
                if (rs3.next()) {
                    grade1 = rs3.getInt(1);
                    rs3.close();
                    ps3.close();
                }
                ps3 = db.getPs(sqlp);
                ps3.setInt(1, p2);
                rs3 = ps3.executeQuery();
                if (rs3.next()) {
                    grade2 = rs3.getInt(1);
                    rs3.close();
                    ps3.close();
                }
                ps3 = db.getPs(sqlp);
                ps3.setInt(1, p3);
                rs3 = ps3.executeQuery();
                if (rs3.next()) {
                    grade3 = rs3.getInt(1);
                    rs3.close();
                    ps3.close();
                }
                grade1 *= num1;
                grade2 *= num2;
                grade3 *= num3;
                final int lastgrade = grade1 + grade2 + grade3;
                score.setTopgrade(grade1);
                score.setOnegrade(grade2);
                score.setTwograde(grade3);
                score.setLastgrade(lastgrade);
                ScoreList.add(score);
            }
            scorejson.setData(ScoreList);
            json = gson.toJson((Object)scorejson);
            rs2.close();
            ps2.close();
            db.getConnect().close();
            out.print(json);
            out.flush();
            out.close();
        }
        catch (SQLException e2) {
            e2.printStackTrace();
        }
    }
}
