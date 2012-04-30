package com.login.jaascommons;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

public class LoginHolder {
	
	private static LoginContext lc = null;
	private static String username;
	private static String password;
	
	public static LoginContext getContext() throws LoginException{
		if(LoginHolder.lc==null){
			LoginCallBackHandler loginCB = new LoginCallBackHandler(LoginHolder.username, LoginHolder.password);
			LoginHolder.lc = new LoginContext("SDSCLogin", loginCB);
		}
		
		return LoginHolder.lc;
	}
	
	public static void setUsername(String username){
		LoginHolder.username = username;
		
	}
	
	public static void setPassword(String password){
		LoginHolder.password = password;
		
	}
	
	public static void cleanContext(){
		LoginHolder.username=null;
		LoginHolder.password=null;
		LoginHolder.lc=null;
	
	}

}
