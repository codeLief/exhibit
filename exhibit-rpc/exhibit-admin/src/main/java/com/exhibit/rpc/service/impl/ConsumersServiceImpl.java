package com.exhibit.rpc.service.impl;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.log4j.Log4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exhibit.rpc.base.BaseServiceImpl;
import com.exhibit.rpc.core.heartbeat.HeartbeatManager;
import com.exhibit.rpc.dao.ConsumersDAO;
import com.exhibit.rpc.model.Consumers;
import com.exhibit.rpc.model.Env;
import com.exhibit.rpc.model.HessianConsumers;
import com.exhibit.rpc.model.Provider;
import com.exhibit.rpc.service.ConsumersService;
import com.exhibit.rpc.service.EnvService;
import com.exhibit.rpc.service.ProviderService;
import com.exhibit.rpc.util.ConstantsUtil;
import com.exhibit.rpc.util.GsonUtil;
import com.exhibit.rpc.util.StatusConstantsUtil;
import com.google.common.collect.Maps;

/**
 * 
 * @author ankang
 * @version 1.0
 * @since 2017/04/25 14:26
 */
@Log4j
@Service
public class ConsumersServiceImpl extends BaseServiceImpl<ConsumersDAO, Consumers> implements ConsumersService {
	
	@Autowired
	private ProviderService providerService;
	
	@Autowired
	private EnvService envService;
	
	@Override
	public String save(HessianConsumers hc, int heartbeatTime) throws Exception {
		
		String sWebAppName = getWebAppName(hc.getS_url());
		String sMethodUrl = getMethodUrl(hc.getS_url());
		
		Env env = new Env();
		env.setName(hc.getEnv());
		env = envService.create(env);
		
		Map<String, Object> params = Maps.newHashMap();
		params.put("sUrl", sMethodUrl);
		params.put("sEnv", env.getEnvId());
		params.put("cAppName", hc.getC_appName());
		params.put("cIp", hc.getC_ip());
		
		Consumers consumers = findByParams(params);
		
		String heartbeatId = HeartbeatManager.generateHeartbeatId(hc.getC_ip(), 
																	hc.getC_port(), 
																	hc.getC_appName(), 
																 	String.valueOf(env.getEnvId()), 
																 	heartbeatTime);
		
		String coId;
		
		if(consumers == null){
			
			params.clear();
			params.put("sUrl", sMethodUrl);
			params.put("sEnv", env.getEnvId());
			params.put("sAppName", sWebAppName);
			
			Provider provider = providerService.findByParams(params);
			
			if(provider == null){
				
				provider = new Provider();
				provider.setSAppName(sWebAppName);
				provider.setSEnv(env.getEnvId());
				provider.setSInterface(hc.getS_interface());
				provider.setSUrl(sMethodUrl);
				provider.setAuthor(hc.getAuthor());
				provider.setCreateAt(System.currentTimeMillis());
				
				providerService.save(provider);
				log.info(String.format("create provider [%s]", GsonUtil.toJson(provider)));
			}
			consumers = new Consumers();
			
			consumers.setAuthor(hc.getAuthor());
			consumers.setCAppName(hc.getC_appName());
			consumers.setCIp(hc.getC_ip());
			consumers.setProviderId(provider.getId());
			consumers.setSEnv(env.getEnvId());
			consumers.setSInterface(hc.getS_interface());
			consumers.setSUrl(sMethodUrl);
			consumers.setHeartbeatId(heartbeatId);
			consumers.setStatus(StatusConstantsUtil.VALID_STATUS);
			consumers.setCreateAt(System.currentTimeMillis());
			
			save(consumers);
			coId = consumers.getId();
			log.info(String.format("create consumers [%s]", GsonUtil.toJson(consumers)));
		}else{
			coId = consumers.getId();
			consumers.setHeartbeatId(heartbeatId);
			consumers.setUpdateAt(System.currentTimeMillis());
			consumers.setStatus(StatusConstantsUtil.VALID_STATUS);
			modifySelective(consumers);
		}
		return coId;
	}
	
	@Override
	public void destroy(List<String> ids) {
		
		List<Consumers> list = queryByIds(ids);
		
		for (Consumers consumers : list) {
			
			consumers.setDestroyTime(System.currentTimeMillis());
			consumers.setUpdateAt(System.currentTimeMillis());
			consumers.setStatus(StatusConstantsUtil.INVALID_STATUS);
			modifySelective(consumers);
		}
		
		HeartbeatManager.remove(list.get(0).getHeartbeatId());
	}
	
	private String getWebAppName(String url){
		
		url = splitUrl(url);
		
		Pattern p1 = Pattern.compile(ConstantsUtil.WEB_NAME_REGEX);
		Matcher m1 = p1.matcher(url);
		if(m1.lookingAt()){
			
			int end = m1.end();
			
			return url.substring(0, end);
		}
		
		return url;
	}
	
	private String getMethodUrl(String url){
		
		url = splitUrl(url);
		
		Pattern p1 = Pattern.compile(ConstantsUtil.WEB_NAME_REGEX);
		Matcher m1 = p1.matcher(url);
		if(m1.lookingAt()){
			
			int end = m1.end();
			
			return url.substring(end);
		}
		
		return url;
	}
	
	private String splitUrl(String fullUrl){
		
		Pattern p = Pattern.compile(ConstantsUtil.WEB_URL_REGEX);
		Matcher m = p.matcher(fullUrl);
		
		if(m.lookingAt()){
			fullUrl = fullUrl.split(ConstantsUtil.WEB_URL_REGEX)[1];
		}
		
		return fullUrl;
	}
}