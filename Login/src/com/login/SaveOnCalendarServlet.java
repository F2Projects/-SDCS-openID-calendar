package com.login;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Calendar;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.login.oauth.GoogleAuthorization;

/**
 * Servlet implementation class SaveOnCalendarServlet
 */
@WebServlet("/SaveOnCalendarServlet")
public class SaveOnCalendarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveOnCalendarServlet() {
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
		GoogleAuthorization authorizationManager = (GoogleAuthorization) request.getSession().getAttribute("authorizationManager");
		
		String receivedCode = request.getParameter("code");
		
		if(authorizationManager.getCredential()==null && receivedCode==null){
			request.getSession().setAttribute("authorizationManager", null);
			request.setAttribute("saveStatus", "You have not conclused the previous transaction. We have cleaned up the session, try now ;)");
		}else {
			if (authorizationManager.getCredential()==null) 
				authorizationManager.genCredential(receivedCode);
			request.setAttribute("saveStatus", this.googleLogic(request, authorizationManager));
		}
	    
	    request.getSession().setAttribute("eventToSave", null);
        request.getRequestDispatcher("/profile.jsp").forward(request, response);

		
	}
	
	private String googleLogic(HttpServletRequest request, GoogleAuthorization authorizationManager) throws IOException{
		com.google.api.services.calendar.Calendar client = com.google.api.services.calendar.Calendar.builder(new NetHttpTransport(), new JacksonFactory()).
				setApplicationName("sdcs_calendar").setHttpRequestInitializer(authorizationManager.getCredential()).build();

		String uninaCalendarId=null;
		
		CalendarList userCalendars = client.calendarList().list().execute();
		for (CalendarListEntry retreivedCalendar : userCalendars.getItems()){
			if(retreivedCalendar.getSummary().equals("UniNa Calendar"))
					uninaCalendarId=retreivedCalendar.getId();
		}
		
		if(uninaCalendarId==null){
			Calendar uninaCalendarEntry = new Calendar();
			uninaCalendarEntry.setSummary("UniNa Calendar");
			uninaCalendarId = client.calendars().insert(uninaCalendarEntry).execute().getId();
		}
		
		
		com.login.data.Event eventToSave = (com.login.data.Event)request.getSession().getAttribute("eventToSave");
		
		Event event = new Event();
		
		event.setSummary(eventToSave.getSubject());
		
		Date startDate;
		Event insertedEvent;
		try {
			startDate = eventToSave.getDate();
			DateTime start = new DateTime(startDate, TimeZone.getTimeZone("UTC"));
			event.setStart(new EventDateTime().setDateTime(start));
			
			Date endDate = new Date(startDate.getTime() + 24*3600000);
			DateTime end = new DateTime(endDate, TimeZone.getTimeZone("UTC"));
			event.setEnd(new EventDateTime().setDateTime(end));
			
			insertedEvent = client.events().insert(uninaCalendarId, event).execute();
		} catch (ParseException e) {
			return "Error: " + e.getMessage();
		}
			
		return "Saved event with id: " + insertedEvent.getId() + "<br>Date: " + startDate;
		
	}

}
