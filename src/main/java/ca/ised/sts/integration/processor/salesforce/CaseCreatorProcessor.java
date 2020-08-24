package ca.ised.sts.integration.processor.salesforce;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import ca.ised.sts.integration.model.salesforce.Case;

@Component
public class CaseCreatorProcessor implements Processor{

	@Override
	public void process(Exchange exchange) throws Exception {
		Case c = new Case();
		c.setDescription("Mike's description");
		c.setOrigin("Web");
		c.setStatus("New");
		
		exchange.getOut().setBody(c);
		
	}

}
