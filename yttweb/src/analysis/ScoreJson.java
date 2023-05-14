// 
// 
// 

package analysis;

import java.util.List;

public class ScoreJson
{
    private int code;
    private String msg;
    private int count;
    private List<Score> data;
    
    public int getCode() {
        return this.code;
    }
    
    public void setCode(final int code) {
        this.code = code;
    }
    
    public String getMsg() {
        return this.msg;
    }
    
    public void setMsg(final String msg) {
        this.msg = msg;
    }
    
    public int getCount() {
        return this.count;
    }
    
    public void setCount(final int count) {
        this.count = count;
    }
    
    public List<Score> getData() {
        return this.data;
    }
    
    public void setData(final List<Score> data) {
        this.data = data;
    }
}
