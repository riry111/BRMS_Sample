package com.sample;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;


import com.example.order.model.BRMSIOFact;
import com.example.order.model.商品マスタ;
import com.orangesignal.csv.manager.CsvEntityManager;
import com.sample.model.TestFact;

public class TestUtil {
	
	private TestUtil(){}
	
	// マスターファクト用CSVファイル名
	private static final String 商品マスタCSVpath = "商品マスタ.csv";
	
	private static final String ENCODING = "UTF-8";


	private static CsvEntityManager csvMgr;
	/**
	 * テスト結果比較用ファクト生成
	 * @param caseId テストケースNo
	 * @param obj 対象ファクト
	 * @param lineNo テストケース内の行番号
	 * @param resultFlg true:ルール実行結果,false:期待値
	 * @param ignoreList ファクト内のgetterメソッドの内、テスト結果比較対象外となる項目名の配列
	 * @return テスト結果比較用ファクト({@code com.sample.model.TestFact})のリスト
	 * @throws Exception
	 */
	public static List<TestFact> createTestMaster(int caseId, Object obj, int lineNo, boolean resultFlg, String[] ignoreList) {

		List<TestFact> list = new ArrayList<TestFact>();
		
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
				
				String paramName = method.getName().substring( 3, method.getName().length());
				// ignoreListは対象外
				if( ignoreList != null && Arrays.asList(ignoreList).contains(paramName) ){
					continue;
				}

				// getterメソッドを実行
				Object value = method.invoke(obj, null);
				
				// 取得値をString型へ変換
				String valueStr = convertToString(value);
				TestFact fact = new TestFact();
				
				if (resultFlg == true) {
					fact.setType("result");
				}
				fact.setFactName(obj.getClass().getSimpleName()); // ファクト名
				fact.setCaseId(String.valueOf(caseId)); // ケースNo
				fact.setLineNo(String.valueOf(lineNo+1));
				fact.setKey(paramName);	// 項目名	
				fact.setValue(valueStr);// 値
				
				list.add(fact);
				
			}
		} catch( Exception e ){
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * String型へ変換
	 * @param value
	 * @return String
	 */
	private static String convertToString(Object value){

		// 取得値がnullの場合
		if( value == null ){
			return "";
		}
		
		return value.toString();
		
	}
	
	/**
	 * マスターファクト系のデータをCSVファイルから取り込む
	 * @param path マスターファクトの取り込み用CSVファイルのパス
	 * @return マスターファクト系のファクトを保持する{@code com.example.order.model.BRMSIOFact}
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static  BRMSIOFact createBRMSIOFact(String path) throws IOException {
		
    	BRMSIOFact iofact = new BRMSIOFact();
    	
    	List<商品マスタ> shouhinMstList;

    	try {
		
			shouhinMstList = (List<商品マスタ>) ReadCsv.exec(商品マスタ.class, path + 商品マスタCSVpath);

	    	iofact.setShouhinMsList(shouhinMstList);
	    	
	    	
    	} catch (IOException e) {
    		e.printStackTrace();
    		throw e;
    	}
		
    	return iofact;
	}
}
