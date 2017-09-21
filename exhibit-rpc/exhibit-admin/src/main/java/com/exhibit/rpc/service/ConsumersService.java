package com.exhibit.rpc.service;

import java.util.List;

import com.exhibit.rpc.base.BaseService;
import com.exhibit.rpc.model.Consumers;
import com.exhibit.rpc.model.HessianConsumers;

/**
 * 
 * @author ankang
 * @version 1.0
 * @since 2017/04/25 14:26
 */
public interface ConsumersService extends BaseService<Consumers>{
	
	/**
	 * @return id 
	 */
	public String save(HessianConsumers hc, int heartbeatTime) throws Exception;
	
	public void destroy(List<String> ids);
}
