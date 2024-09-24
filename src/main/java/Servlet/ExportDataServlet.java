package Servlet;

import Repository.ExcelDao;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ExportDataServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ExcelDao excelDao = new ExcelDao();
        String tableName = UploadExcelServlet.tableName; 
        List<String> columnNames = UploadExcelServlet.columnNames; 

        if (tableName == null) {
            resp.getWriter().println("Please upload an Excel file first.");
            return;
        }

        try {
            
            List<Map<String, Object>> data = excelDao.getAllData(tableName, columnNames);

            
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet(tableName);

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            for (int i = 0; i < columnNames.size(); i++) {
                headerRow.createCell(i + 1).setCellValue(columnNames.get(i));
            }

            int rowIndex = 1;
            for (Map<String, Object> rowData : data) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue((Integer) rowData.get("id"));
                for (int i = 0; i < columnNames.size(); i++) {
                    row.createCell(i + 1).setCellValue(rowData.get(columnNames.get(i)).toString());
                }
            }

            String filePath = "/home/vishnur/Downloads/" + tableName + "_export.xlsx";
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }

            workbook.close();

            resp.setContentType("text/html");
            PrintWriter out = resp.getWriter();
            out.println("<script type='text/javascript'>");
            out.println("alert('File exported successfully.');");
            out.println("window.location.href = 'home.jsp';"); 
            out.println("</script>");
        } catch (SQLException e) {
            e.printStackTrace();
            resp.getWriter().println("Error fetching data: " + e.getMessage());
        }
    }
}
