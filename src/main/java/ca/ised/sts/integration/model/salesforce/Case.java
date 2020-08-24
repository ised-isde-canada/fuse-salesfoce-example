package ca.ised.sts.integration.model.salesforce;

import com.fasterxml.jackson.annotation.JsonProperty;

import ca.ised.sts.integration.exception.BaseQueryObject;

public class Case extends BaseQueryObject{
		
	@JsonProperty(value = "Description", required = false)
	private String description;
	
	@JsonProperty(value = "Status")
	private String status;
	
	@JsonProperty(value = "Origin")
	private String origin;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	
	
}
