package com.example.order.ejbimpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.example.order.ejb.IFeeCalculationService;
import com.example.order.model.BRMSIOFact;
import com.example.order.model.EJBIOFact;
import com.example.order.model.商品マスタ;

import org.kie.api.event.rule.AgendaEventListener;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

import com.orangesignal.csv.CsvConfig;
import com.orangesignal.csv.manager.CsvEntityManager;

/**
 * 手数料計算EJB
 * Remote呼び出し、Local呼び出しどちらも可能(呼び出し時に指定)
 * @author 
 *
 */
@Stateless
public class FeeCalculationService implements IFeeCalculationService.LocalService, IFeeCalculationService.RemoteService {
	
	private static final String 手数料計算フローID = "calculation_flow";
	
	private static final String ENCODING = "UTF-8";
	private static final String CSVpath = "/Users/mamurai/work/";
	private static final String 商品マスタCSVpath = CSVpath + "商品マスタ.csv";

	private static CsvEntityManager csvMgr;
	
	@EJB
	private RuleLoader loader;
	
	@Override
	public EJBIOFact exeService(EJBIOFact ejbfact) {
		
    	BRMSIOFact iofact;
    	KieSession kSession = null;
		try {

			// sessionプールからKieSessionを取得
			kSession = loader.borrowKieSession();

			AgendaEventListener listener = new PocAgendaEventListener();
			kSession.addEventListener(listener);

			// 初回のみ
			if (kSession.getFactCount() == 0) {
				// CSVを読み込みマスターファクト生成
				iofact = createFact();
				// マスターファクトインサート
				kSession.insert(iofact);
			}			

			// ファクトをインサート		
			FactHandle factHandle = kSession.insert(ejbfact);
			// フロー開始
			kSession.startProcess(手数料計算フローID);
			// ルール実行
			kSession.fireAllRules();
			// EJBIOFactをdelete
			kSession.delete(factHandle);

			kSession.removeEventListener(listener);
			// KieSession返却
			loader.returnKieSession(kSession);

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("CSV読み込みでエラー発生");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ejbfact;
    			
	}
	
	
	
	@SuppressWarnings("unchecked")
	private BRMSIOFact createFact() throws IOException {

		BRMSIOFact iofact = new BRMSIOFact();
    	
    	CsvConfig cfg = new CsvConfig(',', '"', '"');
    	cfg.setIgnoreEmptyLines(true);	// 空白行無視
    	csvMgr = new CsvEntityManager(cfg);

		List<商品マスタ> shouhinMsList;
		
    	try {
		
    		shouhinMsList = (List<商品マスタ>) readCsv(商品マスタ.class, 商品マスタCSVpath);
	    	
	    	iofact.setShouhinMsList(shouhinMsList);
	    	
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
	private List<?> readCsv(Class<?> clazz, String filepath) throws IOException {
		
		File file = new File(filepath);
		InputStreamReader isr = new InputStreamReader(new FileInputStream(file), ENCODING);

		// CSVから読み込み
		List<?> list = csvMgr.load(clazz).from(isr);

		return list;
	}

}
