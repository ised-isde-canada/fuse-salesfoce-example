package ca.ised.sts.integration.exception;

import ca.ised.sts.integration.model.salesforce.BaseQueryObjectAttribute;

public abstract class BaseQueryObject {
	
	private BaseQueryObjectAttribute attributes;

	public BaseQueryObjectAttribute getAttributes() {
		return attributes;
	}

	public void setAttributes(BaseQueryObjectAttribute attributes) {
		this.attributes = attributes;
	}

}
