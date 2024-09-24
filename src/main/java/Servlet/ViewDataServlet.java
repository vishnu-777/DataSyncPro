package Servlet;

import Repository.ExcelDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ViewDataServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ExcelDao excelDao = new ExcelDao();
        String tableName = UploadExcelServlet.tableName; 

        
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        
        if (tableName == null || tableName.isEmpty()) {
            
            out.println("<script type='text/javascript'>");
            out.println("alert('Please upload an Excel file first.');");
            out.println("window.location.href = 'home.jsp';"); 
            out.println("</script>");
            return;
        }

        try {
            
            List<Map<String, Object>> data = excelDao.getAllData(tableName, UploadExcelServlet.columnNames);

            
            req.setAttribute("data", data);
            req.getRequestDispatcher("viewData.jsp").forward(req, resp);

        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<script type='text/javascript'>");
            out.println("alert('Error fetching data: " + e.getMessage() + "');");
            out.println("</script>");
        }
    }
}
