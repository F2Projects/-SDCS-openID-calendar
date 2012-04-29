package com.login.data;

public class UsersDB {
	
	private static UsersDB mySelf;
	
	private UsersDB(){
		
	}
	
	public User getAnUser(String username){
		User loggedUser = new User();
		loggedUser.setName(username);
		loggedUser.setSurname("boemio");
		loggedUser.setUsername(username);
		loggedUser.setPassword("boemio");
		loggedUser.setRole("Teacher");
		
		return loggedUser;
	}
	
	public static UsersDB getDb(){
		if(UsersDB.mySelf==null)
			UsersDB.mySelf = new UsersDB();
		
		return UsersDB.mySelf;
	}
	
}
