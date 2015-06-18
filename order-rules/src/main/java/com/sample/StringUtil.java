package com.sample;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class StringUtil {

	private StringUtil() {}
	
	public static Object convertBlank(Object obj, String alterStr) {
		
		try {
			// メソッド一覧取得
			Method[] methodList = obj.getClass().getMethods();
			
			// getterメソッドパターン
			Pattern pattern = Pattern.compile("get.*");
			
			for( Method method : methodList ){
				// getterメソッド以外はcontinue
				if( ! pattern.matcher(method.getName()).find() ){
					continue;
				}
				
				// getterメソッドを実行
				Object value = method.invoke(obj, null);
				
				// 取得値がString型
				if( value instanceof String ){
					String str = (String)value;
					// △を含む文字列の場合
					if (str.contains(alterStr)) {
						// 半角スペースにリプレース
						str = str.replaceAll(alterStr, " ");
						
						// setterメソッドをinvoke
						String paramName = method.getName().substring( 3, method.getName().length());
						PropertyDescriptor pd = new PropertyDescriptor(paramName, obj.getClass());
						Method w = pd.getWriteMethod();
						w.invoke(obj, str);
					}
				}
			}
			
		} catch (Exception e) {
				//
		}
		return obj;
	}
	
	public static List<String> convertCSVtoList(String str) {
		String[] tmp = str.split(",");
		List<String> stList = Arrays.asList(tmp);
		return stList;
	}
}
