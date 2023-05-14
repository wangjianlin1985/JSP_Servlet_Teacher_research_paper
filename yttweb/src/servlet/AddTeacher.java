// 
// 
// 

package servlet;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import net.sf.json.JSONObject;
import com.google.gson.Gson;
import java.io.PrintWriter;
import java.sql.SQLException;
import db.Db;
import db.GetReader;
import beans.Teacher;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(description = "add teacher", urlPatterns = { "/AddTeacher" })
public class AddTeacher extends HttpServlet
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
        Teacher teacher = new Teacher();
        final JSONObject json = GetReader.receivePost(request);
        teacher = (Teacher)gson.fromJson(json.toString(), (Class)Teacher.class);
        String res = "";
        final Db db = new Db();
        PreparedStatement ps = null;
        String sql = "";
        sql = "select * from teacher where empNum = ? ";
        ps = db.getPs(sql);
        try {
            ps.setInt(1, teacher.getEmpnum());
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                res = "404";
            }
            else {
                sql = "insert into teacher values(?,?,?,?,?,?,?,?) ";
                ps = db.getPs(sql);
                try {
                    ps.setInt(1, teacher.getEmpnum());
                    ps.setString(2, teacher.getName());
                    ps.setString(3, teacher.getPassword());
                    ps.setString(4, teacher.getSex());
                    ps.setInt(5, teacher.getMajorid());
                    ps.setInt(6, teacher.getTitleid());
                    ps.setString(7, teacher.getTelephone());
                    ps.setDate(8, teacher.getBirthday());
                    final int row = ps.executeUpdate();
                    if (row > 0) {
                        res = "1";
                    }
                    else {
                        res = "2";
                    }
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            out.print(res);
            out.flush();
            out.close();
        }
        catch (SQLException e2) {
            e2.printStackTrace();
        }
    }
}
