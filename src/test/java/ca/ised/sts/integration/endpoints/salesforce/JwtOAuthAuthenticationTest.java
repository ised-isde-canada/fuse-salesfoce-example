package ca.ised.sts.integration.endpoints.salesforce;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.annotation.DirtiesContext;
import ca.ised.sts.integration.BaseTest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;


public class JwtOAuthAuthenticationTest extends BaseTest{

	@Spy
	private JwtOAuthAuthentication jwtOAuthAuthenticationSpy;

	@Autowired
	private JwtOAuthAuthentication jwtOAuthAuthentication;

	@Mock
	HttpURLConnection connection;

	@Value("${sf_sts_connected_app_username}")
	private String SF_CONNECTED_APP_USERNAME;

	@DirtiesContext
	@Test
	public void getJWTtokenTest() {

		String jwtToken = jwtOAuthAuthentication.getJWTtoken();

		Jws<Claims> claims = decodeJWT(jwtToken);
		// Test a couple properties to make sure the token is created properly
		assertEquals("RS256", claims.getHeader().get("alg"));
		assertEquals(SF_CONNECTED_APP_USERNAME, claims.getBody().get("sub"));
	}

	private Jws<Claims> decodeJWT(String jwt) {
		// This line will throw an exception if it is not a signed JWS (as expected)
		Jws<Claims> claims = Jwts.parser().setSigningKey(jwtOAuthAuthentication.getPrivateKey()).parseClaimsJws(jwt);
		return claims;
	}

	@DirtiesContext
	@Test
	public void getAuthTokenTest() throws IOException {
		String authCodeString = "Test Auth Code";
		String authTokenJson = "{\"access_token\":\"" + authCodeString
				+ "\",\"scope\":\"web openid api\",\"instance_url\":\"https://null\",\"id\":\"https://test.salesforce.com/id/00D4g0000008bG6EAI/0055W000000JExjQAG\",\"token_type\":\"Bearer\"}";
		InputStream authCodeStream = new ByteArrayInputStream(authTokenJson.getBytes());

		String jwtToken = jwtOAuthAuthentication.getJWTtoken();
		URL url = new URL(
				"https://null/services/oauth2/token?grant_type=urn:ietf:params:oauth:grant-type:jwt-bearer&assertion="
						+ jwtToken);

		doReturn(connection).when(jwtOAuthAuthenticationSpy).createConnection(url);
		doReturn(200).when(connection).getResponseCode();
		doReturn(authCodeStream).when(connection).getInputStream();

		String authToken = jwtOAuthAuthenticationSpy.getAuthToken(jwtToken);
		assertEquals(authCodeString, authToken);
	}
}
