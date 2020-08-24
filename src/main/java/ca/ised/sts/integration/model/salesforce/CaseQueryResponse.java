package ca.ised.sts.integration.model.salesforce;

import java.util.List;

public class CaseQueryResponse {

	private int totalSize;
	private boolean done;
	private List<Case> records;
	
	public int getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}
	public boolean isDone() {
		return done;
	}
	public void setDone(boolean done) {
		this.done = done;
	}
	public List<Case> getRecords() {
		return records;
	}
	public void setRecords(List<Case> records) {
		this.records = records;
	}
	
}
