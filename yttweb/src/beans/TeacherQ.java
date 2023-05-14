// 
// 
// 

package beans;

import java.sql.Date;

public class TeacherQ
{
    private int empnum;
    private String name;
    private String sex;
    private String majorname;
    private String titlename;
    private Date birthday;
    private String telephone;
    
    public int getEmpnum() {
        return this.empnum;
    }
    
    public void setEmpnum(final int empnum) {
        this.empnum = empnum;
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
    
    public String getMajorname() {
        return this.majorname;
    }
    
    public void setMajorname(final String majorname) {
        this.majorname = majorname;
    }
    
    public String getTitlename() {
        return this.titlename;
    }
    
    public void setTitlename(final String titlename) {
        this.titlename = titlename;
    }
    
    public Date getBirthday() {
        return this.birthday;
    }
    
    public void setBirthday(final Date birthday) {
        this.birthday = birthday;
    }
    
    public String getTelephone() {
        return this.telephone;
    }
    
    public void setTelephone(final String telephone) {
        this.telephone = telephone;
    }
}
