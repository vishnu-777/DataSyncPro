package Servlet;

import Repository.AdminDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        AdminDao ad = new AdminDao();
        String crctpass = ad.getAdminLoginDetails(username);
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();

        if (crctpass.equals(password)) {
            resp.sendRedirect("home.jsp");
        } else {
            writer.println("<html>");
            writer.println("<head>");
            writer.println("<script type='text/javascript'>");
            writer.println("alert('Incorrect password entered!');");
            writer.println("window.location.href = 'login.jsp';");
            writer.println("</script>");
            writer.println("</head>");
            writer.println("<body></body>");
            writer.println("</html>");
        }
    }
}

