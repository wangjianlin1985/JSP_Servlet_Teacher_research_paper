// 
// 
// 

package test;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Collection;
import com.google.gson.Gson;
import beans.FileRes;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(description = "\u4e0a\u4f20\u6d4b\u8bd5", urlPatterns = { "/UploadTest" })
@MultipartConfig
public class UploadTest extends HttpServlet
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
        final String savePath = "G:\\MyJava\\yttweb\\WebRoot\\WEB-INF\\uploadFile";
        final Collection<Part> parts = (Collection<Part>)request.getParts();
        if (parts.size() == 1) {
            final Part part = request.getPart("file");
            final String header = part.getHeader("content-disposition");
            fileName = this.getFileName(header);
            part.write(String.valueOf(savePath) + File.separator + fileName);
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
        res.setSrc(fileName);
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
