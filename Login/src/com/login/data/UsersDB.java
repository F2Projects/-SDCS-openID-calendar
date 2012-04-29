package com.login.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.security.auth.login.FailedLoginException;
import javax.sql.DataSource;


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
	
	public String saveNewUser(User newUser){
		
		return newUser.getName() + " " + newUser.getSurname() + " added as " + newUser.getUsername();
	}
	
	public String getPassword(String username) throws FailedLoginException, Exception{
		String sql = "select * from " + PASSWORD_TABLE.TABLE_NAME + " where " + PASSWORD_TABLE.USERNAME_ID + "='" + username + "'";
		
		Connection conn = this.getConnectionToDS();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		if(!rs.next())
			throw new FailedLoginException("The username was not successfully authenticated");
		
		return rs.getString(PASSWORD_TABLE.PASSWORD_ID);
		
	}
	
	public static UsersDB getDb(){
		if(UsersDB.mySelf==null)
			UsersDB.mySelf = new UsersDB();
		
		return UsersDB.mySelf;
	}
	
	private Connection getConnectionToDS() throws NamingException, SQLException{
		Context env = (Context)new InitialContext().lookup("java:comp/env");
		DataSource ds = (DataSource)env.lookup("jdbc/upl_db");
		
		return ds.getConnection();
	}
	
}
