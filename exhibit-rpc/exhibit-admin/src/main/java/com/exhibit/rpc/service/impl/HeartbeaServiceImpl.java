package com.exhibit.rpc.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exhibit.rpc.base.BaseServiceImpl;
import com.exhibit.rpc.dao.HeartbeatDAO;
import com.exhibit.rpc.model.Consumers;
import com.exhibit.rpc.model.Heartbeat;
import com.exhibit.rpc.model.ProviderDetail;
import com.exhibit.rpc.service.ConsumersService;
import com.exhibit.rpc.service.HeartbeaService;
import com.exhibit.rpc.service.ProviderDetailService;
import com.exhibit.rpc.util.StatusConstantsUtil;
import com.google.common.collect.Maps;


@Service
public class HeartbeaServiceImpl extends BaseServiceImpl<HeartbeatDAO, Heartbeat> implements HeartbeaService{

	@Autowired
	private ProviderDetailService detailService;
	
	@Autowired
	private ConsumersService consumersService;
	
	@Override
	public void refresh(String heartbeatId, boolean status) {
		
		Map<String, Object> params = Maps.newHashMap();
		params.put("heartbeatId", heartbeatId);
		
		final List<ProviderDetail> providers = detailService.query(params);
		
		int tm = status? StatusConstantsUtil.VALID_STATUS : StatusConstantsUtil.INVALID_STATUS;
		
		//刷新提供者
		for (ProviderDetail providerDetail : providers) {
			
			providerDetail.setLastCheckTime(System.currentTimeMillis());
			providerDetail.setStatus(tm);
			providerDetail.setUpdateAt(System.currentTimeMillis());
			detailService.modify(providerDetail);
		}
		
		//刷新消费者
		final List<Consumers> consumers = consumersService.query(params);
		for (Consumers con : consumers) {
			
			con.setUpdateAt(System.currentTimeMillis());
			con.setStatus(tm);
			consumersService.modify(con);
		}
	}
}
