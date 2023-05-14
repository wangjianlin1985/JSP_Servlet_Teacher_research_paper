// 
// 
// 

package analysis;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.io.PrintWriter;
import net.sf.json.JSONArray;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.ArrayList;
import db.Db;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(description = "\u6d4b\u8bd5\u4e13\u4e1a\u8bba\u6587\u53d1\u8868\u60c5\u51b5", urlPatterns = { "/EchartTest" })
public class EchartTest extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
    
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        final PrintWriter out = response.getWriter();
        final Db db = new Db();
        final ArrayList<Integer> numlist = new ArrayList<Integer>();
        final ArrayList<String> nameList = new ArrayList<String>();
        final ArrayList<Object> list = new ArrayList<Object>();
        final ArrayList<Integer> time = new ArrayList<Integer>();
        final ArrayList<Integer> timetemp = new ArrayList<Integer>();
        final ArrayList<Integer> majorid = new ArrayList<Integer>();
        String pagetime = null;
        pagetime = request.getParameter("pagetime");
        if (pagetime == "" || pagetime == null) {
            Calendar cale = null;
            cale = Calendar.getInstance();
            int year = cale.get(1);
            ++year;
            for (int i = 0; i < 5; ++i) {
                --year;
                timetemp.add(year);
            }
            for (int n = timetemp.size(); n > 0; --n) {
                time.add(timetemp.get(n - 1));
            }
            list.add(time);
        }
        else {
            final String[] aa = pagetime.split(" - ");
            final int num1 = Integer.valueOf(aa[0]);
            final int num2 = Integer.valueOf(aa[1]);
            int yeartemp = num2 + 1;
            for (int len = num2 - num1 + 1, j = 0; j < len; ++j) {
                --yeartemp;
                timetemp.add(yeartemp);
            }
            for (int n2 = timetemp.size(); n2 > 0; --n2) {
                time.add(timetemp.get(n2 - 1));
            }
            list.add(time);
        }
        String sql = "select majorid,majorname,count(*) numbers from majorechart where  auditflag = '\u5ba1\u6838\u901a\u8fc7'  GROUP BY majorId,majorName ";
        final String sqle = "GROUP BY majorId,majorName ";
        PreparedStatement ps = db.getPs(sql);
        try {
            final ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                nameList.add(rs.getString(2));
                majorid.add(rs.getInt(1));
            }
            list.add(nameList);
            rs.close();
            ps.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        sql = "select majorid,majorname,count(*) numbers from majorechart where  auditflag = '\u5ba1\u6838\u901a\u8fc7' and majorid = ? GROUP BY majorId,majorName ";
        for (int k = 0; k < nameList.size(); ++k) {
            ps = db.getPs(sql);
            try {
                ps.setInt(1, majorid.get(k));
                final ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String sql2 = "select majorid,majorname,count(*) numbers from majorechart where  auditflag = '\u5ba1\u6838\u901a\u8fc7' and majorid = ? ";
                    final String sqlb = "  and time = ?  ";
                    sql2 = String.valueOf(sql2) + sqlb + sqle;
                    final PreparedStatement ps2 = db.getPs(sql2);
                    final ArrayList<Integer> tempList = new ArrayList<Integer>();
                    for (int m = 0; m < time.size(); ++m) {
                        ps2.setInt(1, majorid.get(k));
                        ps2.setInt(2, time.get(m));
                        final ResultSet rs2 = ps2.executeQuery();
                        if (rs2.next()) {
                            tempList.add(rs2.getInt("numbers"));
                        }
                        else {
                            tempList.add(0);
                        }
                    }
                    list.add(tempList);
                }
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
