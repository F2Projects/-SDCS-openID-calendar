/**
 * CheckLoginSevlet.java
 * -----------------------
 * This servlet is called after the user ends the login procedure on the Google site.
 * Here is checked if the login procedure ends successfully and if the user already have a role. If not
 * he was redirect to the registration page where he can assign a role to himself.
 * All this informations are stored into a database, as fields of the tables "users" and "roles" and 
 * accessed through the class "UsersDB.java"
 */
package com.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.openid4java.message.ParameterList;

import com.login.data.User;
import com.login.data.UsersDB;
import com.login.openid.GoogleLogin;

/**
 * Servlet implementation class CheckLoginServlet
 */
@WebServlet("/CheckLoginServlet")
public class CheckLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckLoginServlet() {
        super();
        
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
		// this is the url received as reply by the google server
		String receivingURL = request.getRequestURL() + "?" + request.getQueryString();
		
		// and this is a list of all parameters that we have received
		ParameterList parameters = new ParameterList(request.getParameterMap());
		
		GoogleLogin loginManager = (GoogleLogin)request.getSession().getAttribute("loginManager");
		// if the login procedure ends successfully
		if(loginManager.checkLogin(receivingURL, parameters)){
			User loggedUser = loginManager.getLoggedUser();
			UsersDB db = UsersDB.getDb();
			try{
				// we try to retreive the role of the current logged in user
				loggedUser.setRole(db.getUserRole(loggedUser.getUsername()));
				request.getRequestDispatcher("/profile.jsp").forward(request, response);
			} catch(Exception e){
				// if he doesn't have one, is raised this exception and we redirect to the registration page
				request.getRequestDispatcher("/register.jsp").forward(request, response);
			}
		}else{
			// finally this piece of code is executed if the login procedure doesn't ends correctly
			request.setAttribute("loginFailedMessage", "Ok, ok, you are a dumb! Username and/or password are/is wrong...");
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}
		
		
	}

}
