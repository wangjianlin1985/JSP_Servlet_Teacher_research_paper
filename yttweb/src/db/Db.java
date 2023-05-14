// 
// 
// 

package db;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Connection;

public class Db
{
    private String dbDriver;
    private String ConnStr;
    public Connection connect;
    public ResultSet rs;
    
    public Connection getConnect() {
        return this.connect;
    }
    
    public void setConnect(final Connection connect) {
        this.connect = connect;
    }
    
    public Db() {
        this.dbDriver = "com.mysql.jdbc.Driver";
        this.ConnStr = "jdbc:mysql://127.0.0.1:3306/jsp_keyan?useUnicode=true&characterEncoding=UTF-8&useSSL=false";
        this.connect = null;
        this.rs = null;
        try {
            Class.forName(this.dbDriver).newInstance();
            this.connect = DriverManager.getConnection(this.ConnStr, "root", "123456");
        }
        catch (Exception ex) {
            System.out.println("error!");
        }
    }
    
    public ResultSet executeQuery(final String sql) {
        try {
            final Statement stmt = this.connect.createStatement();
            this.rs = stmt.executeQuery(sql);
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return this.rs;
    }
    
    public void executeUpdate(final String sql) {
        Statement stmt = null;
        this.rs = null;
        try {
            stmt = this.connect.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            this.connect.close();
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    public PreparedStatement getPs(final String sql) {
        PreparedStatement ps = null;
        try {
            ps = this.connect.prepareStatement(sql);
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return ps;
    }
}
