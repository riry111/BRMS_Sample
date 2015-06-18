package com.sample.model;

import java.io.Serializable;

import com.example.order.model.注文ヘッダ;
import com.example.order.model.注文明細;

public class 注文明細テストケース implements Serializable {
	
	private int caseNo;
	private String type;
	private int lineNo;
	private 注文明細 fact;
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
	public int getLineNo() {
		return lineNo;
	}
	public void setLineNo(int lineNo) {
		this.lineNo = lineNo;
	}
	public 注文明細 getFact() {
		return fact;
	}
	public void setFact(注文明細 fact) {
		this.fact = fact;
	}
	
}
