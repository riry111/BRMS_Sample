package com.example.order.ejbimpl;

import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.apache.commons.pool2.ObjectPool;
import org.drools.compiler.kie.builder.impl.InternalKieContainer;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>MavenリポジトリからKieModuleをロードします。</p>
 * <p>{@code KieScanner}を使ってルールの変更を動的反映します。</p>
 */
@Singleton
@Startup
public class RuleLoader {

	private static final KieServices ks = KieServices.Factory.get();
	private static KieContainer kcontainer;
    private static KieBase kbase;
	private static ObjectPool<KieSession> pool;

//    public KieBase kieBase() { 
//    	return kbase;
//    }
    
    private long lastUpdated = 0;
    
    private KieScanner kscanner;
    
    Logger logger = LoggerFactory.getLogger(RuleLoader.class);

    /**
     * 初期化処理です。
     */
    @PostConstruct
    public void init() {
        refreshKbase();
        kscanner = KieServices.Factory.get().newKieScanner(kcontainer);
    }

	@SuppressWarnings("unchecked")
	private void refreshKbase() {
    	ReleaseId releaseId = ks.newReleaseId( "com.example.order", "order-rules", "1.0" );
    	kcontainer = ks.newKieContainer( releaseId );
        kbase = kcontainer.newKieBase("rules", null);
        lastUpdated = kieContainerTimestamp();
        pool = new StatefulSessionPool(kbase).getPool();
    }
    
    private boolean updated() {
        return kieContainerTimestamp() != lastUpdated;
    }
    
    private long kieContainerTimestamp() {
        return ((InternalKieContainer) kcontainer).getCreationTimestamp();
    }

    public KieSession borrowKieSession() throws Exception {
System.out.println("=======> kiesession borrow now");
		return pool.borrowObject();
    }
    
	public void returnKieSession(KieSession kSession) throws Exception {
		pool.returnObject(kSession);
System.out.println("=======> kiesession return now");
	}
    
    
    /**
     * Mavenリポジトリに対し、変更の有無をチェックし、変更を動的反映します。
     */
    @Schedule(second="*/30", minute="*", hour="*", persistent=false)
    public void scan() {
        logger.debug("start scanning...");

        kscanner.scanNow();
        if (updated()) {
            logger.debug("KieContainer updated.");
            refreshKbase();
        }
        logger.debug("end scanning.");
    }
}
