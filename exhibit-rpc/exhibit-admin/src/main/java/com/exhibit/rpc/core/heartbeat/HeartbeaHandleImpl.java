package com.exhibit.rpc.core.heartbeat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.exhibit.rpc.core.http.HttpClientHandler;
import com.exhibit.rpc.service.HeartbeaService;
import com.exhibit.rpc.util.SpringApplicationContextUtil;

/**
 * @author: ankang
 */
public class HeartbeaHandleImpl implements HeartbeaHandle{
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(HeartbeaHandleImpl.class);
	
	private HeartbeatConfig config;
	
	private HttpClientHandler handle;
	
	public HeartbeaHandleImpl(HeartbeatConfig config){
		
		this.config = config;
		this.handle = new HttpClientHandler(config.getHeartbeatUrl());
	}
	
	@Override
	public void execute(int hbInvalidCount){
		
		boolean result = Boolean.TRUE;
		
		try {
			
			result = handle.isNormal(handle.sendRequest());
			
		} catch (Exception e) {
			
			LOGGER.debug("check error");
			result = Boolean.FALSE;
		}
		
		LOGGER.info(String.format("check service url:[%s] result:[%s]", config.getHeartbeatUrl(), result));
		
		executeAfter(result, hbInvalidCount);
	}
	
	private void executeAfter(boolean result, int hbInvalidCount){
		
		if(result){
			
			config.restFailCount();
		}else if(config.checkUrlValid(hbInvalidCount)){
			//连续连接失效则对此服务判死刑
			result = Boolean.FALSE;
			HeartbeatManager.remove(config.getHeartbeatId());
		}else{
			result = Boolean.TRUE;
		}
		
		HeartbeaService heartbeaService = SpringApplicationContextUtil.getBeansOfType(HeartbeaService.class);
		
		heartbeaService.refresh(config.getHeartbeatId(), result);
	}
}
