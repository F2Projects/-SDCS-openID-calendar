package com.test.openid;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openid4java.consumer.ConsumerException;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.discovery.DiscoveryException;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.MessageException;
import org.openid4java.message.ParameterList;
import org.openid4java.message.ax.FetchRequest;

/**
 * Servlet implementation class OpenIDTestServlet
 */
@WebServlet(description = "A test class for OpenID using as relay server the Google provider", urlPatterns = { "/OpenIDTestServlet" })
@SuppressWarnings("rawtypes")
public class OpenIDTestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OpenIDTestServlet() {
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
        final String discoveryTarget = "https://www.google.com/accounts/o8/id";
        String returnToUrl = "http://localhost:8080/";
		PrintWriter outputWriter = response.getWriter();
		//initializing response page
		outputWriter.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
		outputWriter.println("<html>");
		outputWriter.println("<head>");
		outputWriter.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
		outputWriter.println("<title>Test OpenID</title>");
		outputWriter.println("</head>");
		outputWriter.println("<body>");
        
		// instantiate a ConsumerManager object
		ConsumerManager manager = new ConsumerManager();
		try {
			// retrieving Google open id provider informations  
			List discoveries = manager.discover(discoveryTarget);
			
			// printing them on the output webpage
			for (Object d : discoveries)
				outputWriter.println(d.toString() +"<br>");
			
			// encapsulating this informations to use them with the login request
			DiscoveryInformation discovered = manager.associate(discoveries);
			// setting the discovered informations as attribute of the current session
			request.getSession().setAttribute("openid-disc", discovered);
			// generating an authentication request
			AuthRequest authReq = manager.authenticate(discovered, returnToUrl);
			FetchRequest fetch = FetchRequest.createFetchRequest(); // additional attributes
			fetch.addAttribute("email", "http://axschema.org/contact/email", true);
			fetch.addAttribute("language", "http://axschema.org/pref/language", true);
			fetch.addAttribute("firstname", "http://axschema.org/namePerson/first", true);
			fetch.addAttribute("lastname", "http://axschema.org/namePerson/last", true);
			fetch.addAttribute("country", "http://axschema.org/contact/country/home", true);
			authReq.addExtension(fetch);
			
			// printing parameters associated to the current request
			ParameterList parameterList = new ParameterList(authReq.getParameterMap());
			for (Object p : parameterList.getParameters())
				outputWriter.println(p +"<br>");
			
			// print destination url to submit the authentication request
			outputWriter.println("<br><a href=\"" + authReq.getDestinationUrl(true) + "\">authenticate here!</a>");
		} catch (DiscoveryException e) {
			outputWriter.println(e.getMessage());
		} catch (MessageException e) {
			outputWriter.println(e.getMessage());
		} catch (ConsumerException e) {
			outputWriter.println(e.getMessage());
		}
		
		// finalizing response page
		outputWriter.println("</body>");
		outputWriter.println("</html>");
		
		outputWriter.flush();
		outputWriter.close();
		
	}

}
