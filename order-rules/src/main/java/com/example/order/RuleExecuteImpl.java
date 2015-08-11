package com.example.order;

import java.io.IOException;

import org.kie.api.event.rule.AgendaEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.example.order.model.BRMSIOFact;
import com.example.order.model.EJBIOFact;
import com.sample.model.RuleExecute;

public class RuleExecuteImpl implements RuleExecute {

	private KieContainer kContainer;
	private static final String 金額計算フローID = "calculation_flow";
	
    public RuleExecuteImpl(KieContainer kContainer){
        this.kContainer = kContainer;
    }
    
	@Override
	public void apply(EJBIOFact data) {

		KieSession ksession = null;

		try {
        	ksession = kContainer.newKieSession("ksession-calculation");
					
			AgendaEventListener listener = new PocAgendaEventListener();
			ksession.addEventListener(listener);
			
			// 初回のみ
			if (ksession.getFactCount() == 0) {
				// CSVを読み込みマスターファクト生成
				BRMSIOFact iofact = DroolsTest.createFact();
				// マスターファクトインサート
				ksession.insert(iofact);
			}	
				
            ksession.insert(data);
//            ksession.getAgenda().getAgendaGroup("値引").setFocus();

            // フロー開始
            ksession.startProcess(金額計算フローID);
			
			// ルール実行
            ksession.fireAllRules();
            
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (ksession != null) ksession.dispose();
			
		}
	}
}

