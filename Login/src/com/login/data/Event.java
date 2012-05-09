package com.login.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Event {
	private String subject;
	private int day;
	private int mounth;
	private int year;
	private String comment;
	
	public Event(){
		subject="";
		day=0;
		mounth=0;
		year=0;
		comment="";
	}
	
	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * @return the day
	 */
	public int getDay() {
		
		return this.day;
	}
	/**
	 * @param sqlDate the date to set
	 */
	public void setDay(int rDay) {
		this.day = rDay;
	}
	public void setMounth(int rMounth) {
		this.mounth = rMounth;
	}
	public void setYear(int rYear) {
		this.year = rYear;
	}
	/**
	 * @return the mounth
	 */
	public int getMounth() {
		
		return this.mounth;
	
	}
	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}
	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	/**
	 * @return the year
	 */
	public int getYear() {
		return this.year;
	}
	
	public Date getDate() throws ParseException{
		String day = this.day<9 ? "0" + new Integer(this.day).toString() : new Integer(this.day).toString();
		String year = new Integer(this.year).toString();
		String mounth = this.mounth<9 ? "0" + new Integer(this.mounth).toString() : new Integer(this.mounth).toString();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy"); 
		
		return sdf.parse(day+mounth+year);
	}
	
}
