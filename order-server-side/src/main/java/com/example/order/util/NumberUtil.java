package com.example.order.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberUtil {
	
	private NumberUtil() {}

	/**
	 * 引数のdoubleの中で、最大値を返却する
	 * @param args
	 * @return
	 */
	public static double getMax(double... args){
		
		double result = 0d;
		for (double db : args) {
			if (result < db) {
				result = db;
			}
		}
		
		return result;
	}
	
	/**
	 * 引数のdoubleを乗算し、小数点以下切捨てる
	 * @param db1
	 * @param db2
	 * @return
	 */
	public static long multiplyDouble(long lo1, double db2) {
		BigDecimal bd1 = new BigDecimal(lo1);
		BigDecimal bd2 = new BigDecimal(db2);
		
		return bd1.multiply(bd2).setScale(0, RoundingMode.DOWN).longValue();
	}
	
	public static void main(String[] args) {
		
		
		System.out.println(NumberUtil.multiplyDouble(12, 1.34));
		System.out.println(NumberUtil.calculateTax(1000,5,100));
	}
	
	/**
	 * 第一引数のlongに第二引数のdoubleを乗算し、第三引数のlongで除算し、小数点以下切り捨てる
	 * @param l1
	 * @param d1 
	 * @param l2
	 * @return
	 */
	public static long calculateTax(long l1, double d1, long l2) {
		BigDecimal bd1 = new BigDecimal(l1);
		BigDecimal bd2 = new BigDecimal(d1);
		BigDecimal bd3 = new BigDecimal(l2);
		
		if (l2 == 0) {
			return 0;
		}
		
		return bd1.multiply(bd2).divide(bd3).setScale(0, RoundingMode.DOWN).longValue();		
		
	}
	
	/**
	 *  (単価 + (単価 * 税率 / 100)) * 数量（小数点以下切捨て）
	 * @param price
	 * @param amount
	 * @param tax
	 * @return
	 */
	public static long calGross(long price, int amount, double tax) {
		
		BigDecimal bPrice = new BigDecimal(price);
		BigDecimal bTax = new BigDecimal(tax);
		
		bPrice = bPrice.add(bPrice.multiply(bTax).divide(new BigDecimal(100).setScale(0, RoundingMode.DOWN)));
		
		return bPrice.longValue() * amount;
	}
}
