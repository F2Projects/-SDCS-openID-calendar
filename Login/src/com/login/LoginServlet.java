package com.login;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.login.data.User;

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
		//request.setAttribute("loginFailedMessage", "Ok, ok, you are a dumb! Username and/or password are/is wrong...");
        //request.getRequestDispatcher("/Login/index.jsp").forward(request, response);
		User loggedUser = new User();
		loggedUser.setName("Pasquale");
		loggedUser.setSurname("Boemio");
		loggedUser.setUsername("pau");
		loggedUser.setRole("Student");
		request.setAttribute("current_user", loggedUser);
		request.getRequestDispatcher("/profile.jsp").forward(request, response);
		
	}

}
