package com.exhibit.rpc.core.heartbeat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.exhibit.rpc.model.Heartbeat;
import com.exhibit.rpc.service.HeartbeaService;
import com.exhibit.rpc.task.TimerSchedule;
import com.exhibit.rpc.util.ConstantsUtil;
import com.exhibit.rpc.util.SpringApplicationContextUtil;
import com.google.common.collect.Maps;

/**
 * @author: ankang
 */
public class HeartbeatManager {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(HeartbeatManager.class);
	
	private static final Map<String, String> HEARTBEAT_URL = Maps.newConcurrentMap();
	
	private static final Map<String, HeartbeatConfig> HEARTBEAT_CONFIG = Maps.newConcurrentMap();
	
	private static final Map<String, TimerTask> HEARTBEAT_TASK = Maps.newConcurrentMap();
	
	private static String generateHeartbeatId(String url, String env, int heartbeatTime){
		
		String urlValue = url + env;
		
		if(HEARTBEAT_URL.containsValue(urlValue)){
			
			LOGGER.info("heartbeat instance already exists. url:" + url);
			return HEARTBEAT_CONFIG.get(urlValue).getHeartbeatId();
		}
		
		String heartbeatId = getHeartbeatId(url, heartbeatTime, env);
		
		final HeartbeatConfig config = new HeartbeatConfig(heartbeatId, url, heartbeatTime);
		
		HEARTBEAT_URL.put(heartbeatId, urlValue);
		HEARTBEAT_CONFIG.put(urlValue, config);
		HEARTBEAT_TASK.put(heartbeatId, schedule(config));
		
		return heartbeatId;
	}
	
	public static String generateHeartbeatId(String ip, String port, String appName, String env, int heartbeatTime){
		
		if(StringUtils.isBlank(ip)
				|| StringUtils.isBlank(port)
				|| StringUtils.isBlank(appName)
				|| StringUtils.isBlank(env)){
			
			throw new IllegalArgumentException("generate heartbeatId fail, argument is null");
		}
		if(heartbeatTime <= 0){
			heartbeatTime = ConstantsUtil.HEARTBEAT_TIME_DEFAULT;
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append(ConstantsUtil.PROTOCOL_HEAD)
		.append(ip)
		.append(ConstantsUtil.COLON)
		.append(port)
		.append(appName);
		
		return generateHeartbeatId(sb.toString(), env, heartbeatTime);
	}
	
	private static String getHeartbeatId(String url, int heartbeatTime, String env){
		
		HeartbeaService heartbeaService = SpringApplicationContextUtil.getBeansOfType(HeartbeaService.class);
		
		Heartbeat heartbeat = new Heartbeat(url, heartbeatTime, Integer.valueOf(env));
		
		heartbeaService.save(heartbeat);
		
		return heartbeat.getId();
	}
	
	public static void load(){
		
		HeartbeaService heartbeaService = SpringApplicationContextUtil.getBeansOfType(HeartbeaService.class);
		
		List<Heartbeat> list = heartbeaService.query(new HashMap<String, Object>());
		
		for (Heartbeat heartbeat : list) {
			
			HeartbeatConfig config = new HeartbeatConfig(heartbeat.getId(), heartbeat.getHeartbeatUrl(), heartbeat.getHeartbeatTime());
			
			String urlValue = heartbeat.getHeartbeatUrl() + heartbeat.getEnv();
			
			HEARTBEAT_URL.put(heartbeat.getId(), urlValue);
			HEARTBEAT_CONFIG.put(urlValue, config);
			HEARTBEAT_TASK.put(heartbeat.getId(), schedule(config));
			
			LOGGER.info("load heartbeat data url:" + heartbeat.getHeartbeatUrl());
		}
	}
	
	public static void remove(String heartbeatId){
		
		if(HEARTBEAT_URL.containsKey(heartbeatId)){
			
			HEARTBEAT_CONFIG.remove(HEARTBEAT_URL.get(heartbeatId));
			
			HEARTBEAT_URL.remove(heartbeatId);
			
			TimerTask task = HEARTBEAT_TASK.remove(heartbeatId);
			
			HeartbeaService heartbeaService = SpringApplicationContextUtil.getBeansOfType(HeartbeaService.class);
			
			heartbeaService.remove(heartbeatId);
			
			task.cancel();
			
			LOGGER.info("remove heartbeatId:" + heartbeatId);
		}
	}
	
	private static TimerTask schedule(HeartbeatConfig config) {

		return TimerSchedule.schedule(new HeartbeaTask(new HeartbeaHandleImpl(config)), config.getHeartbeatTime() * 1000,
				config.getHeartbeatTime() * 1000);
	}
}
