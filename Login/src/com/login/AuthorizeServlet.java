package com.login;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.login.data.Event;
import com.login.oauth.GoogleAuthorization;

/**
 * Servlet implementation class AuthorizeServlet
 */
@WebServlet("/AuthorizeServlet")
public class AuthorizeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuthorizeServlet() {
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
		Event eventToSave = new Event();
		eventToSave.setSubject(request.getParameter("subject"));
		
		eventToSave.setComment(request.getParameter("comment"));
		
		eventToSave.setDay(Integer.parseInt(request.getParameter("day")));
		eventToSave.setMounth(Integer.parseInt(request.getParameter("mounth")));
		eventToSave.setYear(Integer.parseInt(request.getParameter("year")));
		
		request.getSession().setAttribute("eventToSave", eventToSave);
		
		if(request.getSession().getAttribute("authorizationManager")==null){
			GoogleAuthorization authorizationManager = new GoogleAuthorization();
			request.getSession().setAttribute("authorizationManager", authorizationManager);
			String authUrl = authorizationManager.getAuthorizationUrl("http://localhost:8080/Login/remote/calendar2step");
			response.sendRedirect(authUrl);
		}
		else
			request.getRequestDispatcher("/remote/calendar2step").forward(request, response);
	}

}
