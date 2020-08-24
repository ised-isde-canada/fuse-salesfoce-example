package ca.ised.sts.integration.model.salesforce;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BaseQueryObjectAttributeTest {

	@Test
	public void getSetTest() {
		String type = "type";
		String url = "URL";
		
		BaseQueryObjectAttribute baseQueryObjectAttribute = new BaseQueryObjectAttribute();
		baseQueryObjectAttribute.setType(type);
		baseQueryObjectAttribute.setUrl(url);
		
		assertEquals(type, baseQueryObjectAttribute.getType());
		assertEquals(url, baseQueryObjectAttribute.getUrl());
	}
}
