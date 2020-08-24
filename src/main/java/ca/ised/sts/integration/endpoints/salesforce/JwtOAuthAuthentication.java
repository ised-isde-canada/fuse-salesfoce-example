package ca.ised.sts.integration.endpoints.salesforce;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import ca.ised.sts.integration.exception.EndpointException;
import ca.ised.sts.integration.model.AuthTokenResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.*; 
import java.text.MessageFormat;

import javax.annotation.PostConstruct;  

/**
 * Authorize access to Salesforce using JWT and OAuth
 * 
 * @author Michael Marshall
 *
 */
@Component
public class JwtOAuthAuthentication {
	
	private String CHAR_ENCODING = "UTF-8"; 
	
	@Value("${sf_sts_connected_app_consumer_key}") 
	private String SF_CONNECTED_APP_CONSUMER_KEY;
	
	@Value("${sf_sts_connected_app_username}") 
	private String SF_CONNECTED_APP_USERNAME;
	
	@Value("${sf_login_url_key}") 
	private String SF_LOGIN_URL;
	
	@Value("${sf_sts_endpoint_domain}") 
	private String SF_ENDPOINT_DOMAIN;

	@Value("${sf_sts_connected_app_certificate_password}") 
	private String SF_CONNECTED_APP_CERTIFICATE_PASSWORD;
	
	@Value("${java_keystore_path}") 
	private String JAVA_KEYSTORE_PATH;

	@Value("${java_keystore_password}") 
	private String JAVA_KEYSTORE_PASSWORD;
		
	@Value("${java_keystore_certificate_name}") 
	private String SF_CONNECTED_APP_CERTIFICATE_NAME;
	
	private HttpURLConnection con = null; 
	
	private PrivateKey privateKey;
		
	protected PrivateKey getPrivateKey() {
		return privateKey;
	}

	@PostConstruct  
    public void createPrivateKey(){  
		try {
			//Load the private key from a keystore 
			KeyStore keystore = KeyStore.getInstance("JKS");
			keystore.load(new FileInputStream(JAVA_KEYSTORE_PATH), JAVA_KEYSTORE_PASSWORD.toCharArray());
			privateKey = (PrivateKey) keystore.getKey(SF_CONNECTED_APP_CERTIFICATE_NAME, SF_CONNECTED_APP_CERTIFICATE_PASSWORD.toCharArray());
		}catch(Exception ex) {
			throw new RuntimeException("Error creating private key from Java Key Store. Check path and certificate values. Path: " + JAVA_KEYSTORE_PATH + ", Cert name: " + SF_CONNECTED_APP_CERTIFICATE_NAME, ex);
		}
    }  
	
  	public String getJWTtoken() {
	  	String header = "{\"alg\":\"RS256\"}";
	    String claimTemplate = "'{'\"iss\": \"{0}\", \"sub\": \"{1}\", \"aud\": \"{2}\", \"exp\": \"{3}\"'}'";
	
	    StringBuffer token = new StringBuffer();
	    
	    try {
	
		  //Encode the JWT Header and add it to our string to sign
		  token.append(Base64.encodeBase64URLSafeString(header.getBytes(CHAR_ENCODING)));
		
		  //Separate with a period
		  token.append(".");
		
		  //Create the JWT Claims Object
		  String[] claimArray = new String[4];
		  claimArray[0] = SF_CONNECTED_APP_CONSUMER_KEY;
		  claimArray[1] = SF_CONNECTED_APP_USERNAME;
		  claimArray[2] = SF_LOGIN_URL;
		  claimArray[3] = Long.toString( ( System.currentTimeMillis()/1000 ) + 300);
		  
		  MessageFormat claims;
		  claims = new MessageFormat(claimTemplate);
		  String payload = claims.format(claimArray);
		
		  //Add the encoded claims object
		  token.append(Base64.encodeBase64URLSafeString(payload.getBytes(CHAR_ENCODING)));
		
		  //Sign the JWT Header + "." + JWT Claims Object
		  Signature signature = Signature.getInstance("SHA256withRSA");
		  signature.initSign(privateKey);
		  signature.update(token.toString().getBytes(CHAR_ENCODING));
		  String signedPayLoad = Base64.encodeBase64URLSafeString(signature.sign());
		  
		  //Separate with a period
		  token.append(".");
		
		  //Add the encoded signature
		  token.append(signedPayLoad);
	
	    } catch (Exception e) {
	    	throw new EndpointException("JWT Token creation failed", e);
	    }
	    
	    return token.toString();
	}

  	protected HttpURLConnection createConnection(URL url) throws IOException {
  	    return (HttpURLConnection) url.openConnection();
    }
	public String getAuthToken(String jwtToken) {
		String token = "";
		
		try {
			URL url = new URL("https://" + SF_ENDPOINT_DOMAIN + "/services/oauth2/token?grant_type=urn:ietf:params:oauth:grant-type:jwt-bearer&assertion=" + jwtToken);
			con = createConnection(url);
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
	
			// For POST only - START
			con.setDoOutput(true);
	
			int responseCode = con.getResponseCode();
	
			if (responseCode == HttpURLConnection.HTTP_OK) { //success
				BufferedReader in = new BufferedReader(new InputStreamReader(
						con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();
	
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
	
				ObjectMapper mapper = new ObjectMapper();
			    AuthTokenResponse result = mapper.readValue(response.toString(), AuthTokenResponse.class);
				token = result.getAccess_token();
	
			    
			} else {
				throw new EndpointException("Authentication failed - With response code: " + responseCode);
			}
		}catch(Exception ex) {
			throw new EndpointException("Authentication Token creation failed", ex);
		}
		
		return token;
	}
	
}