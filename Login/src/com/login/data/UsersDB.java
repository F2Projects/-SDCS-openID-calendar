/**
 * UsersDB.java
 * -----------------------
 * This is the class that is used to access to the local db.
 * It is written using the "Singleton Patter", so all the access to the db are serialized
 * (using synchronized methods) using only one instance of the current class.
 * Respecting the pattern's guidelines, the constructor is private, and the instance of the class
 * is stored into the static field "mySelf".
 * In the end, to access to the currently active instance of the class, the implementer must use the
 * method "getDb()"
 */
package com.login.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.login.data.tables.AGENDA;
import com.login.data.tables.ROLES;
import com.login.data.tables.SUBJECTS;
import com.login.data.tables.USERS_TABLE;


public class UsersDB {
	
	private static UsersDB mySelf;
	
	/**
	 * The constructor of the class.
	 * It does nothing, is only private.
	 */
	private UsersDB(){}
	
	/**
	 * This is the method that executes a query on the database and extracts the role associated to a given username.
	 * If it exists into the local database, it is returned to the calling class as a string,
	 * otherwise it throws a generic exception.
	 * 
	 * @param username is the username to use as search key into the database
	 * @return the role as a string
	 * @throws Exception
	 */
	public synchronized String getUserRole(String username) throws Exception{
		// this is the query, written in sql language, used to extract the role information for the user having "username" username :)
		String sql = "select " + ROLES.TABLE_NAME + "." + ROLES.ROLE_ID + 
								 " from " + USERS_TABLE.TABLE_NAME + ", " + ROLES.TABLE_NAME + 
								 " where " + USERS_TABLE.TABLE_NAME + "." + USERS_TABLE.ROLE_ID + " = " + ROLES.TABLE_NAME + "." + ROLES.ID +
								 " and " + USERS_TABLE.TABLE_NAME + "." + USERS_TABLE.USERNAME_ID + " = '" + username + "'"; 
		
		String userRole = null;
		
		Statement stmt=null;
		Connection conn=null;
		try {
			// connecting to the database
			conn = this.getConnectionToDS();
			stmt = conn.createStatement();
			// executing the sql query
			ResultSet rs = stmt.executeQuery(sql);
			// if nothing is found
			if(!rs.next())
				// an exception is raised
				throw new Exception("The username was not successfully authenticated");
			// else 
			userRole = rs.getString(ROLES.ROLE_ID);
		} catch (NamingException e) {
			throw new Exception(e);
		} catch (SQLException e) {
			throw new Exception(e);
		} finally{
			this.close(conn, stmt);
		}
		
		return userRole;
	}
	
	/**
	 * This is the method used to save a new user into the db.
	 * If all is done, then a message of confirm is returned back,
	 * otherwise is returned a message containing the exception.
	 * 
	 * @param newUser is the data structure containing the current user informations
	 * @return a message with the response of the operation
	 */
	public synchronized String saveNewUser(User newUser){
		// this is the query, written in sql language, used to insert a new user into the local database
		String sql = "insert into " + USERS_TABLE.TABLE_NAME + " values('" +
														newUser.getUsername() + "', " + 
														Integer.parseInt(newUser.getRole()) + ")";
		
		try {
			// connecting to the database
			Connection conn = this.getConnectionToDS();
			Statement stmt = conn.createStatement();
			// executing the sql query
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			// Oh oh.. something goes wrong...
			return "User not saved: " + e.getMessage();
		}
		
		// All done, bro!
		return newUser.getName() + " " + newUser.getSurname() + " added as " + newUser.getUsername();
	
	}
	
