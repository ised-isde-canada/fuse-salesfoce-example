package ca.ised.sts.integration.processor.salesforce;

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

//@SpringBootTest
//@RunWith(CamelSpringRunner.class)
public class SalesforceAuthExceptionProcessorTest {

	@Autowired
	private CamelContext camelContext;
	
	@InjectMocks
	private SalesforceAuthExceptionProcessor salesforceAuthExceptionProcessor;
	
	@Mock
	private SalesforceAuthenticationProcessor salesforceAuthenticationProcessor;
    
    //@Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        Mockito.doNothing().when(salesforceAuthenticationProcessor).process(Mockito.anyObject());
    }
    
    @DirtiesContext
    //@Test
   	public void processTest() throws Exception {
   		Exchange exchange = new DefaultExchange(camelContext);
   		
   		salesforceAuthExceptionProcessor.process(exchange);
   		
   		assert(true); // we made it here without errors
   	}
}
