package ca.ised.sts.integration.processor.salesforce;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.test.spring.CamelSpringRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import ca.ised.sts.integration.BaseTest;
import ca.ised.sts.integration.model.salesforce.Case;


public class CaseCreatorProcessorTest extends BaseTest {

	@Autowired
	private CamelContext camelContext;
	
	@DirtiesContext
	@Test
	public void processTest() throws Exception {
		Exchange exchange = new DefaultExchange(camelContext);
		
		CaseCreatorProcessor processor = new CaseCreatorProcessor();
		processor.process(exchange);
		Case c = (Case)exchange.getOut().getBody();
		
		assertEquals("Web", c.getOrigin());
		assertEquals("New", c.getStatus());
	}

}
