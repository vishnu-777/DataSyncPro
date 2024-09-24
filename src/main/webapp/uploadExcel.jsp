<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>DataSyncPro - Upload Excel File</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f0f2f5;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .upload-container {
            background-color: #ffffff;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            width: 400px;
            text-align: center;
        }
        .upload-container h2 {
            margin-bottom: 20px;
            color: #333;
        }
        .upload-container label {
            font-size: 14px;
            color: #555;
        }
        .upload-container input[type="file"] {
            width: 100%;
            padding: 10px;
            margin: 20px 0;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        .upload-container input[type="submit"] {
            width: 100%;
            padding: 10px;
            background-color: #007bff;
            border: none;
            color: white;
            font-size: 16px;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        .upload-container input[type="submit"]:hover {
            background-color: #0056b3;
        }
        .upload-container .back-link {
            margin-top: 20px;
            display: block;
            font-size: 14px;
            color: #555;
        }
        .upload-container .back-link a {
            color: #007bff;
            text-decoration: none;
        }
        .upload-container .back-link a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="upload-container">
        <h2>Upload Excel File</h2>
        <form action="uploadExcelServlet" method="post" enctype="multipart/form-data">
            <label for="file">Choose Excel File:</label>
            <input type="file" id="file" name="file" accept=".xlsx, .xls" required>

            <input type="submit" value="Upload">
        </form>
        <div class="back-link">
            <a href="home.jsp">Back to Dashboard</a>
        </div>
    </div>
</body>
</html>
