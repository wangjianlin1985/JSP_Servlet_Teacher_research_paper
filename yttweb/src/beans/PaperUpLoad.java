// 
// 
// 

package beans;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Collection;
import com.google.gson.Gson;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(description = "\u6587\u4ef6\u4e0a\u4f20\u5904\u7406", urlPatterns = { "/PaperUpLoad" })
@MultipartConfig
public class PaperUpLoad extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
    
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String fileName = null;
        String fileNameEnd = null;
        final int rd = (int)((Math.random() * 9.0 + 1.0) * 10000.0);
        final String savePath = String.valueOf(Thread.currentThread().getContextClassLoader().getResource("").getPath().substring(0, Thread.currentThread().getContextClassLoader().getResource("").getPath().length() - 16)) + File.separator + "WEB-INF" + File.separator + "uploadFile";
        final Collection<Part> parts = (Collection<Part>)request.getParts();
        if (parts.size() == 1) {
            final Part part = request.getPart("file");
            final String header = part.getHeader("content-disposition");
            fileName = this.getFileName(header);
            final String[] aa = fileName.split("\\.");
            fileNameEnd = String.valueOf(aa[0]) + rd + '.' + aa[1];
            part.write(String.valueOf(savePath) + File.separator + fileNameEnd);
        }
        else {
            for (final Part part : parts) {
                final String header = part.getHeader("content-disposition");
                fileName = this.getFileName(header);
                part.write(String.valueOf(savePath) + File.separator + fileName);
            }
        }
        final FileRes res = new FileRes();
        String json = "";
        final Gson gson = new Gson();
        res.setCode(0);
        res.setMsg("");
        res.setSrc(fileNameEnd);
        json = gson.toJson((Object)res);
        final PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
        out.close();
    }
    
    public String getFileName(final String header) {
        final String[] tempArr1 = header.split(";");
        final String[] tempArr2 = tempArr1[2].split("=");
        final String fileName = tempArr2[1].substring(tempArr2[1].lastIndexOf("\\") + 1).replaceAll("\"", "");
        return fileName;
    }
}
