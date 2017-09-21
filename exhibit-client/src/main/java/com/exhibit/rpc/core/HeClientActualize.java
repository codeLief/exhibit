package com.exhibit.rpc.core;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.remoting.caucho.HessianExporter;
import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping;

import com.exhibit.rpc.conf.ConfigAnnotation;
import com.exhibit.rpc.model.BaseResponse;
import com.exhibit.rpc.model.HessianProvider;
import com.exhibit.rpc.model.RegisterServiceResponse;
import com.exhibit.rpc.task.TimerCallback;
import com.exhibit.rpc.task.TimerSchedule;
import com.exhibit.rpc.util.ClassUtils;
import com.exhibit.rpc.util.GsonUtil;
import com.exhibit.rpc.util.SpringApplicationContextUtil;
import com.google.common.collect.Lists;
/**
 * @author: ankang
 */
public class HeClientActualize<T> implements TimerCallback {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HeClientActualize.class);
	
	private HessianExposeSystemConfig sysConfig = HeCoreFactory.getHessianExposeSystemConfig();
	
	private boolean registerDone = Boolean.FALSE;
	
	private Map<String, ?> handlerMap;
	
	private List<String> ids = Lists.newArrayList();
	
	private List<T> registerData = Lists.newArrayList();
	
	private int retryTimeInrc;
	
	private int retryCount;
	
	protected void initConfig() throws Exception{
		
		retryTimeInrc = sysConfig.getRetryTimeIncr();
		
		retryCount = sysConfig.getRetryCount();
		
		try {
			if(handlerMap == null){
				BeanNameUrlHandlerMapping bean = SpringApplicationContextUtil.getBeansOfType(BeanNameUrlHandlerMapping.class);
				handlerMap = bean.getHandlerMap();
			}
		} catch (Exception e) {
			handlerMap = Collections.unmodifiableMap(SpringApplicationContextUtil.getBeanNamesOfType(HessianExporter.class));
		}
	}
	
	protected void autowareMethodUrl(T t){
		
		if(t instanceof HessianProvider){
			
			HessianProvider temp = (HessianProvider)t;
			
			if(StringUtils.isEmpty(temp.getS_url())){
				
				for (Map.Entry<String, ?> handel : handlerMap.entrySet()) {
					Object value = handel.getValue();
					if(value != null && value instanceof HessianExporter){
						if(temp.getS_interface().equals(((HessianExporter)value).getServiceInterface().getCanonicalName())){
							LOGGER.debug("handel url:" + handel.getKey());
							temp.setS_url(handel.getKey());
							return;
						}
					}
				}
				
			}
		}
	}
	
	protected void register(String methodUrl) throws Exception {
		
		if(registerDone){
			
			LOGGER.warn("Already registered!");
			return;
		}
		
		initConfig();
		
		RegisterServiceResponse response = null;
		
		try {
			
			if(CollectionUtils.isEmpty(registerData)) return;
				
			Iterator<T> iterator = registerData.iterator();
			
			while (iterator.hasNext()) {
				
				T t = iterator.next();
				
				autowareMethodUrl(t);
				
				if(!autowareAndCheck(t)){
					LOGGER.info("Check failed, remove data:" + GsonUtil.toJson(t));
					iterator.remove();
				}
			}
			
			response = HeConnector.submit(methodUrl, registerData, RegisterServiceResponse.class);
		} catch (Exception e) {
			LOGGER.error("Register hessian fail!");
		}
		call(response, methodUrl);
	}
	
	protected void addEntity(T t){

		try {
			if(!registerDone){
				registerData.add(t);
			}
		} catch (Exception e) {
			LOGGER.error("Add provider fail", e);
		}
	
	}
	
	protected boolean autowareAndCheck(T t) throws Exception{

        if (null == t) {
            throw new Exception("cannot autowareConfig null");
        }

        try {

            Field[] fields = t.getClass().getDeclaredFields();

            for (Field field : fields) {

                if (field.isAnnotationPresent(ConfigAnnotation.class)) {

                    if (Modifier.isStatic(field.getModifiers())) {
                        continue;
                    }
                    
                    String property = field.getName();
                    
                    String value = BeanUtils.getProperty(t, property);
                    
                    if(StringUtils.isEmpty(value)){
                    	
                    	ConfigAnnotation configAnnotation = field.getAnnotation(ConfigAnnotation.class);
                    	
                    	value = sysConfig.getValue(configAnnotation.name());
                    	
                    	 if (StringUtils.isNotEmpty(value)) {

                             try {
                            	 
                            	 field.setAccessible(true);
                            	 ClassUtils.setFieldValeByType(field, t, value);
                             } catch (Exception e) {

                                 LOGGER.error(String.format("invalid config: %s", property), e);
                             }
                         }else{
                        	 
                        	 if(configAnnotation.require()){
                        		 
                        		 return !configAnnotation.require();
                        	 }
                         }
                    }
                    
                }
            }
        } catch (Exception e) {
        	
            throw new Exception("error while autowire config file", e);
        }
        
        return Boolean.TRUE;
    }
	
	protected void destroy(String methodUrl) throws Exception{
		
		if(CollectionUtils.isNotEmpty(ids)){
			
			HeConnector.submit(methodUrl, ids, RegisterServiceResponse.class);
		}
	}

	@Override
	public void call(Object result, String methodUrl) {
		
		if(result != null && result instanceof BaseResponse && !registerDone){
			
			RegisterServiceResponse response = (RegisterServiceResponse)result;
			
			if(response.resultCheck()){
				
				registerDone = Boolean.TRUE;
				ids = response.getLists();
				LOGGER.info("Register hessian success!");
				return;
			}
		}
		
		if(retryCount == -1 || retryCount > 0){
			
			retryTimeInrc = sysConfig.getRetryTime() + retryTimeInrc;
			
			TimerSchedule.schedule(new RetryTimer(methodUrl, registerData, 
													RegisterServiceResponse.class, this), 
													retryTimeInrc * 1000);
			
			LOGGER.info(retryTimeInrc + " seconds later retry");
			
			if(retryCount != -1)
				retryCount--;
		}
	}
}
