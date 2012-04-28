package servLogin;

import javax.servlet.*;  
import javax.servlet.http.*;  
  
import javax.security.auth.login.LoginContext;  
import javax.security.auth.login.LoginException;  
import javax.security.auth.callback.CallbackHandler;  
  
public class Loginservlet extends HttpServlet {  
  
  public void doGet(HttpServletRequest request,  
    HttpServletResponse response)  
      throws ServletException, java.io.IOException {  
  
      //The CallbackHandler gets the username and password from  
      //request parameters in the URL; therefore, the ServletRequest is  
      //passed to the CallbackHandler constructor  
      WebCallBackHandler webcallback = new WebCallBackHandler(request);
  
      LoginContext lcontext = null;  
  
      boolean loginSuccess = true;  
  
  
      try{  
  
          lcontext = new LoginContext( "WebLogin",webcallback );
  
          //this method throws a LoginException  
          //if authentication is unsuccessful  
          lcontext.login( );  
  
      } catch (LoginException lge){  
  
          loginSuccess = false;  
  
      }  
  
          response.setContentType("text/html");  
  
          java.io.PrintWriter out = response.getWriter( );  
  
          out.println(  
          "<html><head><title>Thanks for logging in</title>"+  
          "</head><body>");  
  
          out.println("<h2>Your logged in status</h2>");  
  
          out.println(""+ ( loginSuccess ? "Logged in" :  
            "Failed Login" ));  
  
          out.println("</body></html>");  
  
  } //doGet  
  
  public void doPost(HttpServletRequest request,  
       HttpServletResponse response) throws ServletException,  
       java.io.IOException {  
  
      doGet(request,response);  
  
  } //doPost  
  
} //LoginServlet  
