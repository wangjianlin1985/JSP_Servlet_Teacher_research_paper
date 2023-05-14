// 
// 
// 

package paper;

import java.sql.Date;

public class PaperBean
{
    private String title;
    private String firstauthor;
    private Date pubtime;
    private int pubtypeid;
    private int journalid;
    private int subtypeid;
    private int firstsubid;
    private int prosourceid;
    private String istrans;
    private String fileurl;
    private String layout;
    private String pubarea;
    
    public int getSubtypeid() {
        return this.subtypeid;
    }
    
    public void setSubtypeid(final int subtypeid) {
        this.subtypeid = subtypeid;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(final String title) {
        this.title = title;
    }
    
    public String getFirstauthor() {
        return this.firstauthor;
    }
    
    public void setFirstauthor(final String firstauthor) {
        this.firstauthor = firstauthor;
    }
    
    public Date getPubtime() {
        return this.pubtime;
    }
    
    public void setPubtime(final Date pubtime) {
        this.pubtime = pubtime;
    }
    
    public int getPubtypeid() {
        return this.pubtypeid;
    }
    
    public void setPubtypeid(final int pubtypeid) {
        this.pubtypeid = pubtypeid;
    }
    
    public int getJournalid() {
        return this.journalid;
    }
    
    public void setJournalid(final int journalid) {
        this.journalid = journalid;
    }
    
    public int getFirstsubid() {
        return this.firstsubid;
    }
    
    public void setFirstsubid(final int firstsubid) {
        this.firstsubid = firstsubid;
    }
    
    public int getProsourceid() {
        return this.prosourceid;
    }
    
    public void setProsourceid(final int prosourceid) {
        this.prosourceid = prosourceid;
    }
    
    public String getPubarea() {
        return this.pubarea;
    }
    
    public void setPubarea(final String pubarea) {
        this.pubarea = pubarea;
    }
    
    public String getIstrans() {
        return this.istrans;
    }
    
    public void setIstrans(final String istrans) {
        this.istrans = istrans;
    }
    
    public String getFileurl() {
        return this.fileurl;
    }
    
    public void setFileurl(final String fileurl) {
        this.fileurl = fileurl;
    }
    
    public String getLayout() {
        return this.layout;
    }
    
    public void setLayout(final String layout) {
        this.layout = layout;
    }
}
