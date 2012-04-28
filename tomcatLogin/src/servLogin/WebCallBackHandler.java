package servLogin;

import javax.security.auth.callback.*;  
import javax.servlet.ServletRequest;  
  
public class WebCallBackHandler implements CallbackHandler {  
  
    private String userName;  
    private String password;  
  
  public WebCallBackHandler(ServletRequest request){  
  
      userName = request.getParameter("Username");  
      password = request.getParameter("Password");  
  
  }  
  
  public void handle(Callback[] callbacks) throws java.io.IOException,  
    UnsupportedCallbackException {  
  
      //Add the username and password from the request parameters to  
      //the Callbacks  
      for (int i = 0; i < callbacks.length; i++){  
  
          if (callbacks[i] instanceof NameCallback){  
  
              NameCallback nameCall = (NameCallback) callbacks[i];  
  
              nameCall.setName(userName);  
  
          } else if (callbacks[i] instanceof PasswordCallback){  
  
              PasswordCallback passCall = (PasswordCallback) callbacks[i];  
  
              passCall.setPassword(password.toCharArray( ));  
  
          } else{  
  
              throw new UnsupportedCallbackException (callbacks[i],  
                "The CallBacks are unrecognized in class: "+getClass( ).  
                 getName( ));  
  
          }  
  
       } //for  
   } //handle  
  
}  