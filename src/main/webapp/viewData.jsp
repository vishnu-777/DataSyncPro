<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="Servlet.UploadExcelServlet" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%= UploadExcelServlet.tableName %> Data</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            margin: 20px;
        }

        h2 {
            text-align: center;
            color: #333;
        }

        table {
            width: 100%;
            margin: 20px auto;
            border-collapse: collapse;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }

        th, td {
            padding: 12px 15px;
            text-align: left;
            border: 1px solid #ddd;
        }

        th {
            background-color: #4CAF50;
            color: white;
        }

        tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        tr:hover {
            background-color: #f1f1f1;
        }

        tbody tr {
            transition: background-color 0.3s ease;
        }

        .container {
            max-width: 90%;
            margin: auto;
        }

        /* Style for the back button */
        .back-button {
            text-align: center;
            margin: 30px;
        }

        .back-button button {
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
        }

        .back-button button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Data from <%= UploadExcelServlet.tableName %></h2>

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <%
                        for (String columnName : UploadExcelServlet.columnNames) {
                            out.println("<th>" + columnName + "</th>");
                        }
                    %>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Map<String, Object>> data = (List<Map<String, Object>>) request.getAttribute("data");
                    if (data != null) {
                        for (Map<String, Object> row : data) {
                            out.println("<tr>");
                            out.println("<td>" + row.get("id") + "</td>");
                            for (String columnName : UploadExcelServlet.columnNames) {
                                out.println("<td>" + row.get(columnName) + "</td>");
                            }
                            out.println("</tr>");
                        }
                    } else {
                        out.println("<tr><td colspan='" + (UploadExcelServlet.columnNames.size() + 1) + "'>No data available</td></tr>");
                    }
                %>
            </tbody>
        </table>

        <!-- Back to Home Button -->
        <div class="back-button">
            <form action="home.jsp">
                <button type="submit">Back to Dashboard</button>
            </form>
        </div>
    </div>
</body>
</html>
