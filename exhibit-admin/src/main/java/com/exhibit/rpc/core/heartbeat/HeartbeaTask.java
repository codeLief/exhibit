package com.exhibit.rpc.core.heartbeat;

import java.util.TimerTask;

import com.exhibit.rpc.util.ConstantsUtil;
/**
 * @author: ankang
 */
public class HeartbeaTask extends TimerTask{

	private HeartbeaHandle handle;
	
	@Override
	public void run() {
		
		handle.execute(ConstantsUtil.HEARTBEAT_INVALID_DEFAULT);
	}
	public HeartbeaTask(HeartbeaHandle handle){
		this.handle = handle;
	}
}
