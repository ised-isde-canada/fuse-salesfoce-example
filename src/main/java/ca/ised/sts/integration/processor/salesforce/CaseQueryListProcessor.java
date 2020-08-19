package ca.ised.sts.integration.processor.salesforce;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import ca.ised.sts.integration.model.salesforce.CaseQueryResponse;

/**
 * Handle returned query object from Salesforce and convert records to a list of records.
 * 
 * @author Michael Marshall
 *
 */
@Component
public class CaseQueryListProcessor implements Processor {
	
	
    public void process(Exchange exchange) throws Exception {
    	InputStream is = (InputStream)exchange.getIn().getBody();
    	
    	BufferedReader in = new BufferedReader(new InputStreamReader(is));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		} 
		in.close();

		ObjectMapper mapper = new ObjectMapper();
		CaseQueryResponse result = mapper.readValue(response.toString(), CaseQueryResponse.class);		
		exchange.getOut().setBody(result.getRecords());
    }
}
