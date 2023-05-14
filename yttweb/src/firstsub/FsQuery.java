// 
// 
// 

package firstsub;

import java.util.List;
import beans.ResJson;
import beans.ItemB;
import java.util.ArrayList;
import com.google.gson.Gson;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.io.PrintWriter;
import java.sql.SQLException;
import db.Db;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(description = "\u4e00\u7ea7\u5b66\u79d1\u67e5\u8be2", urlPatterns = { "/FsQuery" })
public class FsQuery extends HttpServlet
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
                    this.deletesingle(request, response);
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
    
    private void deletesingle(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        final PrintWriter out = response.getWriter();
        final Db db = new Db();
        final String sql = "delete from firstsub where id = ?";
        final int id = Integer.parseInt(request.getParameter("id"));
        final String sql2 = "select * from paper where firstsubid = ?";
        PreparedStatement ps = db.getPs(sql2);
        try {
            ps.setInt(1, id);
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                out.print("404");
            }
            else {
                int row = 0;
                ps = db.getPs(sql);
                ps.setInt(1, id);
                row = ps.executeUpdate();
                if (row != 0) {
                    out.print("1");
                }
                else {
                    out.print("0");
                }
                ps.close();
                db.getConnect().close();
            }
            out.flush();
            out.close();
        }
        catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
    
    private void queryList(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        final PrintWriter out = response.getWriter();
        final Gson gson = new Gson();
        String json = "";
        int numbers = 0;
        final Db db = new Db();
        final String id = request.getParameter("id");
        final String name = request.getParameter("firstsubname");
        final String partid = request.getParameter("partid");
        final int limit = Integer.parseInt(request.getParameter("limit"));
        final int page = Integer.parseInt(request.getParameter("page"));
        final int offset = limit * (page - 1);
        String sql = "";
        final String sqlf = "select firstsub.id,firstsubname,subpartname from firstsub,subpart where (firstsub.subpartid = subpart.id)";
        final String sqle = "  order by firstsub.id limit " + offset + "," + limit;
        String sql2 = "select count(*) numbers from firstsub,subpart where (firstsub.subpartid = subpart.id)  ";
        String str = "";
        if (id != "" && id != null) {
            str = String.valueOf(str) + " and firstsub.id like '%" + id + "%'";
        }
        if (name != "" && name != null) {
            str = String.valueOf(str) + " and firstsubname like '%" + name + "%'";
        }
        if (partid != "" && partid != null) {
            str = String.valueOf(str) + " and subpartid like '%" + partid + "%'";
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
        final List<ItemB> ItembList = new ArrayList<ItemB>();
        final ResJson resjson = new ResJson();
        resjson.setCode(0);
        resjson.setCount(numbers);
        resjson.setMsg("");
        try {
            PreparedStatement ps2 = db.getPs(sql);
            ps2 = db.getPs(sql);
            final ResultSet rs2 = ps2.executeQuery();
            while (rs2.next()) {
                final ItemB itemb = new ItemB();
                itemb.setId(rs2.getInt(1));
                itemb.setName(rs2.getString(2));
                itemb.setForname(rs2.getString(3));
                ItembList.add(itemb);
            }
            resjson.setData(ItembList);
            json = gson.toJson((Object)resjson);
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
