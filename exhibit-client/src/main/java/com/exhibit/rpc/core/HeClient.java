package com.exhibit.rpc.core;

import com.exhibit.rpc.model.HessianConsumers;
import com.exhibit.rpc.model.HessianProvider;



/**
 * 
 * @author: ankang
 * @date: 2017-4-14 下午2:09:39
 */
public class HeClient {

	private static HeProviderClient PROVIDER =  HeCoreFactory.getHeProviderClient();
	
	private static HeConsumersClient CONSUMER = HeCoreFactory.getHeConsumersClient();
	
	public static void addProvider(String sUrl, String sIp, String env,
			String sAppName, String sInterface, String readTimeout,
			String sDesc, String author) {
		
		PROVIDER.addProvider(new HessianProvider(sUrl, 
													sIp, 
													env, 
													null, 
													null, 
													sAppName, 
													sInterface, 
													readTimeout, 
													sDesc, 
													null, 
													author));
	}

	public static void addConsumer(String sUrl, String sInterface, String cIp,
			String env, String cAppName, String author) {

		CONSUMER.addConsumer(new HessianConsumers(sUrl, 
													sInterface, 
													cIp, 
													null,
													env,
													null, 
													cAppName, 
													author));
	}
	
}
