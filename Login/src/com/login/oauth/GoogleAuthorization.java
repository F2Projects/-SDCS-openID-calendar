package com.login.oauth;

import java.io.IOException;
import java.util.Arrays;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets.Details;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.calendar.CalendarScopes;


public class GoogleAuthorization {
	
	private final String CLIENT_ID = "475470206103.apps.googleusercontent.com";
	private final String CIENT_SECRET = "4IY3VUmu13z5ri3w3S_fhRNW";
	
	private String redirectUrl;
	
	// this is the object used to follow the sequence of steps to follow to accomplish a successful authorization
	private GoogleAuthorizationCodeFlow flow=null;
	private Credential credential;
	
	public String getAuthorizationUrl(String redirectUri){
		this.redirectUrl = redirectUri;
		
		// here were generated the holder for the secrets of the current application
		GoogleClientSecrets clientSecrets = new GoogleClientSecrets(); 
		Details details = new Details();
		details.setClientId(this.CLIENT_ID);
		details.setClientSecret(this.CIENT_SECRET);
		clientSecrets.setWeb(details);
		
		// building a new authorization flow
		this.flow = new GoogleAuthorizationCodeFlow.Builder(new NetHttpTransport(), new JacksonFactory(), 
										clientSecrets, Arrays.asList(CalendarScopes.CALENDAR)).build();
		
		return this.flow.newAuthorizationUrl().setRedirectUri(redirectUri).build();
	}
	
	public void genCredential(String code) throws IOException{
		GoogleTokenResponse response = flow.newTokenRequest(code).setRedirectUri(this.redirectUrl).execute();
		this.credential = this.flow.createAndStoreCredential(response, null);
		
	}
	
	public Credential getCredential(){
		return this.credential;
		
	}
	
}
