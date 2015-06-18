package com.sample;

import org.drools.decisiontable.InputType;
import org.drools.decisiontable.SpreadsheetCompiler;
import org.kie.internal.io.ResourceFactory;

public class DecisionTableCompile {
	
	private static String xlsFileName = "/Users/mamurai/01_Middle/01_App/workspace/JBDS8-BRMS6.1-OrderSample/order-rules/src/main/resources/com/sample/testcases/注文ヘッダテストケース作成.xls";
	
	public static void main(String[] args) {
		try {
			DecisionTableCompile.printCompiledXls(xlsFileName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	/**
	 * デシジョンテーブルをDRLにコンパイルした結果をコンソールに出力
	 * @param xlsFileName
	 * @throws Exception
	 */
	private static void printCompiledXls(String xlsFileName) throws Exception {		

    	// DRLにコンパイルした結果をSYSOUT ===========================>>	
        SpreadsheetCompiler sc = new SpreadsheetCompiler(); 		
        String drlstr = sc.compile( 		
        ResourceFactory.newClassPathResource(xlsFileName, DecisionTableCompile.class).getInputStream(), InputType.XLS);         		
        System.out.println(drlstr);		
    	// DRLにコンパイルした結果をSYSOUT ===========================<<	
        		
    }		
}