	/**
	 * This method is used to extract the list of the events stored on the database.
	 * It executes the query and return the result.. What you expected that made?
	 * 
	 * @return 
	 * @throws Exception
	 */
	public synchronized ArrayList<Event> getEventList() throws Exception{
		String sql = "select " + SUBJECTS.TABLE_NAME + "." + SUBJECTS.NAME_ID + ", " +
								 AGENDA.TABLE_NAME + "." + AGENDA.DAY_ID + ", " +
								 AGENDA.TABLE_NAME + "." + AGENDA.MOUNTH_ID + ", " +
								 AGENDA.TABLE_NAME + "." + AGENDA.YEAR_ID + ", " +
								 AGENDA.TABLE_NAME + "." + AGENDA.COMMENTS_ID + 
								 " from " + SUBJECTS.TABLE_NAME + ", " + AGENDA.TABLE_NAME +
								 " where " + AGENDA.TABLE_NAME + "." + AGENDA.EXAM_NAME_ID + "=" + SUBJECTS.TABLE_NAME + "." + SUBJECTS.ID;
		
		ArrayList<Event> eventList = new ArrayList<Event>();
		Statement stmt=null;
		Connection conn=null;
		try {
			// connecting to the database
			conn = this.getConnectionToDS();
			stmt = conn.createStatement();
			// executing the sql query
			ResultSet rs = stmt.executeQuery(sql);
			// add found events to the list
			while(rs.next()){
				Event currentEvent = new Event();
				currentEvent.setSubject(rs.getString(SUBJECTS.NAME_ID));
				currentEvent.setComment(rs.getString(AGENDA.COMMENTS_ID));
				currentEvent.setDay(rs.getInt(AGENDA.DAY_ID));
				currentEvent.setMounth(rs.getInt(AGENDA.MOUNTH_ID));
				currentEvent.setYear(rs.getInt(AGENDA.YEAR_ID));
				eventList.add(currentEvent);
			}
		} catch (NamingException e) {
			throw new Exception(e);
		} catch (SQLException e) {
			throw new Exception(e);
		} finally{
			this.close(conn, stmt);
		}
		
		return eventList;
	}
	
	/**
	 * Another method that does query on database. This time it stores the informations the theacher inserts into the 
	 * relative form.
	 * 
	 * @param newEvent
	 * @return
	 */
	public synchronized String saveEvent(Event newEvent){
		// this is the query, written in sql language, used to insert a new user into the local database
		String sql = "insert into " + AGENDA.TABLE_NAME + " values(null, '" +
													newEvent.getSubject() + "', '" + 
													newEvent.getComment() + "', " + 
													newEvent.getDay() + ", " + newEvent.getMounth() + ", " + newEvent.getYear() + ")";
		
		try {
			// connecting to the database
			Connection conn = this.getConnectionToDS();
			Statement stmt = conn.createStatement();
			// executing the sql query
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			// Oh oh.. something goes wrong...
			return "Event not saved: " + e.getMessage();
		}
		
		// All done, bro!
		return newEvent.getSubject() + " exam added!";
	}
	
	/**
	 * And now, the last method that performs query. It extracts the list of the subjects
	 * previously stored on the database.
	 * 
	 * @return
	 * @throws Exception
	 */
	public synchronized ArrayList<Subject> getSubjectList() throws Exception{
		String sql = "select * from " + SUBJECTS.TABLE_NAME; 
		
		ArrayList<Subject> subjectList = new ArrayList<Subject>();
		Statement stmt=null;
		Connection conn=null;
		try {
			// connecting to the database
			conn = this.getConnectionToDS();
			stmt = conn.createStatement();
			// executing the sql query
			ResultSet rs = stmt.executeQuery(sql);
			// add found events to the list
			while(rs.next()){
				Subject retreivedSubject = new Subject();
				retreivedSubject.setAcronim(rs.getString(SUBJECTS.ID));
				retreivedSubject.setName(rs.getString(SUBJECTS.NAME_ID));
				subjectList.add(retreivedSubject);
			}
		} catch (NamingException e) {
			throw new Exception(e);
		} catch (SQLException e) {
			throw new Exception(e);
		} finally{
			this.close(conn, stmt);
		}
		
		return subjectList;
	}
	
	/**
	 * This is the getter of this class. 
	 * It exists because this class implements the singleton pattern, do you remember?
	 * I'd told to you before.. there.. above.. in the top...
	 * 
	 * @return
	 */
	public static UsersDB getDb(){
		// if no one have already allocated this class
		if(UsersDB.mySelf==null)
			// allocate it
			UsersDB.mySelf = new UsersDB();
		
		// and now GIMME THE CLASS..
		return UsersDB.mySelf;
	}
	
	/**
	 * This is the inner method that performs a connection to the local database.
	 * The JNDI name are setted locally to the application server.
	 * This configuration can be enforced using a properties file, but I don't want :S
	 * 
	 * @return the connection object to the database
	 * @throws NamingException
	 * @throws SQLException
	 */
	private Connection getConnectionToDS() throws NamingException, SQLException{
		Context env = (Context)new InitialContext().lookup("java:comp/env");
		DataSource ds = (DataSource)env.lookup("jdbc/SdcsDb");
		
		return ds.getConnection();
	}
	
	/**
	 * This is the inner method used to close the statement and the current connection to the db.
	 * Why I have done a method to do this.
	 * Because I really don't like a try-catch statement into the finally statement (required into the calling method)
	 * so... here you are :)  
	 * 
	 * @param c is the current connection
	 * @param s is the current statement
	 */
	private void close(Connection c, Statement s){
		try {
			s.close();
			c.close();
		} catch (Exception e) {}
		
	}
	
}
