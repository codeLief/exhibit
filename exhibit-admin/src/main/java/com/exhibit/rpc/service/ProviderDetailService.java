package com.exhibit.rpc.service;

import java.util.List;

import com.exhibit.rpc.base.BaseService;
import com.exhibit.rpc.model.HessianProvider;
import com.exhibit.rpc.model.ProviderDetail;

/**
 * 
 * @author ankang
 * @version 1.0
 * @since 2017/04/25 14:26
 */
public interface ProviderDetailService extends BaseService<ProviderDetail>{
	
	public String save(HessianProvider hp, String providerId, int heartbeatTime);
	
	public void destroy(List<String> ids);
}
