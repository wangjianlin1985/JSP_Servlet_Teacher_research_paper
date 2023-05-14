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

@WebServlet(description = "\u7ed8\u5236\u4e13\u4e1a\u6309\u671f\u520a\u7b49\u7ea7\u53d1\u8868\u8bba\u6587\u6570", urlPatterns = { "/PubChart" })
public class PubChart extends HttpServlet
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
        final ArrayList<String> nameList = new ArrayList<String>();
        final ArrayList<Object> list = new ArrayList<Object>();
        final ArrayList<Integer> time = new ArrayList<Integer>();
        final ArrayList<Integer> timetemp = new ArrayList<Integer>();
        final ArrayList<Integer> majorid = new ArrayList<Integer>();
        final ArrayList<Integer> pubid = new ArrayList<Integer>();
        final ArrayList<String> pubname = new ArrayList<String>();
        String pagetime = null;
        String pubpart = null;
        final String sqlp = "select DISTINCT(pubtypeid),pubpartname from pubechart ";
        pagetime = request.getParameter("pagetime");
        pubpart = request.getParameter("pubpart");
        System.out.println(pubpart);
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
        if (pubpart == "" || pubpart == null) {
            final PreparedStatement ps = db.getPs(sqlp);
            try {
                final ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    pubid.add(rs.getInt(1));
                    pubname.add(rs.getString(2));
                }
                rs.close();
                ps.close();
                list.add(pubname);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            final String[] aa = pubpart.split(",");
            for (int k = 0; k < aa.length; ++k) {
                final int temp = Integer.valueOf(aa[k]);
                pubid.add(temp);
            }
            final String sqlp2 = "select id,pubpartname from pubpart where id = ?";
            for (int i = 0; i < pubid.size(); ++i) {
                final PreparedStatement ps = db.getPs(sqlp2);
                try {
                    ps.setInt(1, pubid.get(i));
                    final ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        pubname.add(rs.getString(2));
                    }
                }
                catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
            list.add(pubname);
        }
        String sql = "select majorid,majorname from majorechart where  auditflag = '\u5ba1\u6838\u901a\u8fc7'  GROUP BY majorId,majorName ";
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
        catch (SQLException e3) {
            e3.printStackTrace();
        }
        sql = "select majorid,majorname from pubechart where  auditflag = '\u5ba1\u6838\u901a\u8fc7' and majorid = ? GROUP BY majorId,majorName ";
        for (int l = 0; l < nameList.size(); ++l) {
            ps = db.getPs(sql);
            try {
                ps.setInt(1, majorid.get(l));
                final ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    int tempm = 0;
                    final ArrayList<Integer> tempList1 = new ArrayList<Integer>();
                    for (int h = 0; h < pubid.size(); ++h) {
                        String sql2 = "select majorid,majorname,count(*) numbers from pubechart where  auditflag = '\u5ba1\u6838\u901a\u8fc7' and majorid = ? ";
                        final String sqlb = "  and time = ?  ";
                        tempm = 0;
                        final String sqlc = "and pubtypeid = ? ";
                        sql2 = String.valueOf(sql2) + sqlb + sqlc + sqle;
                        final PreparedStatement ps2 = db.getPs(sql2);
                        final ArrayList<Integer> tempList2 = new ArrayList<Integer>();
                        for (int m = 0; m < time.size(); ++m) {
                            ps2.setInt(1, majorid.get(l));
                            ps2.setInt(2, time.get(m));
                            ps2.setInt(3, pubid.get(h));
                            final ResultSet rs2 = ps2.executeQuery();
                            if (rs2.next()) {
                                tempList2.add(rs2.getInt("numbers"));
                            }
                            else {
                                tempList2.add(0);
                            }
                        }
                        for (int p = 0; p < tempList2.size(); ++p) {
                            tempm += tempList2.get(p);
                        }
                        tempList1.add(tempm);
                    }
                    list.add(tempList1);
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
