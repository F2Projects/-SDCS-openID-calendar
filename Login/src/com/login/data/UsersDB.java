package com.login.data;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class UsersDB {
	
	private static UsersDB mySelf;
	
	private UsersDB(){
		
	}
	
	public User getAnUser(String username) throws IOException{
		String sql = "select " + USERS_TABLE.NAME_ID + ", " + 
								 USERS_TABLE.SURNAME_ID + ", " + 
								 USERS_TABLE.USERNAME_ID + ", " + 
								 USERS_TABLE.PASSWORD_ID + ", " +
								 ROLES.ROLE_ID + 
								 " from " + USERS_TABLE.TABLE_NAME + ", " + ROLES.TABLE_NAME + 
								 " where " + USERS_TABLE.ROLE_ID + " = " + ROLES.ID +
								 " and " + USERS_TABLE.USERNAME_ID + " = '" + username + "'"; 
				
		User loggedUser;
		Statement stmt=null;
		Connection conn=null;
		try {
			conn = this.getConnectionToDS();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(!rs.next())
				throw new IOException("The username was not successfully authenticated");
			
			loggedUser = new User();
			loggedUser.setName(rs.getString(USERS_TABLE.NAME_ID));
			loggedUser.setSurname(rs.getString(USERS_TABLE.SURNAME_ID));
			loggedUser.setUsername(rs.getString(USERS_TABLE.USERNAME_ID));
			loggedUser.setPassword(rs.getString(USERS_TABLE.PASSWORD_ID));
			loggedUser.setRole(rs.getString(ROLES.ROLE_ID));
		} catch (NamingException e) {
			throw new IOException(e);
		} catch (SQLException e) {
			throw new IOException(e);
		} finally{
			this.close(conn, stmt);
		}
		
		return loggedUser;
	}
	
	public String saveNewUser(User newUser){
		String sql = "insert into " + USERS_TABLE.TABLE_NAME + " values('" +
														newUser.getUsername() + "', '" + 
														newUser.getName() + "', '" +
														newUser.getSurname() + "', '" + 
														newUser.getPassword() + "', " +
														Integer.parseInt(newUser.getRole()) + ")";
		
		try {
			Connection conn = this.getConnectionToDS();
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			return "User not saved: " + e.getMessage();
		} catch (NamingException e) {
			return "User not saved!";
		}
		
		return newUser.getName() + " " + newUser.getSurname() + " added as " + newUser.getUsername();
	
	}
	
	public static UsersDB getDb(){
		if(UsersDB.mySelf==null)
			UsersDB.mySelf = new UsersDB();
		
		return UsersDB.mySelf;
	}
	
	private Connection getConnectionToDS() throws NamingException, SQLException{
		Context env = (Context)new InitialContext().lookup("java:comp/env");
		DataSource ds = (DataSource)env.lookup("jdbc/SdcsDb");
		
		return ds.getConnection();
	}
	
	private void close(Connection c, Statement s){
		try {
			s.close();
			c.close();
		} catch (Exception e) {}
		
	}
	
}
