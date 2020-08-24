package ca.ised.sts.integration.processor.salesforce;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.test.spring.CamelSpringRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import ca.ised.sts.integration.BaseTest;
import ca.ised.sts.integration.endpoints.salesforce.JwtOAuthAuthentication;

public class SalesforceAuthenticationProcessorTest extends BaseTest {

	@Autowired
	private CamelContext camelContext;

	@InjectMocks
	private SalesforceAuthenticationProcessor salesforceAuthenticationProcessor;

	@Mock
	private JwtOAuthAuthentication jwtOAuthAuthentication;

	private final String AUTH_TOKEN = "I authorize you for now";

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		Mockito.doReturn(AUTH_TOKEN).when(jwtOAuthAuthentication).getAuthToken(Mockito.anyString());
		Mockito.doReturn("jwtToken").when(jwtOAuthAuthentication).getJWTtoken();
	}

	@DirtiesContext
	@Test
	public void processTest() throws Exception {
		Exchange exchange = new DefaultExchange(camelContext);

		salesforceAuthenticationProcessor.process(exchange);
		String authTokenString = (String) exchange.getOut().getHeader("Authorization");

		assertEquals("Bearer " + AUTH_TOKEN, authTokenString);
	}
}