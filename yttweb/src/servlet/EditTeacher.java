// 
// 
// 

package servlet;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import net.sf.json.JSONObject;
import java.io.PrintWriter;
import java.sql.SQLException;
import db.GetReader;
import db.Db;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet({ "/EditTeacher" })
public class EditTeacher extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
    
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        final PrintWriter out = response.getWriter();
        final Db db = new Db();
        final JSONObject json = GetReader.receivePost(request);
        final int id = Integer.parseInt(json.getString("id"));
        final int empnum = Integer.parseInt(json.getString("empnum"));
        final String name = json.getString("name");
        final String sex = json.getString("sex");
        final String telephone = json.getString("telephone");
        final String birthday = json.getString("birthday");
        final int majorid = Integer.parseInt(json.getString("majorid"));
        final int titleid = Integer.parseInt(json.getString("titleid"));
        Label_0627: {
            if (id != empnum) {
                String sql = "select * from teacher where empnum = ?";
                PreparedStatement ps = db.getPs(sql);
                try {
                    ps.setInt(1, empnum);
                    final ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        out.print("1");
                        rs.close();
                        break Label_0627;
                    }
                    rs.close();
                    sql = "update teacher set empnum = ?,name = ?,sex = ?,telephone = ?, birthday = ?,majorid = ?,titleid = ? where empnum = ?";
                    ps = db.getPs(sql);
                    try {
                        ps.setInt(1, empnum);
                        ps.setString(2, name);
                        ps.setString(3, sex);
                        ps.setString(4, telephone);
                        ps.setString(5, birthday);
                        ps.setInt(6, majorid);
                        ps.setInt(7, titleid);
                        ps.setInt(8, id);
                        final int row = ps.executeUpdate();
                        ps.close();
                        final String sql2 = "update paper set firstauthor = ? where teacherid = ? and mentorflag = '\u5426'";
                        final PreparedStatement ps2 = db.getPs(sql2);
                        ps2.setString(1, name);
                        ps2.setInt(2, empnum);
                        final int row2 = ps2.executeUpdate();
                        ps2.close();
                        db.getConnect().close();
                        if (row > 0 && row2 > 0) {
                            out.print("2");
                        }
                        else {
                            out.print("3");
                        }
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break Label_0627;
                }
                catch (SQLException e2) {
                    e2.printStackTrace();
                    break Label_0627;
                }
            }
            String sql = "update teacher set empnum = ?,name = ?,sex = ?,telephone = ?, birthday = ?,majorid = ?,titleid = ? where empnum = ?";
            PreparedStatement ps = db.getPs(sql);
            try {
                ps.setInt(1, empnum);
                ps.setString(2, name);
                ps.setString(3, sex);
                ps.setString(4, telephone);
                ps.setString(5, birthday);
                ps.setInt(6, majorid);
                ps.setInt(7, titleid);
                ps.setInt(8, empnum);
                final int row3 = ps.executeUpdate();
                ps.close();
                final String sql3 = "update paper set firstauthor = ? where teacherid = ? and mentorflag = '\u5426'";
                final PreparedStatement ps3 = db.getPs(sql3);
                ps3.setString(1, name);
                ps3.setInt(2, empnum);
                final int row4 = ps3.executeUpdate();
                ps3.close();
                db.getConnect().close();
                if (row3 > 0 && row4 > 0) {
                    out.print("2");
                }
                else {
                    out.print("3");
                }
            }
            catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        out.flush();
        out.close();
    }
}
