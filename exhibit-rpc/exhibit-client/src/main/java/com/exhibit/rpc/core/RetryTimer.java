package com.exhibit.rpc.core;

import java.util.List;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.exhibit.rpc.task.TimerCallback;

/**
 * @author: ankang
 */
public class RetryTimer extends TimerTask {

	protected static final Logger LOGGER = LoggerFactory.getLogger(RetryTimer.class);
	
	private String url;
	
	private List<?> data;
	
	private Class<?> clazz;
	
	private TimerCallback call;
	
	@Override
	public void run() {
		
		try {
			
			Object result = HeConnector.submit(url, data, clazz);
			
			call.call(result, url);
		} catch (Exception e) {
			LOGGER.error("submit error");
			call.call(null, url);
		}
	}

	public RetryTimer(String url, List<?> data, Class<?> clazz){
		this.url = url;
		this.data = data;
		this.clazz = clazz;
	}
	public RetryTimer(String url, List<?> data, Class<?> clazz, TimerCallback call){
		this.url = url;
		this.data = data;
		this.clazz = clazz;
		this.call = call;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

	public void setData(List<?> data) {
		this.data = data;
	}
}
