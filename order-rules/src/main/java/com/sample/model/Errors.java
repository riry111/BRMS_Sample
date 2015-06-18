package com.sample.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Errors implements Serializable {
	
	private List<String> errorList = new ArrayList<String>();

	public List<String> getErrorList() {
		return errorList;
	}

	public void setErrorList(List<String> errorList) {
		this.errorList = errorList;
	}
	
	public void addError(String str) {
		errorList.add(str);
	}

}
