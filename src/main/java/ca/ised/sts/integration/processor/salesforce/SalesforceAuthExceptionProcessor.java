package ca.ised.sts.integration.processor.salesforce;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ca.ised.sts.integration.endpoints.salesforce.JwtOAuthAuthentication;

/**
 * Polpulate Auth token
 * 
 * @author Michael Marshall
 * 
 *
 */
@Component
public class SalesforceAuthExceptionProcessor implements Processor {
	
	@Autowired
	private SalesforceAuthenticationProcessor salesforceAuthenticationProcessor;
	
	// Clear auth token which will force a refresh
	@Override
	public void process(Exchange exchange) throws Exception {
		salesforceAuthenticationProcessor.populateAuthToken();
	}

}
