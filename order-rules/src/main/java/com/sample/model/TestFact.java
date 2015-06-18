package com.sample.model;

import java.io.Serializable;

public class TestFact implements Serializable {
	
	private String type;

	private String factName;
	
	private String caseId;
	
	private String lineNo;
	
	private String key;
	
	private String value;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFactName() {
		return factName;
	}

	public void setFactName(String factName) {
		this.factName = factName;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getLineNo() {
		return lineNo;
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "TestFact [type=" + type + ", factName=" + factName
				+ ", caseId=" + caseId + ", lineNo=" + lineNo + ", key=" + key
				+ ", value=" + value + "]";
	}

}
