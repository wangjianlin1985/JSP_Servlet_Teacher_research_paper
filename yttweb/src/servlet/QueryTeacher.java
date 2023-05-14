// 
// 
// 

package servlet;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import com.google.gson.Gson;
import java.io.PrintWriter;
import java.util.List;
import beans.ResJson;
import beans.TeacherQ;
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

@WebServlet(description = "\u67e5\u8be2\u6559\u5e08", urlPatterns = { "/QueryTeacher" })
public class QueryTeacher extends HttpServlet
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
                case "deletemulti": {
                    this.deleteMulti(request, response);
                    return;
                }
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
        final String empnum = request.getParameter("empnum");
        final String name = request.getParameter("name");
        final String majorname = request.getParameter("majorid");
        final String titlename = request.getParameter("titleid");
        final int limit = Integer.parseInt(request.getParameter("limit"));
        final int page = Integer.parseInt(request.getParameter("page"));
        final int offset = limit * (page - 1);
        String sql = "";
        final String sqlf = "select empnum,name,sex,majorname,titlename,birthday,telephone from teacher,major,title where (teacher.majorid = major.id AND teacher.titleid = title.id)";
        final String sqle = "order by empnum limit " + offset + "," + limit;
        String sql2 = "select count(*) numbers from teacher,major,title where (teacher.majorid = major.id AND teacher.titleid = title.id)";
        String str = "";
        if (empnum != "" && empnum != null) {
            str = String.valueOf(str) + "and empnum like '%" + empnum + "%'";
        }
        if (name != "" && name != null) {
            str = String.valueOf(str) + "and name like '%" + name + "%'";
        }
        if (majorname != "" && majorname != null) {
            str = String.valueOf(str) + "and majorid like '%" + majorname + "%'";
        }
        if (titlename != "" && titlename != null) {
            str = String.valueOf(str) + "and titleid like '%" + titlename + "%'";
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
        final List<TeacherQ> teacherList = new ArrayList<TeacherQ>();
        final ResJson resjson = new ResJson();
        resjson.setCode(0);
        resjson.setCount(numbers);
        resjson.setMsg("");
        try {
            PreparedStatement ps2 = db.getPs(sql);
            ps2 = db.getPs(sql);
            final ResultSet rs2 = ps2.executeQuery();
            while (rs2.next()) {
                final TeacherQ teacherQ = new TeacherQ();
                teacherQ.setEmpnum(rs2.getInt(1));
                teacherQ.setName(rs2.getString(2));
                teacherQ.setSex(rs2.getString(3));
                teacherQ.setMajorname(rs2.getString(4));
                teacherQ.setTitlename(rs2.getString(5));
                teacherQ.setBirthday(rs2.getDate(6));
                teacherQ.setTelephone(rs2.getString(7));
                teacherList.add(teacherQ);
            }
            resjson.setData(teacherList);
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
        final String sql = "delete from teacher where empnum = ?";
        final int empnum = Integer.parseInt(request.getParameter("empnum"));
        final String sql2 = "select * from paper where teacherid = ?";
        PreparedStatement ps = db.getPs(sql2);
        try {
            ps.setInt(1, empnum);
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                out.print("404");
            }
            else {
                int row = 0;
                ps = db.getPs(sql);
                ps.setInt(1, empnum);
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
        }
        catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
    
    private void deleteMulti(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        final PrintWriter out = response.getWriter();
        final Db db = new Db();
        final String num = request.getParameter("empnums");
        final String[] a = num.split(";");
        final int[] arr = new int[a.length];
        for (int i = 0; i < a.length; ++i) {
            arr[i] = Integer.parseInt(a[i]);
        }
        final String sql = "delete from teacher where empnum = ?";
        final PreparedStatement ps = db.getPs(sql);
        for (int j = 0; j < arr.length; ++j) {
            try {
                ps.setInt(1, arr[j]);
                ps.addBatch();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            final int[] rows = ps.executeBatch();
            final int row = rows.length;
            ps.close();
            db.getConnect().close();
            if (row == arr.length) {
                out.print("1");
            }
            else {
                out.print("0");
            }
            out.flush();
            out.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
