<%@ page import="Servlet.UploadExcelServlet" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add New Record</title>
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
        form {
            max-width: 600px;
            margin: auto;
            background-color: white;
            padding: 20px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }
        label {
            display: block;
            margin: 15px 0 5px;
        }
        input[type="text"], input[type="number"], input[type="date"] {
            width: 100%;
            padding: 10px;
            margin: 5px 0;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        button {
            background-color: #4CAF50;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            margin-top: 20px;
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
    <h2>Add New Record to <%= UploadExcelServlet.tableName %></h2>

    <form action="AddRecordServlet" method="post">
        <%
            for (String columnName : UploadExcelServlet.columnNames) {
        %>
            <label for="<%= columnName %>"><%= columnName %></label>
            <input type="text" id="<%= columnName %>" name="<%= columnName %>" required>
        <%
            }
        %>
        <button type="submit">Add Record</button>
    </form>

    <!-- Back to Dashboard Button -->
    <div class="back-button">
        <form action="home.jsp">
            <button type="submit">Back to Dashboard</button>
        </form>
    </div>
</body>
</html>
