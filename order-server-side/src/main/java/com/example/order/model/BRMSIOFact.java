package com.example.order.model;

import java.io.Serializable;
import java.util.List;
import com.example.order.model.商品マスタ;

public class BRMSIOFact implements Serializable {

	private static final long serialVersionUID = -5400486622121510173L;

	private List<商品マスタ> shouhinMsList;

	public List<商品マスタ> getShouhinMsList() {
		return shouhinMsList;
	}

	public void setShouhinMsList(List<商品マスタ> shouhinMsList) {
		this.shouhinMsList = shouhinMsList;
	}
}
