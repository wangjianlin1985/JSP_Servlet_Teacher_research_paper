// 
// 
// 

package selects;

import java.util.List;
import beans.ItemSelJson;
import com.google.gson.Gson;
import beans.Item;
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

@WebServlet(description = "\u53d1\u8868\u520a\u7269/\u8bba\u6587\u96c6\u4e0b\u62c9\u6846\u52a8\u6001\u52a0\u8f7d", urlPatterns = { "/Journal" })
public class Journal extends HttpServlet
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
                case "load": {
                    this.Load(request, response);
                    return;
                }
                case "findvalue": {
                    this.FindValue(request, response);
                    return;
                }
                default:
                    break;
            }
            this.Load(request, response);
        }
        else {
            this.Load(request, response);
        }
    }
    
    private void FindValue(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        final PrintWriter out = response.getWriter();
        final Db db = new Db();
        int id = 0;
        final String name = request.getParameter("firstsubname");
        final String sql = "select * from projectsource where firstsubname = ?";
        final PreparedStatement ps = db.getPs(sql);
        try {
            ps.setString(1, name);
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
                rs.close();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        final String string1 = "{\"id\":" + id + "}";
        final JSONObject json = JSONObject.fromObject((Object)string1.toString());
        out.print(json);
        out.flush();
        out.close();
    }
    
    private void Load(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        final PrintWriter out = response.getWriter();
        final int pubpartid = Integer.parseInt(request.getParameter("pubpartid"));
        String sql = "";
        final List<Item> ListM = new ArrayList<Item>();
        final Gson gson = new Gson();
        final ItemSelJson seljson = new ItemSelJson();
        String json = "";
        final Db db = new Db();
        ResultSet rs = null;
        if (pubpartid == 1) {
            sql = "select * from journal";
            final PreparedStatement ps = db.getPs(sql);
            try {
                rs = ps.executeQuery();
                while (rs.next()) {
                    final Item item = new Item();
                    item.setId(rs.getInt(1));
                    item.setName(rs.getString(2));
                    ListM.add(item);
                }
                rs.close();
                ps.close();
                db.getConnect().close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            sql = "select * from journal where pubpartid = ?";
            final PreparedStatement ps = db.getPs(sql);
            try {
                ps.setInt(1, pubpartid);
                rs = ps.executeQuery();
                while (rs.next()) {
                    final Item item = new Item();
                    item.setId(rs.getInt(1));
                    item.setName(rs.getString(2));
                    ListM.add(item);
                }
                rs.close();
                ps.close();
                db.getConnect().close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        seljson.setItem(ListM);
        json = gson.toJson((Object)seljson);
        out.print(json);
        out.flush();
        out.close();
    }
}
