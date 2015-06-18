package com.example.order.ejbimpl;

import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.kie.api.KieBase;

public class StatefulSessionPool {

	ObjectPool pool;
	GenericObjectPoolConfig config = initConfig();
	KieBase ruleBase;

	public ObjectPool getPool() {
		return pool;
	}

	public StatefulSessionPool(KieBase ruleBase) {
		this.ruleBase = ruleBase;
		reBuildPool();
	}

	private static GenericObjectPoolConfig initConfig() {
		// (1)
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		config.setMaxTotal(2);
		config.setMaxIdle(2);
		config.setBlockWhenExhausted(true);
		config.setTestOnBorrow(false);
		config.setTestOnReturn(false);
		return config;
	}

	public void reBuildPool() {
		// (2)
		PoolableStatefulSessionFactory factory = new PoolableStatefulSessionFactory(
				ruleBase);
		this.pool = newGenericObjectPool(factory, config);
	}

	private static void initialPoolObjects(ObjectPool pool, int initNum) {
		// (3)
		Object[] objs = new Object[initNum];
		try {
			for (int i = 0; i < initNum; i++) {
				objs[i] = pool.borrowObject();
			}
			for (int i = 0; i < initNum; i++) {
				pool.returnObject(objs[i]);
				objs[i] = null;
			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				for (Object obj : objs) {
					if (obj != null)
						pool.returnObject(obj);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	private static GenericObjectPool newGenericObjectPool(
			PoolableStatefulSessionFactory factory,
			GenericObjectPoolConfig config) {
		// (4)
		GenericObjectPool pool = new GenericObjectPool(factory, config);
		initialPoolObjects(pool, config.getMaxIdle());
		return pool;
	}

}
