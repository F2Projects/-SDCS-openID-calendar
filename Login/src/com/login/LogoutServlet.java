package com.login;

import java.io.IOException;

import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.login.data.UsersDB;
import com.login.jaascommons.LoginHolder;
import com.login.repo.Repo;

/**
 * Servlet implementation class LogoutServlet
 */
@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutServlet() {
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
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		
		try {
			LoginHolder.getContext(username, password).logout();
			LoginHolder.cleanContext();
			request.setAttribute("loginFailedMessage", "User " + username + " logged out!");
		    request.getRequestDispatcher("/index.jsp").forward(request, response);
		} catch (LoginException e) {
			request.setAttribute("current_user", UsersDB.getDb().getAnUser(username));
			request.setAttribute("repoFiles", Repo.getRepo().getFileList());
			request.setAttribute("uploadStatus", e.getMessage());
			request.getRequestDispatcher("/profile.jsp").forward(request, response);
		}
		
	}
}
