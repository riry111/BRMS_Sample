package com.example.order.model;

import java.io.Serializable;

import com.orangesignal.csv.annotation.CsvColumn;
import com.orangesignal.csv.annotation.CsvEntity;

@CsvEntity(header=true)
public class 商品マスタ implements Serializable {
	
	private static final long serialVersionUID = -91580505431910115L;

	@CsvColumn(name="SHOUHIN_ID")
	private String 商品番号;
	
	@CsvColumn(name="SHOUHIN_NAME")
	private String 商品名;
	
	@CsvColumn(name="TANKA")
	private long 単価;
	
	@CsvColumn(name="SEND_KUBUN")
	private long 送料区分;

	public String get商品番号() {
		return 商品番号;
	}

	public void set商品番号(String 商品番号) {
		this.商品番号 = 商品番号;
	}

	public String get商品名() {
		return 商品名;
	}

	public void set商品名(String 商品名) {
		this.商品名 = 商品名;
	}

	public long get単価() {
		return 単価;
	}

	public void set単価(long 単価) {
		this.単価 = 単価;
	}

	public long get送料区分() {
		return 送料区分;
	}

	public void set送料区分(long 送料区分) {
		this.送料区分 = 送料区分;
	}

	@Override
	public String toString() {
		return "商品マスタ [商品番号=" + 商品番号 + ", 商品名=" + 商品名 + ", 単価=" + 単価
				+ ", 送料区分=" + 送料区分 + "]";
	}
	
	
}
