package com.exhibit.rpc.core;

import com.exhibit.rpc.model.HessianConsumers;
import com.exhibit.rpc.model.HessianProvider;
/**
 * @author: ankang
 */
public final class HeCoreFactory {
	
	public static HeProviderClient getHeProviderClient(){
		
		return HeProviderClient.getInstance(new HeClientActualize<HessianProvider>());
	}
	
	public static HeConsumersClient getHeConsumersClient(){
		
		return HeConsumersClient.getInstance(new HeClientActualize<HessianConsumers>());
	}
	
	public static HessianExposeSystemConfig getHessianExposeSystemConfig(){
		
		HessianExposeSystemConfig config = HessianExposeSystemConfig.getInstance();
		
		try {
			config.loadConfig(null);
		} catch (Exception e) {
			
		}
		
		return config;
	}
}
