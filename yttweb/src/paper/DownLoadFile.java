// 
// 
// 

package paper;

import java.io.OutputStream;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(description = "\u4e0b\u8f7d\u6587\u4ef6", urlPatterns = { "/DownLoadFile" })
public class DownLoadFile extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
    
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        final String savepath = String.valueOf(Thread.currentThread().getContextClassLoader().getResource("").getPath().substring(0, Thread.currentThread().getContextClassLoader().getResource("").getPath().length() - 16)) + File.separator + "WEB-INF" + File.separator + "uploadFile" + File.separator;
        final String fileurl = request.getParameter("filename");
        final String filename = String.valueOf(savepath) + fileurl;
        response.setContentType(this.getServletContext().getMimeType(filename));
        response.setHeader("Content-Disposition", "attachment;filename=" + filename);
        final InputStream in = new FileInputStream(filename);
        final OutputStream out = (OutputStream)response.getOutputStream();
        int b;
        while ((b = in.read()) != -1) {
            out.write(b);
        }
        in.close();
        out.close();
    }
}
