// 
// 
// 

package paper;

import java.sql.Timestamp;

public class AuditBean
{
    private Timestamp time;
    private int auditorid;
    private String auditor;
    private String status;
    private String views;
    
    public Timestamp getTime() {
        return this.time;
    }
    
    public void setTime(final Timestamp time) {
        this.time = time;
    }
    
    public int getAuditorid() {
        return this.auditorid;
    }
    
    public void setAuditorid(final int auditorid) {
        this.auditorid = auditorid;
    }
    
    public String getAuditor() {
        return this.auditor;
    }
    
    public void setAuditor(final String auditor) {
        this.auditor = auditor;
    }
    
    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(final String status) {
        this.status = status;
    }
    
    public String getViews() {
        return this.views;
    }
    
    public void setViews(final String views) {
        this.views = views;
    }
}
