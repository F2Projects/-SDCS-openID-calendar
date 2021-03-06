/**
 * User.java
 * -----------------------
 * This class implements a data structure that holds the informations for an user.
 * It is useful because user's informations are stored into the local db and into the remote Google db,
 * so it can give to the implementer an unified vision of them.
 * The methods are simply getter/setter functions for each inner field.
 */
package com.login.data;

public class User {
	private String name;
	private String surname;
	private String username;
	private String password;
	private String role;
	
	public User(){
		this.name="";
		this.surname="";
		this.username="";
		this.password="";
		this.role="";
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
}
