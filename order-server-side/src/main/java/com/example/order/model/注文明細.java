package com.example.order.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class 注文明細 implements Serializable {
	
	private static final long serialVersionUID = 6817201187189442062L;

	private String 注文番号;
	
	private String 明細番号;

	private String 商品番号;
	
	private long 受注数;
	
	// OUT
	private String 商品名;
	
	private long 値引前金額;

	private long 値引後金額;
		
	private long 送料区分;
	
	private List<String> 適用割引一覧 = new ArrayList<String>();

	public String get注文番号() {
		return 注文番号;
	}

	public void set注文番号(String 注文番号) {
		this.注文番号 = 注文番号;
	}

	public String get明細番号() {
		return 明細番号;
	}

	public void set明細番号(String 明細番号) {
		this.明細番号 = 明細番号;
	}
	
	public String get商品番号() {
		return 商品番号;
	}

	public void set商品番号(String 商品番号) {
		this.商品番号 = 商品番号;
	}

	public long get受注数() {
		return 受注数;
	}

	public void set受注数(long 受注数) {
		this.受注数 = 受注数;
	}

	public String get商品名() {
		return 商品名;
	}

	public void set商品名(String 商品名) {
		this.商品名 = 商品名;
	}

	public long get値引前金額() {
		return 値引前金額;
	}

	public void set値引前金額(long 値引前金額) {
		this.値引前金額 = 値引前金額;
	}

	public long get値引後金額() {
		return 値引後金額;
	}

	public void set値引後金額(long 値引後金額) {
		this.値引後金額 = 値引後金額;
	}

	public long get送料区分() {
		return 送料区分;
	}

	public void set送料区分(long 送料区分) {
		this.送料区分 = 送料区分;
	}

	public List<String> get適用割引一覧() {
		return 適用割引一覧;
	}

	public void set適用割引一覧(List<String> 適用割引一覧) {
		this.適用割引一覧 = 適用割引一覧;
	}
	
	@Override
	public String toString() {
		return "注文明細 [注文番号=" + 注文番号 + ", 明細番号=" + 明細番号 + ", 商品番号=" + 商品番号
				+ ", 受注数=" + 受注数 + ", 商品名=" + 商品名 + ", 値引前金額=" + 値引前金額
				+ ", 値引後金額=" + 値引後金額 + ", 送料区分=" + 送料区分 + ", 適用割引一覧=" + 適用割引一覧
				+ "]";
	}

	
}
