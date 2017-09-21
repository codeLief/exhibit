package com.exhibit.rpc.core;

import java.lang.reflect.Method;

import com.exhibit.rpc.model.HessianProvider;
import com.exhibit.rpc.util.ConstantsUtil;
/**
 * @author: ankang
 */
public class HeProviderClient implements HeClientInterface{
	
	private static final String REGEX = "equals|toString|notify|notifyAll|wait|getClass|hashCode";
	
	private HeClientActualize<HessianProvider> actualize;
	
	private HeProviderClient(HeClientActualize<HessianProvider> actualize){
		this.actualize = actualize;
	}
	
	private static HeProviderClient INSTANCE = null;
	public static HeProviderClient getInstance(HeClientActualize<HessianProvider> actualize){
		
		if(null == INSTANCE){
			synchronized (HeProviderClient.class) {
				if(null == INSTANCE){
					INSTANCE = new HeProviderClient(actualize);
				}
			}
		}
		return INSTANCE;
	}
	
	public void addProvider(HessianProvider provider){
		
		try {
			
			provider.setS_method(getMethod(provider.getS_interface()));
			
			actualize.addEntity(provider);
			
		} catch (ClassNotFoundException e) {
			
		}
		
	}
	
	@Override
	public void register() throws Exception {
		
		actualize.register(ConstantsUtil.HE_REGISTER_PROVIDER);
	}

	@Override
	public void destroy() throws Exception {
		
		actualize.destroy(ConstantsUtil.HE_DESTROY_PROVIDER);
	}
	
	private String getMethod(String clazz) throws ClassNotFoundException{
		
		Class<?> forName = Class.forName(clazz);
		
		Method[] methods = forName.getMethods();
		
		StringBuffer sb = new StringBuffer();
		
		if(methods != null && methods.length > 0){
			
			for (int i = 0; i < methods.length; i++) {
				
				Method method = methods[i];
				String name = method.getName();
				
				if(!name.matches(REGEX)){
					sb.append(",")
						.append(name);
				}
			}
		}
		
		return sb.length() > 0? sb.substring(1):sb.toString();
	}
}
