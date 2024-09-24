package Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;

public class ExcelDao {

    public void createTable(String tableName, List<String> columnNames, Sheet sheet) throws SQLException {
        Connection conn = null;

        try {
            conn = Connect.getConnection();
            Statement stmt = conn.createStatement();

            Row secondRow = sheet.getRow(1);
            StringBuilder query = new StringBuilder("CREATE TABLE IF NOT EXISTS ").append(tableName).append(" (");
            query.append("id SERIAL PRIMARY KEY, ");
            for (int i = 0; i < columnNames.size(); i++) {
                String columnName = columnNames.get(i);
                String columnType = "VARCHAR(255)";

                if (secondRow != null && secondRow.getCell(i) != null) {
                    Cell cell = secondRow.getCell(i);
                    if (cell.getCellType() == CellType.NUMERIC) {
                        double numericValue = cell.getNumericCellValue();
                        if (String.valueOf((int) numericValue).length() <= 9) {
                            columnType = "INT";
                        }
                    }
                }

                query.append('"').append(columnName).append('"').append(" ").append(columnType);
                if (i != columnNames.size() - 1) {
                    query.append(", ");
                }
            }

            query.append(");"); 

            
            stmt.executeUpdate(query.toString());

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while creating the table: " + e.getMessage());
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    public void insertRows(String tableName, List<String> columnNames, Sheet sheet) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = Connect.getConnection();

            
            StringBuilder sql = new StringBuilder("INSERT INTO ").append(tableName).append(" (");

            
            for (int i = 0; i < columnNames.size(); i++) {
                sql.append('"').append(columnNames.get(i)).append('"');
                if (i < columnNames.size() - 1) {
                    sql.append(", ");
                }
            }

            sql.append(") VALUES (");

            
            for (int i = 0; i < columnNames.size(); i++) {
                sql.append("?");
                if (i < columnNames.size() - 1) {
                    sql.append(", ");
                }
            }

            sql.append(")");

            pstmt = conn.prepareStatement(sql.toString());

            
            for (int i = 1; i <= sheet.getLastRowNum(); i++) { 
                Row row = sheet.getRow(i);
                if (row == null) continue; 

                for (int j = 0; j < columnNames.size(); j++) {
                    Cell cell = row.getCell(j);
                    if (cell == null) {
                        pstmt.setNull(j + 1, java.sql.Types.NULL);
                    } else {
                        switch (cell.getCellType()) {
                            case STRING:
                                pstmt.setString(j + 1, cell.getStringCellValue());
                                break;
                            case NUMERIC:
                                if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                                    
                                    pstmt.setDate(j + 1, new java.sql.Date(cell.getDateCellValue().getTime()));
                                } else {
                                    double numericValue = cell.getNumericCellValue();
                                    
                                    if (numericValue == (long) numericValue) {
                                        String numString = String.valueOf((long) numericValue);

                                        
                                        if (numString.length() > 9) {
                                            
                                            pstmt.setString(j + 1, numString);
                                        } else {
                                            
                                            pstmt.setLong(j + 1, (long) numericValue);
                                        }
                                    } else {
                                        
                                        pstmt.setDouble(j + 1, numericValue);
                                    }
                                }
                                break;
                            default:
                                pstmt.setString(j + 1, cell.toString());
                                break;
                        }
                    }
                }
                pstmt.addBatch();
            }
            pstmt.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while inserting rows: " + e.getMessage());
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public List<Map<String, Object>> getAllData(String tableName, List<String> columnNames) throws SQLException {
        List<Map<String, Object>> dataList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = Connect.getConnection();
            StringBuilder query = new StringBuilder("SELECT id, ");

            
            for (int i = 0; i < columnNames.size(); i++) {
                query.append('"').append(columnNames.get(i)).append('"');
                if (i < columnNames.size() - 1) {
                    query.append(", ");
                }
            }

            query.append(" FROM ").append(tableName);

            pstmt = conn.prepareStatement(query.toString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("id", rs.getInt("id"));

                for (String columnName : columnNames) {
                    row.put(columnName, rs.getObject(columnName));
                }
                dataList.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while fetching data: " + e.getMessage());
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }

        return dataList;
    }

    public void insertSingleRecord(String tableName, Map<String, String> columnValues) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = Connect.getConnection();

            StringBuilder sql = new StringBuilder("INSERT INTO ").append(tableName).append(" (");
            StringBuilder valuesPart = new StringBuilder(") VALUES (");

            int index = 0;
            for (String columnName : columnValues.keySet()) {
                sql.append('"').append(columnName).append('"');
                valuesPart.append("?");
                if (index < columnValues.size() - 1) {
                    sql.append(", ");
                    valuesPart.append(", ");
                }
                index++;
            }

            sql.append(valuesPart).append(")");

            pstmt = conn.prepareStatement(sql.toString());

            index = 1;
            for (Map.Entry<String, String> entry : columnValues.entrySet()) {
                String columnName = entry.getKey();
                String value = entry.getValue();

                String columnType = getColumnDataType(conn, tableName, columnName); 

                if ("int".equalsIgnoreCase(columnType) || "integer".equalsIgnoreCase(columnType)) {
                    pstmt.setInt(index, Integer.parseInt(value));
                } else if ("float".equalsIgnoreCase(columnType)) {
                    pstmt.setFloat(index, Float.parseFloat(value));
                } else if ("boolean".equalsIgnoreCase(columnType)) {
                    pstmt.setBoolean(index, Boolean.parseBoolean(value));
                } else {
                    pstmt.setString(index, value); 
                }

                index++;
            }

            
            pstmt.executeUpdate();

        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            throw new SQLException("Error inserting record: " + e.getMessage());
        } finally {
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    
    private String getColumnDataType(Connection conn, String tableName, String columnName) throws SQLException {
        String query = "SELECT data_type FROM information_schema.columns WHERE table_name = ? AND column_name = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, tableName);
            pstmt.setString(2, columnName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("data_type"); 
                }
            }
        }
        return "text"; 
    }

}
