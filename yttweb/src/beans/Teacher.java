// 
// 
// 

package beans;

import java.sql.Date;

public class Teacher
{
    private int empnum;
    private String password;
    private String name;
    private String sex;
    private int majorid;
    private int titleid;
    private String telephone;
    private Date birthday;
    
    public int getEmpnum() {
        return this.empnum;
    }
    
    public void setEmpnum(final int empnum) {
        this.empnum = empnum;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(final String password) {
        this.password = password;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public String getSex() {
        return this.sex;
    }
    
    public void setSex(final String sex) {
        this.sex = sex;
    }
    
    public int getMajorid() {
        return this.majorid;
    }
    
    public void setMajorid(final int majorid) {
        this.majorid = majorid;
    }
    
    public int getTitleid() {
        return this.titleid;
    }
    
    public void setTitleid(final int titleid) {
        this.titleid = titleid;
    }
    
    public String getTelephone() {
        return this.telephone;
    }
    
    public void setTelephone(final String telephone) {
        this.telephone = telephone;
    }
    
    public Date getBirthday() {
        return this.birthday;
    }
    
    public void setBirthday(final Date birthday) {
        this.birthday = birthday;
    }
}
