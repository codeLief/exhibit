package com.exhibit.rpc.service.impl;

import java.util.Map;

import lombok.extern.log4j.Log4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exhibit.rpc.base.BaseServiceImpl;
import com.exhibit.rpc.dao.ProviderDAO;
import com.exhibit.rpc.model.Env;
import com.exhibit.rpc.model.HessianProvider;
import com.exhibit.rpc.model.Provider;
import com.exhibit.rpc.service.EnvService;
import com.exhibit.rpc.service.ProviderDetailService;
import com.exhibit.rpc.service.ProviderService;
import com.exhibit.rpc.util.GsonUtil;
import com.google.common.collect.Maps;

/**
 * 
 * @author ankang
 * @version 1.0
 * @since 2017/04/25 14:26
 */
@Log4j
@Service
public class ProviderServiceImpl extends BaseServiceImpl<ProviderDAO, Provider> implements ProviderService {
	
	@Autowired
	private ProviderDetailService detailService;
	
	@Autowired
	private EnvService envService;
	
	@Override
	public String save(HessianProvider pro) throws Exception{
		
		Env env = new Env();
		env.setName(pro.getEnv());
		
		env = envService.create(env);
		
		Map<String, Object> params = Maps.newHashMap();
		params.clear();
		params.put("sUrl", pro.getS_url());
		params.put("sEnv", env.getEnvId());
		params.put("sAppName", pro.getS_appName());
		
		Provider provider = findByParams(params);
		//创建提供者
		if(provider == null){
			
			provider = new Provider();
			provider.setAuthor(pro.getAuthor());
			provider.setSAppName(pro.getS_appName());
			provider.setSEnv(env.getEnvId());
			provider.setSInterface(pro.getS_interface());
			provider.setSMethods(pro.getS_method());
			provider.setSUrl(pro.getS_url());
			provider.setCreateAt(System.currentTimeMillis());
			
			save(provider);
			log.info(String.format("create provider [%s]", GsonUtil.toJson(provider)));
		}else{
			
			provider.setAuthor(pro.getAuthor());
			provider.setSInterface(pro.getS_interface());
			provider.setSMethods(pro.getS_method());
			provider.setUpdateAt(System.currentTimeMillis());
			
			modifySelective(provider);
			log.info(String.format("update provider [%s]", GsonUtil.toJson(provider)));
		}
		
		return provider.getId();
	}
	
}