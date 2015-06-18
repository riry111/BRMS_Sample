package com.example.order.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class 注文ヘッダ implements Serializable {
	
	private static final long serialVersionUID = 3978918965280922040L;

	private String 注文番号;

	private String 会員番号;
	
	private String 注文日;
	
	// OUT
	private long 値引前金額;
	
	private long 明細合計金額;
	
	private long 値引後金額;

	private long 送料;
	
	private long 合計金額;
	
	private long 送料区分合計;
	
	private List<String> 適用割引一覧 = new ArrayList<String>();

	public String get注文番号() {
		return 注文番号;
	}

	public void set注文番号(String 注文番号) {
		this.注文番号 = 注文番号;
	}

	public String get会員番号() {
		return 会員番号;
	}

	public void set会員番号(String 会員番号) {
		this.会員番号 = 会員番号;
	}

	public String get注文日() {
		return 注文日;
	}

	public void set注文日(String 注文日) {
		this.注文日 = 注文日;
	}

	public long get値引前金額() {
		return 値引前金額;
	}

	public void set値引前金額(long 値引前金額) {
		this.値引前金額 = 値引前金額;
	}

	public long get明細合計金額() {
		return 明細合計金額;
	}

	public void set明細合計金額(long 明細合計金額) {
		this.明細合計金額 = 明細合計金額;
	}
	
	public long get値引後金額() {
		return 値引後金額;
	}

	public void set値引後金額(long 値引後金額) {
		this.値引後金額 = 値引後金額;
	}

	public long get送料() {
		return 送料;
	}

	public void set送料(long 送料) {
		this.送料 = 送料;
	}

	public long get合計金額() {
		return 合計金額;
	}

	public void set合計金額(long 合計金額) {
		this.合計金額 = 合計金額;
	}

	public long get送料区分合計() {
		return 送料区分合計;
	}

	public void set送料区分合計(long 送料区分合計) {
		this.送料区分合計 = 送料区分合計;
	}

	public List<String> get適用割引一覧() {
		return 適用割引一覧;
	}

	public void set適用割引一覧(List<String> 適用割引一覧) {
		this.適用割引一覧 = 適用割引一覧;
	}

	@Override
	public String toString() {
		return "注文ヘッダ [注文番号=" + 注文番号 + ", 会員番号=" + 会員番号 + ", 注文日=" + 注文日
				+ ", 値引前金額=" + 値引前金額 + ", 明細合計金額=" + 明細合計金額 + ", 値引後金額="
				+ 値引後金額 + ", 送料=" + 送料 + ", 合計金額=" + 合計金額 + ", 送料区分合計="
				+ 送料区分合計 + ", 適用割引一覧=" + 適用割引一覧 + "]";
	}

}
