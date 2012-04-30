package com.login.jaascommons;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

public class LoginHolder {
	
	private static LoginContext lc = null;
	
	public static LoginContext getContext(String username, String password) throws LoginException{
		if(LoginHolder.lc==null){
			LoginCallBackHandler loginCB = new LoginCallBackHandler(username, password);
			LoginHolder.lc = new LoginContext("SDSCLogin", loginCB);
		}
		
		return LoginHolder.lc;
	}
	
	public static void cleanContext(){
		LoginHolder.lc=null;
	
	}

}
