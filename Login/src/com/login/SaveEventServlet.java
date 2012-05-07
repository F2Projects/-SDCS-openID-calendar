package com.login;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.login.data.Event;
import com.login.data.UsersDB;

/**
 * Servlet implementation class SaveEventServlet
 */
@WebServlet("/SaveEventServlet")
@SuppressWarnings("deprecation")
public class SaveEventServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveEventServlet() {
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
		Event submittedEvent = new Event();
		submittedEvent.setSubject(request.getParameter("subject"));
		submittedEvent.setComment(request.getParameter("comments"));
		Date examDate = new Date();
		examDate.setDate(Integer.parseInt(request.getParameter("day")));
		examDate.setYear(Integer.parseInt(request.getParameter("year")));
		examDate.setMonth(Integer.parseInt(request.getParameter("mounth")));
		submittedEvent.setDate(examDate);
		
		String message = UsersDB.getDb().saveEvent(submittedEvent);
		request.setAttribute("saveStatus", message);
        request.getRequestDispatcher("/profile.jsp").forward(request, response);
	}

}
