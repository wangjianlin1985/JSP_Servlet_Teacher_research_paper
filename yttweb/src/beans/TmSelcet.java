// 
// 
// 

package beans;

import java.util.List;

public class TmSelcet
{
    private List<Major> major;
    private List<Title> title;
    
    public List<Major> getMajor() {
        return this.major;
    }
    
    public void setMajor(final List<Major> major) {
        this.major = major;
    }
    
    public List<Title> getTitle() {
        return this.title;
    }
    
    public void setTitle(final List<Title> title) {
        this.title = title;
    }
}
