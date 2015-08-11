package com.example.order;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;

import com.example.order.model.EJBIOFact;
import com.example.order.model.注文ヘッダ;
import com.example.order.model.注文明細;

public class RuleExecuteImplTest {

	static KieContainer kcontainer = KieServices.Factory.get().getKieClasspathContainer();

	
	@Test
	public void OrderTest1(){
		EJBIOFact data = new EJBIOFact();
		List<注文明細> dlist = new ArrayList<注文明細>();
		dlist.add(new 注文明細() {{ set注文番号("T0001"); set明細番号("01"); set商品番号("A00001"); set受注数(12); }});
		dlist.add(new 注文明細() {{ set注文番号("T0001"); set明細番号("02"); set商品番号("A00002"); set受注数(20); }});
		data.setHeader( new 注文ヘッダ() {{ set会員番号("00001"); set注文番号("T0001"); set注文日("20150617");}});
		data.setDetailsList(dlist);
		
		RuleExecuteImpl exec = new RuleExecuteImpl(kcontainer);
		exec.apply(data);

		System.out.println (data.getHeader().toString());
		System.out.println (data.getDetailsList().toString());

		assertEquals(data.getHeader().get合計金額(), 3880);
	}
}
