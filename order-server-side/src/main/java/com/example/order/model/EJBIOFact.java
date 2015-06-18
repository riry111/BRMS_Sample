package com.example.order.model;

import java.io.Serializable;
import java.util.List;

/**
 * 注文金額計算EJBの引数
 * @author mamurai
 *
 */
public class EJBIOFact implements Serializable {
	
	private static final long serialVersionUID = 5854646215013342684L;

	private 注文ヘッダ header;

	private List<注文明細> detailsList;	

	public 注文ヘッダ getHeader() {
		return header;
	}

	public void setHeader(注文ヘッダ header) {
		this.header = header;
	}

	public List<注文明細> getDetailsList() {
		return detailsList;
	}

	public void setDetailsList(List<注文明細> detailsList) {
		this.detailsList = detailsList;
	}

	@Override
	public String toString() {
		return "EJBIOFact [header=" + header + ", detailsList=" + detailsList
				+ "]";
	}

}
