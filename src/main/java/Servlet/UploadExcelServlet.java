package Servlet;

import Repository.ExcelDao;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@MultipartConfig
public class UploadExcelServlet extends HttpServlet {

    
    public static String tableName;
    public static List<String> columnNames = new ArrayList<>();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part filePart = req.getPart("file"); 
        String fileName = extractFileName(filePart);
        InputStream fileContent = filePart.getInputStream();

        
        Workbook workbook = new XSSFWorkbook(fileContent);
        Sheet sheet = workbook.getSheetAt(0); 

        
        Row headerRow = sheet.getRow(0);

        columnNames.clear(); 

        
        for (int i = 0; i < headerRow.getPhysicalNumberOfCells(); i++) {
            String columnName = headerRow.getCell(i).getStringCellValue();
            columnNames.add(columnName);
        }

        
        ExcelDao excelDao = new ExcelDao();
        try {
            tableName = fileName.substring(0, fileName.lastIndexOf('.')); 
            excelDao.createTable(tableName, columnNames, sheet); 
            excelDao.insertRows(tableName, columnNames, sheet); 

            resp.setContentType("text/html");
            PrintWriter out = resp.getWriter();
            out.println("<script type='text/javascript'>");
            out.println("alert('File uploaded successfully.');");
            out.println("window.location.href = 'home.jsp';"); 
            out.println("</script>");


        } catch (SQLException e) {
            e.printStackTrace();
            resp.getWriter().println("Error occurred while processing the file: " + e.getMessage());
        } finally {
            workbook.close();
        }



    }

    
    private String extractFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        for (String cd : contentDisposition.split(";")) {
            if (cd.trim().startsWith("filename")) {
                return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
