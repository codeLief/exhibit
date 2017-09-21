package com.exhibit.rpc.service;

import com.exhibit.rpc.base.BaseService;
import com.exhibit.rpc.model.Heartbeat;

public interface HeartbeaService extends BaseService<Heartbeat>{
	
	/**
	 * 
		 * @Description: 根据 heartbeatId 刷新状态
	     * @param heartbeatId
	     * @param status true 有效  false 无效
	 */
	public void refresh(String heartbeatId, boolean status);
	
}
