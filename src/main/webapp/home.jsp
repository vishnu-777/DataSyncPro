<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>DataSyncPro - Admin Dashboard</title>
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
        .dashboard-container {
            background-color: #ffffff;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            width: 400px;
            text-align: center;
        }
        .dashboard-container h2 {
            margin-bottom: 20px;
            color: #333;
        }
        .card {
            display: block;
            background-color: #007bff;
            padding: 15px;
            margin: 10px 0;
            border-radius: 5px;
            color: white;
            text-decoration: none;
            font-size: 18px;
            transition: background-color 0.3s ease;
        }
        .card:hover {
            background-color: #0056b3;
        }
        .logout-button {
            margin-top: 20px;
            width: 100%;
            padding: 10px;
            background-color: #f44336;
            border: none;
            color: white;
            font-size: 16px;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        .logout-button:hover {
            background-color: #d32f2f;
        }
    </style>
</head>
<body>
    <div class="dashboard-container">
        <h2>Welcome, Admin - DataSyncPro Dashboard</h2>

        <a href="uploadExcel.jsp" class="card">Upload Excel File</a>
        <a href="viewData" class="card">View Data</a>
        <a href="exportData.jsp" class="card">Export Data to Excel</a>
        <a href="addRecord.jsp" class="card">Add New Record</a>

        <form action="login.jsp" method="post">
            <input type="submit" class="logout-button" value="Logout">
        </form>
    </div>
</body>
</html>
