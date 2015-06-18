package com.example.order;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.kie.api.KieServices;
import org.kie.api.event.rule.AgendaEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.logger.KieRuntimeLogger;

import com.example.order.model.BRMSIOFact;
import com.example.order.model.商品マスタ;
import com.example.order.model.注文ヘッダ;
import com.example.order.model.注文明細;
import com.orangesignal.csv.CsvConfig;
import com.orangesignal.csv.manager.CsvEntityManager;

/**
 * This is a sample class to launch a rule.
 */
public class DroolsTest {
	private static final String ENCODING = "UTF-8";
	private static final String CSVpath = "/Users/mamurai/work/";
	private static final String 商品マスタCSVpath = CSVpath + "商品マスタ.csv";

	private static CsvEntityManager csvMgr;
	private static final String 金額計算フローID = "calculation_flow";
	
    public static final void main(String[] args) {
    	BRMSIOFact iofact;
    	
        try {
            // load up the knowledge base
	        KieServices ks = KieServices.Factory.get();
    	    KieContainer kContainer = ks.getKieClasspathContainer();
        	KieSession kSession = kContainer.newKieSession("ksession-calculation");
        	KieRuntimeLogger logger = ks.getLoggers().newFileLogger(kSession, "audit");

			AgendaEventListener listener = new PocAgendaEventListener();
			kSession.addEventListener(listener);
			
			// 初回のみ
			if (kSession.getFactCount() == 0) {
				// CSVを読み込みマスターファクト生成
				iofact = createFact();
				// マスターファクトインサート
				kSession.insert(iofact);
			}	
			
            // go !
        	注文ヘッダ header = new 注文ヘッダ();
        	header.set会員番号("00001");
        	header.set注文番号("T0001");
        	header.set注文日("20150616");
        	
        	注文明細 meisai1 = new 注文明細();
        	meisai1.set注文番号("T0001");
        	meisai1.set明細番号("01");
        	meisai1.set商品番号("A00001");
        	meisai1.set受注数(10);
        	
        	注文明細 meisai2 = new 注文明細();
        	meisai2.set注文番号("T0001");
        	meisai2.set明細番号("02");
        	meisai2.set商品番号("A00002");
        	meisai2.set受注数(20);
        	
        	注文ヘッダ header2 = new 注文ヘッダ();
        	header2.set会員番号("00002");
        	header2.set注文番号("T0002");
        	header2.set注文日("20150617");
        	
        	注文明細 meisai3 = new 注文明細();
        	meisai3.set注文番号("T0002");
        	meisai3.set明細番号("01");
        	meisai3.set商品番号("A00003");
        	meisai3.set受注数(3);
        	
        	注文明細 meisai4 = new 注文明細();
        	meisai4.set注文番号("T0002");
        	meisai4.set明細番号("02");
        	meisai4.set商品番号("A00004");
        	meisai4.set受注数(6);
        	
            kSession.insert(header);
            kSession.insert(meisai1);
            kSession.insert(meisai2);
            
            kSession.insert(header2);
            kSession.insert(meisai3);
            kSession.insert(meisai4);
            
			// フロー開始
			kSession.startProcess(金額計算フローID);
			
			// ルール実行
            kSession.fireAllRules();
            
            logger.close();
            
            //Output
            System.out.println("ヘッダー：" + header.toString());
            System.out.println("明細1：" + meisai1.toString());
            System.out.println("明細2：" + meisai2.toString());
            
            System.out.println("ヘッダー2：" + header2.toString());
            System.out.println("明細3：" + meisai3.toString());
            System.out.println("明細4：" + meisai4.toString());
            
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

	@SuppressWarnings("unchecked")
	private static BRMSIOFact createFact() throws IOException {

		BRMSIOFact iofact = new BRMSIOFact();
    	
    	CsvConfig cfg = new CsvConfig(',', '"', '"');
    	cfg.setIgnoreEmptyLines(true);	// 空白行無視
    	csvMgr = new CsvEntityManager(cfg);

		List<商品マスタ> shouhinMstList;

    	try {
		
			shouhinMstList = (List<商品マスタ>) readCsv(商品マスタ.class, 商品マスタCSVpath);

	    	iofact.setShouhinMsList(shouhinMstList);
	    	
    	} catch (IOException e) {
    		e.printStackTrace();
    		throw e;
    	}
		
    	return iofact;
	}

	/**
	 * CSVファイルを読み込み、ファクトにセット
	 * @param clazz
	 * @param filepath
	 * @return
	 * @throws IOException
	 */
	private static List<?> readCsv(Class<?> clazz, String filepath) throws IOException {
		
		File file = new File(filepath);
		InputStreamReader isr = new InputStreamReader(new FileInputStream(file), ENCODING);

		// CSVから読み込み
		List<?> list = csvMgr.load(clazz).from(isr);

		return list;
	}
}