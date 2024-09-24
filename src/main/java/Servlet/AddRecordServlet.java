package Servlet;

import Repository.ExcelDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AddRecordServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tableName = UploadExcelServlet.tableName; 
        Map<String, String> columnValues = new HashMap<>();

        
        for (String columnName : UploadExcelServlet.columnNames) {
            String value = req.getParameter(columnName);
            columnValues.put(columnName, value);
        }

        
        ExcelDao excelDao = new ExcelDao();
        resp.setContentType("text/html");
        try {
            excelDao.insertSingleRecord(tableName, columnValues);

            
            resp.getWriter().println("<script type=\"text/javascript\">");
            resp.getWriter().println("alert('Data added successfully');");
            resp.getWriter().println("window.location.href = 'home.jsp';"); 
            resp.getWriter().println("</script>");

        } catch (SQLException e) {
            e.printStackTrace();
            resp.getWriter().println("Error while inserting the record: " + e.getMessage());
        }
    }
}
