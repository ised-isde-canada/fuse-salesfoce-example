package ca.ised.sts.integration.processor.salesforce;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.test.spring.CamelSpringRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import ca.ised.sts.integration.model.salesforce.Case;

//@SpringBootTest
//@RunWith(CamelSpringRunner.class)
public class CaseQueryListProcessorTest {

	@Autowired
	private CamelContext camelContext;
		
	private String description = "test description";
	
	private String caseListJson = "{" + 
			"    \"totalSize\": 1," + 
			"    \"done\": true," + 
			"    \"records\": [" + 
			"        {" + 
			"            \"attributes\": {" + 
			"                \"type\": \"Case\"," + 
			"                \"url\": \"/services/data/v48.0/sobjects/Case/someId\"" + 
			"            }," + 
			"            \"Description\": \"" + description + "\"," + 
			"            \"Status\": \"New\"," + 
			"            \"Origin\": \"Phone\"" + 
			"        }]}";
	
	@DirtiesContext
	@SuppressWarnings("unchecked")
	//@Test
	public void processTest() throws Exception {
		Exchange exchange = new DefaultExchange(camelContext);
	    InputStream caseListStream = new ByteArrayInputStream(caseListJson.getBytes());
		exchange.getIn().setBody(caseListStream);
		
		CaseQueryListProcessor processor = new CaseQueryListProcessor();
		processor.process(exchange);
		
		List<Case> cases = (List<Case>)(List<?>)exchange.getOut().getBody();
		
		String outDescription = cases.get(0).getDescription();
		
		assertEquals(description, outDescription);
	}
}
