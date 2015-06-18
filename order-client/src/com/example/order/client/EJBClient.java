package com.example.order.client;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.example.order.ejb.IFeeCalculationService;
import com.example.order.model.EJBIOFact;
import com.example.order.model.注文ヘッダ;
import com.example.order.model.注文明細;
/**
 * EJBクライアントテスト用クラス
 * 
 * @author 
 *
 */
public class EJBClient {

	public static void main(String[] args) throws Exception {
		
		invokeStatelessBean();
	}

	private static void invokeStatelessBean() throws NamingException {
		
		// lookup(Remoteインターフェース使用)
		final IFeeCalculationService.RemoteService ejbService = lookupService();
		System.out.println("Obtained a remote ejb");
		
		// create fact
		EJBIOFact fact = createFact();
		
		System.out.println("before:" + fact.toString());
		// invoke
		EJBIOFact result = ejbService.exeService(fact);
		
		System.out.println("after :" + result.toString());
		
	}

	public static EJBIOFact createFact() {
		
		EJBIOFact ejbfact = new EJBIOFact();
		
    	注文ヘッダ header = create注文ヘッダ();     	
    	List<注文明細> detailsList = create注文明細リスト();

    	ejbfact.setHeader(header);
    	ejbfact.setDetailsList(detailsList);

		return ejbfact;
	}
	

	private static List<注文明細> create注文明細リスト() {
		List<注文明細> detailsList = new ArrayList<注文明細>();
    	
    	注文明細 detail_1 = new 注文明細();
    	detail_1.set注文番号("T0001");
    	detail_1.set明細番号("01");
    	detail_1.set商品番号("A00001");
    	detail_1.set受注数(10);

		detailsList.add(detail_1);

    	注文明細 detail_2 = new 注文明細();
    	detail_2.set注文番号("T0001");
    	detail_2.set明細番号("02");
    	detail_2.set商品番号("A00002");
    	detail_2.set受注数(20);
    	
		detailsList.add(detail_2);
		
		return detailsList;
	}

	private static 注文ヘッダ create注文ヘッダ() {
		
    	注文ヘッダ header = new 注文ヘッダ();
    	header.set会員番号("00001");
    	header.set注文番号("T0001");
    	header.set注文日("20150616");
		
		return header;
	}


	private static IFeeCalculationService.RemoteService lookupService() throws NamingException {
		
        final Hashtable<String, String> jndiProperties = new Hashtable<String, String>();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");

        final Context context = new InitialContext(jndiProperties);

        // The JNDI lookup name for a stateless session bean has the syntax of:
        // ejb:<appName>/<moduleName>/<distinctName>/<beanName>!<viewClassName>
        //
        // <appName> The application name is the name of the EAR that the EJB is deployed in
        // (without the .ear). If the EJB JAR is not deployed in an EAR then this is
        // blank. The app name can also be specified in the EAR's application.xml
        //
        // <moduleName> By the default the module name is the name of the EJB JAR file (without the
        // .jar suffix). The module name might be overridden in the ejb-jar.xml
        //
        // <distinctName> : AS7 allows each deployment to have an (optional) distinct name.
        // This example does not use this so leave it blank.
        //
        // <beanName> : The name of the session been to be invoked.
        //
        // <viewClassName>: The fully qualified classname of the remote interface. Must include
        // the whole package name.

        // let's do the lookup
        return (IFeeCalculationService.RemoteService) context.lookup("ejb:/order-server-side/FeeCalculationService!"
                + IFeeCalculationService.RemoteService.class.getName());
    }

}
