package ca.ised.sts.integration.model.salesforce;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class CaseQueryResponseTest {
	
	@Test
	public void getSetTest() {
		int totalSize = 22;
		boolean done = true;
		List<Case> records = new ArrayList<Case>();
		Case c = new Case();
		records.add(c);
		
		CaseQueryResponse queryResponse = new CaseQueryResponse();
		queryResponse.setTotalSize(totalSize);
		queryResponse.setDone(done);
		queryResponse.setRecords(records);
		
		assertEquals(totalSize, queryResponse.getTotalSize());
		assertEquals(done, queryResponse.isDone());
		assertEquals(records, queryResponse.getRecords());
		assertEquals(c, queryResponse.getRecords().get(0));
	}

}
