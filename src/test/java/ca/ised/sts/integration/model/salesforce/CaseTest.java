package ca.ised.sts.integration.model.salesforce;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CaseTest {
	
	@Test
	public void getSetTest() {
		
		BaseQueryObjectAttribute attributes = new BaseQueryObjectAttribute();
		String description = "description";
		String status = "status";
		String origin = "origin";
		
		Case c = new Case();
		c.setAttributes(attributes);
		c.setDescription(description);
		c.setStatus(status);
		c.setOrigin(origin);
		
		assertEquals(attributes, c.getAttributes());
		assertEquals(description, c.getDescription());
		assertEquals(status, c.getStatus());
		assertEquals(origin, c.getOrigin());
		
	}
}
