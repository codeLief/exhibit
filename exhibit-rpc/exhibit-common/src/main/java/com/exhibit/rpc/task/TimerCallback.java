package com.exhibit.rpc.task;

public interface TimerCallback {
	
	void call(Object result, String methodUrl);
}
