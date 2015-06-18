package com.example.order.ejbimpl;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.kie.api.KieBase;
import org.kie.api.runtime.KieSession;

public class PoolableStatefulSessionFactory extends
		BasePooledObjectFactory<KieSession> {

	KieBase kBase;

	public PoolableStatefulSessionFactory(KieBase kBase) {
		this.kBase = kBase;
	}

	@Override
	public KieSession create() throws Exception {
		// (1)
		KieSession kSession = kBase.newKieSession();
System.out.println("=======> kiesession create now");
		return kSession;
	}

	@Override
	public PooledObject<KieSession> wrap(KieSession obj) {
		// (2)
		return new DefaultPooledObject(obj);
	}

	@Override
	public void destroyObject(PooledObject<KieSession> obj) throws Exception {
		// (3)
		KieSession kSession = (KieSession) obj;
		kSession.dispose();
		System.out.println("=======> kiesession dispose now");
	}

}
