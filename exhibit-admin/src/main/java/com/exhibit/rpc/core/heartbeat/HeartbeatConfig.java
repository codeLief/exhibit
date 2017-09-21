package com.exhibit.rpc.core.heartbeat;
/**
 * @author: ankang
 */
public class HeartbeatConfig {
	
	private String heartbeatId;
	
	private String heartbeatUrl;
	
	private int heartbeatTime;
	
	private int failCount = 0;

	public HeartbeatConfig() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HeartbeatConfig(String heartbeatId, String heartbeatUrl,
			int heartbeatTime) {
		super();
		this.heartbeatId = heartbeatId;
		this.heartbeatUrl = heartbeatUrl;
		this.heartbeatTime = heartbeatTime;
	}

	public String getHeartbeatId() {
		return heartbeatId;
	}

	public String getHeartbeatUrl() {
		return heartbeatUrl;
	}

	public int getHeartbeatTime() {
		return heartbeatTime;
	}

	public int getFailCount() {
		return failCount;
	}
	
	public void restFailCount(){
		this.failCount = 0;
	}
	
	public boolean checkUrlValid(int validCount){
		
		return ++this.failCount >= validCount;
	}
}
