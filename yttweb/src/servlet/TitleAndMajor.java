// 
// 
// 

package servlet;

import java.util.List;
import beans.TmSelcet;
import com.google.gson.Gson;
import beans.Title;
import beans.Major;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.io.PrintWriter;
import net.sf.json.JSONObject;
import java.sql.SQLException;
import db.Db;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet({ "/TitleAndMajor" })
public class TitleAndMajor extends HttpServlet
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
                case "tmload": {
                    this.TmLoad(request, response);
                    return;
                }
                case "findvalue": {
                    this.FindValue(request, response);
                    return;
                }
                default:
                    break;
            }
            this.TmLoad(request, response);
        }
        else {
            this.TmLoad(request, response);
        }
    }
    
    private void FindValue(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        final PrintWriter out = response.getWriter();
        final Db db = new Db();
        int majorid = 0;
        int titleid = 0;
        final String majorname = request.getParameter("majorname");
        final String titlename = request.getParameter("titlename");
        final String sql = "select * from major where majorname = ?";
        final String sql2 = "select * from title where titlename = ?";
        PreparedStatement ps = db.getPs(sql);
        try {
            ps.setString(1, majorname);
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                majorid = rs.getInt("id");
                rs.close();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        ps = db.getPs(sql2);
        try {
            ps.setString(1, titlename);
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                titleid = rs.getInt("id");
                rs.close();
            }
            ps.close();
            db.getConnect().close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        final String string1 = "{\"majorid\":" + majorid + ", \"titleid\": " + titleid + "}";
        final JSONObject json = JSONObject.fromObject((Object)string1.toString());
        out.print(json);
        out.flush();
        out.close();
    }
    
    private void TmLoad(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        final PrintWriter out = response.getWriter();
        String json = "";
        final Db db = new Db();
        final List<Major> ListM = new ArrayList<Major>();
        final List<Title> ListT = new ArrayList<Title>();
        final Gson gson = new Gson();
        final TmSelcet tmSelcet = new TmSelcet();
        String sql = "";
        ResultSet rs = null;
        sql = "select * from major";
        PreparedStatement ps = db.getPs(sql);
        try {
            rs = ps.executeQuery();
            while (rs.next()) {
                final Major major = new Major();
                major.setId(rs.getInt(1));
                major.setMajorName(rs.getString(2));
                ListM.add(major);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        sql = "select * from title";
        ps = db.getPs(sql);
        try {
            rs = ps.executeQuery();
            while (rs.next()) {
                final Title title = new Title();
                title.setId(rs.getInt(1));
                title.setTitleName(rs.getString(2));
                ListT.add(title);
            }
            rs.close();
            ps.close();
            db.getConnect().close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        tmSelcet.setMajor(ListM);
        tmSelcet.setTitle(ListT);
        json = gson.toJson((Object)tmSelcet);
        out.print(json);
        out.flush();
        out.close();
    }
}
