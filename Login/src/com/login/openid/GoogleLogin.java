/**
 * GoogleLogin.java
 * -----------------
 * Here's, just for you, the class everyone wants to see.
 * With it you can generate the special Google OpenID url,
 * check if the Google login procedure terminated successfully
 * and, last but not least, you can get a special data object containing
 * the information of the current logged in user.
 * Store it as a session attribute, so you can get it in every part of your web app
 * and check in every moment if the user is logged in.
 */
package com.login.openid;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import org.openid4java.consumer.ConsumerManager;
import org.openid4java.consumer.VerificationResult;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.discovery.Identifier;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.AuthSuccess;
import org.openid4java.message.ParameterList;
import org.openid4java.message.ax.AxMessage;
import org.openid4java.message.ax.FetchRequest;
import org.openid4java.message.ax.FetchResponse;

import com.login.data.User;

@SuppressWarnings("rawtypes")
public class GoogleLogin {
	
	private ConsumerManager manager;
	private DiscoveryInformation discovered;
	private User loggedUser;
	
	/**
	 * As explained into the Google OpenID API, in order to accomplish a login procedure you 
	 * have to request a special url to the Google provider.
	 * In the next step you have to redirect your browser to this url and let Google
	 * do the dirty work ;) 
	 * 
	 * @param redirectUrl is the url where, after that the login procedure is terminated, your browser will redirected
	 * @return the special url generated only for you by the Google provider
	 * @throws ServletException
	 */
	public String genLoginUrl(String redirectUrl) throws ServletException{
		final String discoveryTarget = "https://www.google.com/accounts/o8/id";
		
        AuthRequest authReq = null;
        // instantiate a ConsumerManager object
		this.manager = new ConsumerManager();
		try {
			// retrieving Google open id provider informations  
			List discoveries = this.manager.discover(discoveryTarget);
			
			// encapsulating this informations to use them with the login request
			this.discovered = this.manager.associate(discoveries);
			// generating an authentication request
			authReq = this.manager.authenticate(discovered, redirectUrl);
			FetchRequest fetch = FetchRequest.createFetchRequest(); // additional attributes
			fetch.addAttribute("email", "http://axschema.org/contact/email", true);
			fetch.addAttribute("language", "http://axschema.org/pref/language", true);
			fetch.addAttribute("firstname", "http://axschema.org/namePerson/first", true);
			fetch.addAttribute("lastname", "http://axschema.org/namePerson/last", true);
			fetch.addAttribute("country", "http://axschema.org/contact/country/home", true);
			authReq.addExtension(fetch);
			
			
		} catch (Exception e) {
			throw new ServletException(e);
		} 
		
		// get url to redirect your login request
		return authReq.getDestinationUrl(true);
	}
	
	
	/**
	 * After you have generated a valid url invoking the genLoginUrl() and 
	 * have redirect your browser to it, now you can invoke this method to check
	 * if the procedure is finished successfully 
	 * 
	 * @param receivingURL is the url returned after the "Google redirect". Extract it from your request attributes
	 * @param parameters contained into the request object (extract them throw request.getParameterMap())
	 * @return a boolean variable containing the result of the procedure
	 * @throws IOException
	 */
	public boolean checkLogin(String receivingURL, ParameterList parameters) throws IOException{
		
		try {
			// VerificationResult is an object used to check the result of a login procedure
			VerificationResult verification = this.manager.verify(receivingURL, parameters, this.discovered);
			// a successful login has a valid identifier
			Identifier verified = verification.getVerifiedId();
			if (verified != null){
				// if the user accepts the conditions, in the response there are some additional attributes 
                AuthSuccess authSuccess = (AuthSuccess) verification.getAuthResponse();
                this.loggedUser = new User();
                if (authSuccess.hasExtension(AxMessage.OPENID_NS_AX)){
                    FetchResponse fetchResp = (FetchResponse) authSuccess.getExtension(AxMessage.OPENID_NS_AX);
                    this.loggedUser.setName((String)fetchResp.getAttributeValues("firstname").get(0));
                    this.loggedUser.setSurname((String)fetchResp.getAttributeValues("lastname").get(0));
                    this.loggedUser.setUsername((String)fetchResp.getAttributeValues("email").get(0));
                }
                return true; // all done, we can give the good news :)
			}
		} catch (Exception e) {
			throw new IOException(e);
		}
		
		return false;
		
	}
	
	/**
	 * This method is used to get the current logged in user.
	 * It is generated during a successful login procedure accomplished 
	 * invoking genLoginUrl() before of checkLogin() 
	 * 
	 * @return the currently logged in user
	 */
	public User getLoggedUser(){
		// simply return it 
		return this.loggedUser;
	
	}
	
	

}
