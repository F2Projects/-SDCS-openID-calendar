package com.login.jaascommons;

import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import com.login.data.UsersDB;

@SuppressWarnings("rawtypes")
public class DataSource implements LoginModule {

	private boolean loginPassed = false;
	private CallbackHandler handler;
	
	@Override
	public boolean abort() throws LoginException {
		boolean bool = loginPassed;  
	    loginPassed = false;  
	    
	    return bool;
	}

	@Override
	public boolean commit() throws LoginException {
		return loginPassed;
	}

	@Override
	public void initialize(Subject subject, CallbackHandler handler, Map sharedState, Map options) {
		this.handler = handler;
	}

	@Override
	public boolean login() throws LoginException {
		UsersDB db = UsersDB.getDb();
		Callback[] callbacks = new Callback[]{ new NameCallback("Username:"), new PasswordCallback("Password:", false) };
		try {
			this.handler.handle(callbacks);
			String username = ((NameCallback)callbacks[0]).getName();
			String password = new String(((PasswordCallback)callbacks[1]).getPassword());
			String foundPassword = db.getPassword(username);
			if(password.equals(foundPassword))
				this.loginPassed = true;
			else{
				this.loginPassed = false;
				throw new FailedLoginException("The password was not successfully authenticated");
			}
		} catch(FailedLoginException e){
			loginPassed = false;  
            throw e; 
		} catch (Exception e) {
			this.loginPassed = false;
			throw new LoginException();
			
		}
		
		return true;
	}

	@Override
	public boolean logout() throws LoginException {
		loginPassed = false;  
	    return true; 
	
	}

}
