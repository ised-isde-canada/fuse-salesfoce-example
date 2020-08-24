package ca.ised.sts.integration.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AuthTokenResposeTest {

	@Test
	public void getSetTest() {
		String access_token = "TOKEN";
		String id = "ID";
		String instance_url = "URL";
		String scope = "SCOPE";
		String token_type = "TYPE";
		
		AuthTokenResponse authTokenResponse = new AuthTokenResponse();
		authTokenResponse.setAccess_token(access_token);
		authTokenResponse.setId(id);
		authTokenResponse.setInstance_url(instance_url);
		authTokenResponse.setScope(scope);
		authTokenResponse.setToken_type(token_type);
		
		assertEquals(access_token, authTokenResponse.getAccess_token());
		assertEquals(id, authTokenResponse.getId());
		assertEquals(instance_url, authTokenResponse.getInstance_url());
		assertEquals(scope, authTokenResponse.getScope());
		assertEquals(token_type, authTokenResponse.getToken_type());
	}
}
