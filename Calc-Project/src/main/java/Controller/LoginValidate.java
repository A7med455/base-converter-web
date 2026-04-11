package Controller;

import java.io.IOException;
import java.io.PrintWriter;

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
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
       
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
    	// get parameter from the user 
        String UserName = request.getParameter("UserName");
        String password = request.getParameter("password");
        response.setContentType("text/html");
        PrintWriter pw=response.getWriter();
        
        if(UserName == null || UserName.isEmpty() || password == null || password.isEmpty())
        {
        	// will be replaced with thymeleaf later
        	  request.setAttribute("error", "Please enter both fields");
        	  request.getRequestDispatcher("/Login.html").include(request, response);
        }
        
        // create DataBase and validate
        DataBase db=new DataBase();
        User loggedInUser = db.ValidateUser(UserName, password);
        
        if(loggedInUser != null)
        {
        	//means login successfully 
        	// we create a session for the user here so we can remember the user
        	HttpSession session = request.getSession();
        	// stores the user object in the session so other page can access later
        	session.setAttribute("currentUser", loggedInUser);
        	// Auto-logs out user if inactive for 30min
        	session.setMaxInactiveInterval(30*60); // 30 min
        	// redirect  to the other page and change url 
        	response.sendRedirect(request.getContextPath() + "/converter");
        }else
        { 	//Login failed show error and forward back to login page
        	request.setAttribute("error", "Invalid username or password");
        	request.getRequestDispatcher("/Login.html").forward(request, response);
        }
        
        

    }      
}
