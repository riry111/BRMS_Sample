package com.example.order.client;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.example.order.model.EJBIOFact;

import org.apache.http.HttpHost;
import org.apache.http.client.AuthCache;
import org.apache.http.client.HttpClient;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.BasicHttpContext;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.resteasy.client.ClientExecutor;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.client.core.executors.ApacheHttpClient4Executor;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;


/**
 * Restクライアントテスト用クラス
 * 
 * @author  
 *
 */
public class RestClient {

	/**
	 * RestEasyサーバにアクセスして結果を標準出力へ
	 * 
	 * @param args String[]
	 */
	public static void main(String[] args) {
		// RESTEasy初期化
//		RegisterBuiltin.register(ResteasyProviderFactory.getInstance());

		// アクセスするURIをパラメータを含めて生成
		String uri = "http://localhost:8080/order-server-side/rs/fee";
//		
//		HttpClient client = HttpClientBuilder.create().build();
		
//	    ClientExecutor executor = createAuthenticatingExecutor(client);
	    
//		ClientRequest request;

		try {
//			request = new ClientRequest(uri, executor);

		    ClientRequest request = new ClientRequest(uri);
		    			
			// ファクト生成
			EJBIOFact fact = createEJBIOFact();
			
			// ファクトをJSON変換
	        ObjectMapper mapper = new ObjectMapper();
	        String json = mapper.writeValueAsString(fact);	        
			
			request.accept("application/json").pathParameter("id", 1).body( MediaType.APPLICATION_JSON, json);

			// POSTの戻りをStringで受けとり結果を標準出力へ
			printResponse4String(request, "POST");
			
        } catch (Exception e) {
            System.err.println(e);
        }
	}

	/**
	 * INPUTパラメータのEJBIOFact生成
	 * @return
	 */
	private static EJBIOFact createEJBIOFact() {
		return EJBClient.createFact();
	}

	/**
	 * HTTPヘッダーの内容をすべて標準出力へ
	 * 
	 * @param responseHeaderMultivaluedMap MultivaluedMap<String, String>
	 */
	private static void printHeader(
			MultivaluedMap<String, String> responseHeaderMultivaluedMap) {
		Set<Entry<String, List<String>>> set = responseHeaderMultivaluedMap
				.entrySet();

		Iterator<Entry<String, List<String>>> itr = set.iterator();

		while (itr.hasNext()) {
			Entry<String, List<String>> enrtry = itr.next();
			String key = enrtry.getKey();
			String value = "";
			int count = 0;
			List<String> list = enrtry.getValue();
			for (String str : list) {
				if (count == 0) {
					value = value + str;
				} else {
					value = value + "," + str;
				}
				count++;
			}
			System.out.println("HTTP Header:" + key + " value:" + value);
		}
	}

	/**
	 * Stringで受けて内容を標準出力
	 * 
	 * @param request ClientResponse
	 * @param methodName String
	 * @throws Exception
	 */
	private static void printResponse4String(ClientRequest request, String methodName) throws Exception {
		
		ClientResponse<?> response = execHTTPMethod(request, methodName, String.class);
		
        // Check the HTTP status of the request
        // HTTP 200 indicates the request is OK
        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed request with HTTP status: " + response.getStatus());
        }
        
		printHeader(response.getHeaders());
		String returnString = (String) response.getEntity();
		System.out.println(methodName + " ステータスコード:" + response.getStatus());
		System.out.println(methodName + " 戻り値:" + returnString);
	}

	/**
	 * HTTPメソッドの種類を引数で特定しClientResponseを生成
	 * （ここではPOSTしか使っていない）
	 * @param request ClientRequest
	 * @param methodName String
	 * @param retrunClass Class
	 * @return ClientResponse
	 * @throws Exception
	 */
	private static ClientResponse<?> execHTTPMethod(ClientRequest request,
			String methodName, Class<?> retrunClass) throws Exception {
		ClientResponse<?> response;
		if (methodName.equals("GET")) {
			response = request.get(retrunClass);
		} else if (methodName.equals("PUT")) {
			response = request.put(retrunClass);
		} else if (methodName.equals("POST")) {
			response = request.post(retrunClass);
		} else if (methodName.equals("DELETE")) {
			response = request.delete(retrunClass);
		} else {
			response = request.get(retrunClass);
		}
		return response;
	}
	
//    private static ClientExecutor createAuthenticatingExecutor(HttpClient client) {
//      // Create AuthCache instance
//      AuthCache authCache = new BasicAuthCache();
//      
//      // Generate BASIC scheme object and add it to the local auth cache
//      BasicScheme basicAuth = new BasicScheme();
//      HttpHost targetHost = new HttpHost("localhost", 8080);
//      authCache.put(targetHost, basicAuth);
//
//      // Add AuthCache to the execution context
//      BasicHttpContext localContext = new BasicHttpContext();
//      localContext.setAttribute(HttpClientContext.AUTH_CACHE, authCache);
//      
//      // Create ClientExecutor.
//      ApacheHttpClient4Executor executor = new ApacheHttpClient4Executor(client, localContext);
//      return executor;
//    }
}