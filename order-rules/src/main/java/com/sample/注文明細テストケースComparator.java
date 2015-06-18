package com.sample;

import java.util.Comparator;

import com.sample.model.注文明細テストケース;

public class 注文明細テストケースComparator implements Comparator<注文明細テストケース> {

	@Override
	public int compare(注文明細テストケース o1, 注文明細テストケース o2) {
		return o1.getLineNo() > o2.getLineNo() ? -1 : 1;
	}

}
