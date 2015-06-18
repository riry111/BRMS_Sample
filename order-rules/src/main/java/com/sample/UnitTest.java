package com.sample;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

import com.sample.model.Errors;
import com.sample.model.TestFact;

import com.example.order.model.BRMSIOFact;
import com.example.order.model.EJBIOFact;
import com.example.order.model.注文ヘッダ;
import com.example.order.model.注文明細;

/**
 * 手数料計算テスト用クラス
 * 
 * @author 
 *
 */
public class UnitTest {
	
	// TODO マスターファクトのCSVファイルパスを設定
	private static final String MASTER_CSV_PATH = "/Users/mamurai/work/";
	private static final String RULE_SESSION = "ksession-calculation";
	
	private static final String 手数料計算フローID = "calculation_flow";
	
	private static KieContainer kContainer;
	private static KieServices kieServices;
	
	private static String[] destIgnoreList = {"送料セット済"};

	public static void main(String[] args) {

		kieServices = KieServices.Factory.get();
	    kContainer = kieServices.getKieClasspathContainer();
		
		// テスト用インプットデータをデシジョンテーブルから読み込む
		TreeMap<Integer, EJBIOFact> inmap = getTestCases("IN");
	    
		// 手数料計算ルールを実行し、実行結果をテスト用ファクトに変換
		List<TestFact> resultList = execRule(inmap);

		// テスト用期待値データをデシジョンテーブルから読み込む
		TreeMap<Integer, EJBIOFact> outmap = getTestCases("OUT");

		// テスト用ファクトに変換
		List<TestFact> expectList = convertFact(outmap);
		
		// テスト結果比較
		compareResult(resultList, expectList);
		
	}
	
	/**
	 * テスト結果比較ルールを実行し、ルール実行結果と期待値とを不一致があればログ出力する
	 * @param resultList
	 * @param expectList
	 */
	private static void compareResult(List<TestFact> resultList, List<TestFact> expectList) {
		
	    KieFileSystem kfs = kieServices.newKieFileSystem();
	    kfs.write(kieServices.getResources().newFileSystemResource("src/main/resources/com/sample/test/TestRule.drl"));	    
	    
	    kieServices.newKieBuilder(kfs).buildAll();	        
        KieContainer kContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
        KieSession kSession = kContainer.newKieSession();
		
		// 実行結果をインサート
		for (TestFact fact : resultList) {
			kSession.insert(fact);
		}
		
		// 期待値をインサート
		for (TestFact fact : expectList) {
			kSession.insert(fact);
		}
		
		Errors errors = new Errors();
		kSession.insert(errors);
		
		// テスト結果比較ルール実行
		kSession.fireAllRules();
		
		kSession.dispose();
		
		// 結果を出力
		System.out.println("-------------------------------------------");
		if (errors.getErrorList().size() == 0) {
			System.out.println("test Result All OK");
		}
		
		for (String str : errors.getErrorList()) {
			System.out.println(str);
		}
		System.out.println("-------------------------------------------");
	}

	/**
	 * テストケースのOUTPUT用EJBIOFactを受け取り、TestFactにセットする
	 * @param map
	 * @return
	 */
	private static List<TestFact> convertFact(TreeMap<Integer, EJBIOFact> map) {
		List<TestFact> list = new ArrayList<TestFact>();
		
		Iterator<Integer> ite = map.keySet().iterator();
		while (ite.hasNext()){
			
			Integer caseno = ite.next();
			EJBIOFact ejbfact = map.get(caseno);

	    	List<注文明細> detailsList = ejbfact.getDetailsList();
	    	
    		list.addAll(TestUtil.createTestMaster(caseno, ejbfact.getHeader(), 0, false, null));
    		

    		for (int j = 0; j < detailsList.size(); j++) {
        		list.addAll(TestUtil.createTestMaster(caseno, detailsList.get(j), j, false, null));    			
    		}	

		}
		
		return list;
	}

	/**
	 * テストケースのINPUT用EJBIOFactが格納されたMapを受け取り、１ケースずつルールを実行し、
	 * 結果をTestFactにセットする
	 * @param map
	 * @return
	 */
	private static List<TestFact> execRule(TreeMap<Integer, EJBIOFact> map) {
		
		List<TestFact> list = new ArrayList<TestFact>();
		KieSession kSession = null;
		
		try {
	    	// マスターファクト
	    	BRMSIOFact iofact;
			iofact = TestUtil.createBRMSIOFact(MASTER_CSV_PATH);
			
			kSession = kContainer.newKieSession(RULE_SESSION);
//			kSession.addEventListener(new PocAgendaEventListener());
			kSession.insert(iofact);
			
			Iterator<Integer> ite = map.keySet().iterator();
			while (ite.hasNext()){
				
				Integer caseno = ite.next();
				EJBIOFact ejbfact = map.get(caseno);
		
		    	// insert fact
		    	FactHandle factHandle = kSession.insert(ejbfact);
		        
		        // startprocess
		        kSession.startProcess(手数料計算フローID);
		        // fire
		        kSession.fireAllRules();
		        
		        kSession.delete(factHandle);
		        
	    		list.addAll(TestUtil.createTestMaster(caseno, ejbfact.getHeader(), 0, true, null));
	    		

	    		for (int j = 0; j < ejbfact.getDetailsList().size(); j++) {
	        		list.addAll(TestUtil.createTestMaster(caseno, ejbfact.getDetailsList().get(j), j, true, null));    			
	    		}		        
			}

		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			if (kSession != null) {
				kSession.dispose();
			}
		}
        

		return list;
	}

	/**
	 * テストケース生成ルールを実行し、テスト用ファクトを生成
	 * @return
	 */
	private static TreeMap<Integer, EJBIOFact> getTestCases(String inout) {
		
	    TreeMap<Integer, EJBIOFact> map = new TreeMap<Integer, EJBIOFact>();
	    
	    KieFileSystem kfs = kieServices.newKieFileSystem();
	    kfs.write(kieServices.getResources().newFileSystemResource("src/main/resources/com/sample/testcases/テストケース作成フロー.bpmn"));	    
	    kfs.write(kieServices.getResources().newFileSystemResource("src/main/resources/com/sample/testcases/テストケース作成.drl"));	    
	    kfs.write(kieServices.getResources().newFileSystemResource("src/main/resources/com/sample/testcases/注文ヘッダテストケース作成.xls"));	    	    
	    kfs.write(kieServices.getResources().newFileSystemResource("src/main/resources/com/sample/testcases/注文明細テストケース作成.xls"));	    
	    
	    kieServices.newKieBuilder(kfs).buildAll();	        
        KieContainer kContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
        KieSession kSession = kContainer.newKieSession();

		kSession.insert(map);
		kSession.insert(inout);
		
		kSession.startProcess("com.sample.CreateTestCase");
		kSession.fireAllRules();
		
		kSession.dispose();
		
		return map;
	}
}
