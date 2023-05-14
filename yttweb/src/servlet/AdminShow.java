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

@WebServlet(description = "\u7ba1\u7406\u5458\u4fe1\u606f\u901a\u77e5", urlPatterns = { "/AdminShow" })
public class AdminShow extends HttpServlet
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
        final ArrayList<Integer> numlist = new ArrayList<Integer>();
        final ArrayList<String> nameList = new ArrayList<String>();
        final ArrayList<Object> list = new ArrayList<Object>();
        final String sql = "select majorid,majorname,count(*) numbers from adminshow where  auditflag = '未审核' GROUP BY majorId,majorName ";
        final PreparedStatement ps = db.getPs(sql);
        try {
            final ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                numlist.add(rs.getInt("numbers"));
                nameList.add(rs.getString("majorname"));
            }
            list.add(nameList);
            list.add(numlist);
            rs.close();
            ps.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        final JSONArray arry = JSONArray.fromObject((Object)list);
        out.print(arry);
        out.flush();
        out.close();
    }
}
