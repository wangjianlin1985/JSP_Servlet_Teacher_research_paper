// 
// 
// 

package db;

import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import net.sf.json.JSONObject;
import javax.servlet.http.HttpServletRequest;

public class GetReader
{
    public static JSONObject receivePost(final HttpServletRequest request) throws IOException, UnsupportedEncodingException {
        final BufferedReader br = new BufferedReader(new InputStreamReader((InputStream)request.getInputStream(), "utf-8"));
        String line = null;
        final StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        final JSONObject json = JSONObject.fromObject((Object)sb.toString());
        return json;
    }
}
