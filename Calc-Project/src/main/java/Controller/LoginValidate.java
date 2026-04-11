package Controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import Model.User;
import DAO.DataBase;

@WebServlet("/login")
public class LoginValidate extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DataBase db = new DataBase();

    @Override
    public void init() throws ServletException {
        db.initTables();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("Login.html");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String UserName = request.getParameter("UserName");
        String password = request.getParameter("password");

        // Basic validation
        if (UserName == null || password == null || UserName.trim().isEmpty() || password.trim().isEmpty()) {
            request.setAttribute("error", "Please enter both username and password");
            request.getRequestDispatcher("/Login.html").forward(request, response);
            return;
        }

        // Validate against database
        User loggedInUser = db.ValidateUser(UserName.trim(), password);

        if (loggedInUser != null) {
            // Success: Create session & redirect
            HttpSession session = request.getSession();
            session.setAttribute("currentUser", loggedInUser);
            session.setMaxInactiveInterval(30 * 60); // 30 minutes

            response.sendRedirect(request.getContextPath() + "/converter");
        } else {
            // Fail: Show error
            request.setAttribute("error", "Invalid username or password");
            request.getRequestDispatcher("/Login.html").forward(request, response);
        }
    }
}