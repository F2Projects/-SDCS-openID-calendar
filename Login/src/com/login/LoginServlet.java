/**
 * LoginServlet.java
 * -------------------------
 * This is the first called servlet. It simply generates the Google OpenID and redirect your browser to it.
 * Nothing else.
 * Simply and useful.
 * All the login procedure are, instead, implemented into the "GoogleLogin.java" class
 */
package com.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.login.openid.GoogleLogin;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(description = "A simple login servlet", urlPatterns = { "/LoginServlet" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
		// this is the instance of the class GoogleLogin that we use in every login procedure of this web app
		GoogleLogin loginManager = new GoogleLogin();
		// and we save this object as session attribute
		request.getSession().setAttribute("loginManager", loginManager);
		
		// through the method genLoginUrl, we generate the Google OpenID url where, subsequently, your browser will be redirect 
		String loginUrl = loginManager.genLoginUrl("http://localhost:8080/Login/remote/check");
		response.sendRedirect(loginUrl);
		
		
	}
	
}
