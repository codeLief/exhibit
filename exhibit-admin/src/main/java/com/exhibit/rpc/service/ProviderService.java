package com.exhibit.rpc.service;

import com.exhibit.rpc.base.BaseService;
import com.exhibit.rpc.model.HessianProvider;
import com.exhibit.rpc.model.Provider;

/**
 * 
 * @author ankang
 * @version 1.0
 * @since 2017/04/25 14:26
 */
public interface ProviderService extends BaseService<Provider>{
	
	/*
	 * return id 
	 */
	public String save(HessianProvider pro) throws Exception;
}
