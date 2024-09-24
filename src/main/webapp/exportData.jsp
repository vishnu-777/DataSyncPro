<%@ page import="Servlet.UploadExcelServlet" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Export Data</title>
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
        .button-container {
            text-align: center;
            margin-top: 50px;
        }
        button {
            background-color: #4CAF50;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
        button:hover {
            background-color: #45a049;
        }
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
    <script type="text/javascript">
            <%
                
                if (UploadExcelServlet.tableName == null) {
            %>
                alert("Please upload an Excel file first.");
                window.location.href = "home.jsp"; 
            <%
                }
            %>
        </script>
</head>
<body>
    <h2>Export Data to Excel</h2>

    <div class="button-container">
        <form action="ExportDataServlet" method="get" >
            <button type="submit">Export Data</button>
        </form>
    </div>
    <div class="back-button">
                <form action="home.jsp">
                    <button type="submit">Back to Dashboard</button>
                </form>
    </div>
</body>
</html>
