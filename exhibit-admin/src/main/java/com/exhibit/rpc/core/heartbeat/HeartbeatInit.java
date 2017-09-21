package com.exhibit.rpc.core.heartbeat;

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
public class HeartbeatInit implements ApplicationListener<ApplicationContextEvent>{
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(HeartbeatInit.class);
	
	public void init() {
		LOGGER.info(" ================  load heartbeat start  ====================");
		try {
			
			HeartbeatManager.load();
		} catch (Exception e) {
			LOGGER.error("load heartbeat data fail", e);
		}
		
		LOGGER.info(" ================  load heartbeat data end ====================");
	}

	public void destroy() {
		
		try {
			
		} catch (Exception e) {
			
		}
	}

	@Override
	public void onApplicationEvent(ApplicationContextEvent event) {
		
		if(event.getApplicationContext().getParent() == null){
			
			if(event instanceof ContextRefreshedEvent){
				init();
			}else if(event instanceof ContextClosedEvent || 
					event instanceof ContextStoppedEvent){
				
			}
		}
	}
}
