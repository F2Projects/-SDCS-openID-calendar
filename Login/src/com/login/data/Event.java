package com.login.data;

import java.util.Calendar;
import java.util.Date;

public class Event {
	private String subject;
	private Calendar date;
	private String comment;
	
	public Event(){
		subject="";
		date=Calendar.getInstance();
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
		return this.date.get(Calendar.DAY_OF_MONTH);
	}
	/**
	 * @param sqlDate the date to set
	 */
	public void setDate(Date sqlDate) {
		this.date.setTime(sqlDate);
	}
	/**
	 * @return the mounth
	 */
	public int getMounth() {
		return this.date.get(Calendar.MONTH);
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
		return this.date.get(Calendar.YEAR);
	}
}
