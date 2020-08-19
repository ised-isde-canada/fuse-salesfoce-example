package ca.ised.sts.integration.route;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.http.common.HttpOperationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ca.ised.sts.integration.model.salesforce.Case;
import ca.ised.sts.integration.model.salesforce.CaseQueryResponse;
import ca.ised.sts.integration.processor.salesforce.CaseCreatorProcessor;
import ca.ised.sts.integration.processor.salesforce.CaseQueryListProcessor;
import ca.ised.sts.integration.processor.salesforce.SalesforceAuthExceptionProcessor;
import ca.ised.sts.integration.processor.salesforce.SalesforceAuthenticationProcessor;

/**
 * Route files used to orchestrate the integration by piecing together the different stages of the integration
 *  
 * @author Michael Marshall
 *
 */
@Component
public class StsRouteBuilder extends RouteBuilder {
	
	@Value("${sf-sts-endpoint-domain:saasy-ised-isde--devsts.my.salesforce.com}") // added default 
	private String SF_ENDPOINT_DOMAIN;
	
	@Value("${sts-integration-endpoint-domain:localhost}") // added default 
	private String INTEGRATION_ENDPOINT_DOMAIN;
	
	@Value("${sts-integration-endpoint-port:8181}") // added default 
	private String INTEGRATION_ENDPOINT_PORT;
	
	@Autowired
	private CaseQueryListProcessor caseQueryListProcessor;
	
	@Autowired
	private CaseCreatorProcessor caseCreatorProcessor;
	
	@Autowired
	private SalesforceAuthenticationProcessor salesforceAuthenticationProcessor;
	
	@Autowired
	private SalesforceAuthExceptionProcessor salesforceAuthExceptionProcessor;
		
	JacksonDataFormat jsonDataFormatQueryResponse = new JacksonDataFormat(CaseQueryResponse.class);
	JacksonDataFormat jsonDataFormatCase = new JacksonDataFormat(Case.class);

	@Override
	public void configure() throws Exception {

		// route for REST GET Call
		restConfiguration().component("restlet").host(INTEGRATION_ENDPOINT_DOMAIN).port(INTEGRATION_ENDPOINT_PORT);
		
		// READ cases from Salesforce
		rest("cases/list").get().route()
			.process(salesforceAuthenticationProcessor)
			.setHeader(Exchange.HTTP_METHOD, simple("GET"))
			.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
			.to("https://" + SF_ENDPOINT_DOMAIN + "/services/data/v48.0/queryAll/?q=Select+Description,Status,Origin+from+case")
			.process(caseQueryListProcessor)
			.marshal(jsonDataFormatCase)
			.onException(HttpOperationFailedException.class)
		    .maximumRedeliveries(10) 
		    .redeliveryDelay(5000) // 5 sec
		    .onRedelivery(salesforceAuthExceptionProcessor); // refresh OAuth token if token has expired
		
		// INSERT a Case to Salesforce
		rest("cases/insert").get().route().process(caseCreatorProcessor).marshal(jsonDataFormatCase)
			.process(salesforceAuthenticationProcessor)
			.setHeader(Exchange.HTTP_METHOD, simple("POST"))
			.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
			.to("https://" + SF_ENDPOINT_DOMAIN + "/services/data/v48.0/sobjects/case")
			.onException(HttpOperationFailedException.class)
		    .maximumRedeliveries(10) 
		    .redeliveryDelay(5000) // 5 sec
		    .onRedelivery(salesforceAuthExceptionProcessor); // refresh OAuth token if token has expired
				
	}

}