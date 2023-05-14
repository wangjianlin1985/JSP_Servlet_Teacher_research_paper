// 
// 
// 

package servlet;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import com.google.gson.Gson;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import beans.ResJson;
import beans.TeacherQ;
import java.util.ArrayList;
import db.Db;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(description = "\u7ba1\u7406\u5458\u67e5\u770b\u6559\u5e08\u8be6\u7ec6\u4fe1\u606f", urlPatterns = { "/ATeacherInfo" })
public class ATeacherInfo extends HttpServlet
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
        final String sql = "select empnum,name,sex,majorname,titlename,birthday,telephone from teacher,major,title where empnum = ? and major.id = teacher.majorid and teacher.titleid = title.id";
        final Db db = new Db();
        final int tid = Integer.parseInt(request.getParameter("tid"));
        final PreparedStatement ps = db.getPs(sql);
        final List<TeacherQ> teacherList = new ArrayList<TeacherQ>();
        final ResJson resjson = new ResJson();
        resjson.setCode(0);
        resjson.setCount(0);
        resjson.setMsg("");
        try {
            ps.setInt(1, tid);
            final ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                final TeacherQ teacherQ = new TeacherQ();
                teacherQ.setEmpnum(rs.getInt(1));
                teacherQ.setName(rs.getString(2));
                teacherQ.setSex(rs.getString(3));
                teacherQ.setMajorname(rs.getString(4));
                teacherQ.setTitlename(rs.getString(5));
                teacherQ.setBirthday(rs.getDate(6));
                teacherQ.setTelephone(rs.getString(7));
                teacherList.add(teacherQ);
            }
            resjson.setData(teacherList);
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
