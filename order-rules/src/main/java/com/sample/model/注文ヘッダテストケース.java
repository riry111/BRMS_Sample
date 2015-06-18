package com.sample.model;

import java.io.Serializable;

import com.example.order.model.注文ヘッダ;

public class 注文ヘッダテストケース implements Serializable {
	
	private int caseNo;
	private String type;
	private 注文ヘッダ fact;
	
	public int getCaseNo() {
		return caseNo;
	}
	public void setCaseNo(int caseNo) {
		this.caseNo = caseNo;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public 注文ヘッダ getFact() {
		return fact;
	}
	public void setFact(注文ヘッダ fact) {
		this.fact = fact;
	}


}
