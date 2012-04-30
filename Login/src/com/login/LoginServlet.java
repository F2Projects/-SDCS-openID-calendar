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
 * Servlet implementation class LoginServlet
 */
@WebServlet(description = "A simple login servlet", urlPatterns = { "/LoginServlet" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final UsersDB db;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        this.db = UsersDB.getDb();
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
		
		LoginHolder.setUsername(username);
		LoginHolder.setPassword(password);
		
		if(this.doLogin()){
			
			request.setAttribute("current_user", this.db.getAnUser(username));
		
			Repo myRepo = Repo.getRepo();
			request.setAttribute("repoFiles", myRepo.getFileList());
			request.getRequestDispatcher("/profile.jsp").forward(request, response);
		}
		else{
			request.setAttribute("loginFailedMessage", "Ok, ok, you are a dumb! Username and/or password are/is wrong...");
		    request.getRequestDispatcher("/index.jsp").forward(request, response);
		}
		
		
	}
	
	private boolean doLogin(){
		try {
			LoginHolder.getContext().login();
		} catch (LoginException e) {
			LoginHolder.cleanContext();
			return false;
		}
		
		return true;
		
	}

}
