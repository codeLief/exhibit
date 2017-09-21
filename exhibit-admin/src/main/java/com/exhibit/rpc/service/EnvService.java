package com.exhibit.rpc.service;

import com.exhibit.rpc.base.BaseService;
import com.exhibit.rpc.model.Env;

/**
 * 
 * @author ankang
 * @version 1.0
 * @since 2017/04/25 14:26
 */
public interface EnvService extends BaseService<Env>{
	
	public Env create(Env entity);
}
