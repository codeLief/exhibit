package com.exhibit.rpc.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.stereotype.Component;
/**
 * @author: ankang
 */
@Component
public class HeMain implements ApplicationListener<ApplicationContextEvent>{
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(HeMain.class);
	
	private HeProviderClient provider = HeCoreFactory.getHeProviderClient();
	
	private HeConsumersClient consumer = HeCoreFactory.getHeConsumersClient();
	
	public void register() {
		LOGGER.info(" ================  register hessian start ====================");
		try {
			
			provider.register();
			
			consumer.register();
			
		} catch (Exception e) {
			LOGGER.error("register hessian fail", e);
		}
		
		LOGGER.info(" ================  register hessian end ====================");
	}

	public void destroy() {
		
		LOGGER.info(" ================  destroy hessian start ====================");
		try {
			provider.destroy();
			
			consumer.destroy();
		} catch (Exception e) {
			LOGGER.error("destroy hessian fail", e);
		}
		LOGGER.info(" ================  destroy hessian end ====================");
	}

	@Override
	public void onApplicationEvent(ApplicationContextEvent event) {
		
		if(event.getApplicationContext().getParent() == null){
			
			if(event instanceof ContextRefreshedEvent){
				register();
			}else if(event instanceof ContextClosedEvent || 
					event instanceof ContextStoppedEvent){
				destroy();
			}
		}
	}
}
