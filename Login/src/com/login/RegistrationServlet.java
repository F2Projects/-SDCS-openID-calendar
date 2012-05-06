/**
 * RegistrationServlet.java
 * ----------------------------
 * This servlet is called by "registration.jsp" to store some informations of a new Google user.
 * In particular, it stores his username (namely email) and his role, that is selected into the page itself. 
 * Finally it redirects your browser back to the index page.
 */
package com.login;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.login.data.User;
import com.login.data.UsersDB;

/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// User object used to store informations received by the post request
		User newUser = new User();
		newUser.setUsername(request.getParameter("username"));
		newUser.setRole(request.getParameter("role"));
		
		// we get a db instance where will be called the method saveNewUser to save the newUser object
		UsersDB db = UsersDB.getDb();
		String message = db.saveNewUser(newUser);
		// in the end we come back to index page printing the status message in the bottom of the page
		request.setAttribute("loginFailedMessage", message);
        request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

}
