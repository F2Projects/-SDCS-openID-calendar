/**
 * GoogleAuthorization.java
 * -----------------------------
 * This is the class that gives you the authorization to the Google API,
 * this is the class that chooses your destiny.
 * Authorization preocedure is performed using the OAuth 2.0 algorithm, 
 * which is a standard for Google. In particular the process is divided into 2 steps:
 * fisrt one is achieved by calling getAuthorizationUrl() method that generates the url to the
 * Google's authentication page, and the second one is achieved calling genCredential()
 * method that generates the access credentials to the Google API.
 * In the end, there is also a very little method, the getCredential() method, that
 * offers, to those who require, the credentials previously created.
 */
package com.login.oauth;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets.Details;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.calendar.CalendarScopes;


public class GoogleAuthorization {
	
	// the client's identifier that Google has assigned to me
	private final String CLIENT_ID = "475470206103.apps.googleusercontent.com";
	// this is our secret string.. schhhh!
	private final String CIENT_SECRET = "4IY3VUmu13z5ri3w3S_fhRNW";
	// the application scope or, as we like to call it, what the f**k can I do?
	private final List<String> SCOPES = Arrays.asList(CalendarScopes.CALENDAR);
	
	private String redirectUrl;
	
	// this is the object where the OAuth 2.0 algorithm is implemented
	private GoogleAuthorizationCodeFlow flow=null;
	// my pass to the world 
	private Credential credential;
	
	/**
	 * As described into the OAuth 2.0 documentation, the real user must give an explicit consent 
	 * to Google provider in order to release an access token to the web application.
	 * This method generates the special url where the user will be redirected to perform this operation.
	 * 
	 * @param redirectUri
	 * @return
	 */
	public String getAuthorizationUrl(String redirectUri){
		// I save the redirect url (the url where the user will be redirected after that the authentication operation will ended) in a field of the class 
		this.redirectUrl = redirectUri;
		
		// here was generated the holder for the secrets of the current application
		GoogleClientSecrets clientSecrets = new GoogleClientSecrets(); 
		Details details = new Details();
		details.setClientId(this.CLIENT_ID);
		details.setClientSecret(this.CIENT_SECRET);
		clientSecrets.setWeb(details);
		
		// building a new authorization flow
		this.flow = new GoogleAuthorizationCodeFlow.Builder(new NetHttpTransport(), new JacksonFactory(), 
										clientSecrets, this.SCOPES).build();
		
		// and now... here comes your url to authorization page
		return this.flow.newAuthorizationUrl().setRedirectUri(redirectUri).build();
	}
	
	/**
	 * This method executes the second step of OAuth 2.0 algorithm.
	 * After that the user gives to Google the consent to give to me a token,
	 * I receive a so called "Authentication Token", stored into the code variable.
	 * It, with some additional informations, are sent to Google that gives me, finally, 
	 * the "Access Token" and the "Refresh Token". Both are stored into the credential class field
	 * and used every time that the web app sends a request to the Google API.
	 * 
	 * @param code is the received Authentication Token
	 * @throws IOException
	 */
	public void genCredential(String code) throws IOException{
		// ehi.. this is my authentication token, can I have the access token, pleeease?
		GoogleTokenResponse response = flow.newTokenRequest(code).setRedirectUri(this.redirectUrl).execute();
		this.credential = this.flow.createAndStoreCredential(response, null);
		
	}
	
	/**
	 * This method, simply, returns the credential that the user has generated using the methods above.
	 * Please, be careful: if you don't execute correctly the algorithm, this method return a null object
	 * and you can fall in a NullPointerException.
	 * 
	 * @return the credentials
	 */
	public Credential getCredential(){
		return this.credential;
		
	}
	
}
