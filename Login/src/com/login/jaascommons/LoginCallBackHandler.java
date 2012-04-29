package com.login.jaascommons;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

public class LoginCallBackHandler implements CallbackHandler{

	private String username;
	private String password;
	
	public LoginCallBackHandler(String username, String password){
		this.username = username;
		this.password = password;
	}
	
	@Override
	public void handle(Callback[] callbacks) throws IOException,UnsupportedCallbackException {
		for (Callback c : callbacks){
			if(c instanceof NameCallback)
				((NameCallback) c).setName(this.username);
			else if(c instanceof PasswordCallback)
				((PasswordCallback) c).setPassword(this.password.toCharArray());
			else
				throw new UnsupportedCallbackException(c, "The CallBacks are unrecognized in class: " + getClass().getName());
			
		}
		
		
	}

}
