package com.exhibit.rpc.service.impl;

import java.util.List;
import java.util.Map;

import lombok.extern.log4j.Log4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exhibit.rpc.base.BaseServiceImpl;
import com.exhibit.rpc.core.heartbeat.HeartbeatManager;
import com.exhibit.rpc.dao.ProviderDetailDAO;
import com.exhibit.rpc.model.HessianProvider;
import com.exhibit.rpc.model.Provider;
import com.exhibit.rpc.model.ProviderDetail;
import com.exhibit.rpc.service.ProviderDetailService;
import com.exhibit.rpc.service.ProviderService;
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
public class ProviderDetailServiceImpl extends BaseServiceImpl<ProviderDetailDAO, ProviderDetail> implements ProviderDetailService {
	
	@Autowired
	private ProviderService providerService;
	
	@Override
	public String save(HessianProvider hp, String providerId, int heartbeatTime) {
		
		Map<String, Object> params = Maps.newHashMap();
		
		params.put("providerId", providerId);
		params.put("sIp", hp.getS_ip());
		
		ProviderDetail providerDetail = findByParams(params);
		
		Provider provider = providerService.findById(providerId);
		
		String heartbeatId = HeartbeatManager.generateHeartbeatId(hp.getS_ip(), 
																	hp.getPort(), 
																 	provider.getSAppName(), 
																 	String.valueOf(provider.getSEnv()), 
																 	heartbeatTime);
		
		if(providerDetail == null){
			
			providerDetail = new ProviderDetail();
			providerDetail.setProviderId(providerId);
			providerDetail.setSDesc(hp.getS_desc());
			providerDetail.setSIp(hp.getS_ip());
			providerDetail.setSJvmId(Integer.valueOf(hp.getJvmPid()));
			providerDetail.setSPort(Integer.valueOf(hp.getPort()));
			providerDetail.setSReadTimeout(hp.getReadTimeout());
			providerDetail.setStatus(StatusConstantsUtil.VALID_STATUS);
			providerDetail.setCreateAt(System.currentTimeMillis());
			providerDetail.setHeartbeatId(heartbeatId);
			providerDetail.setLastCheckTime(System.currentTimeMillis());
			save(providerDetail);
			
			log.info(String.format("create sub provider [%s]", GsonUtil.toJson(providerDetail)));
		}else{
			
			providerDetail.setLastCheckTime(System.currentTimeMillis());
			providerDetail.setSJvmId(Integer.valueOf(hp.getJvmPid()));
			providerDetail.setSPort(Integer.valueOf(hp.getPort()));
			providerDetail.setHeartbeatId(heartbeatId);
			providerDetail.setSReadTimeout(hp.getReadTimeout());
			providerDetail.setStatus(StatusConstantsUtil.VALID_STATUS);
			providerDetail.setUpdateAt(System.currentTimeMillis());
			
			modifySelective(providerDetail);
			log.info(String.format("update sub provider [%s]", GsonUtil.toJson(providerDetail)));
		}
		
		return providerDetail.getId();
	}

	@Override
	public void destroy(List<String> ids) {
		
		List<ProviderDetail> list = queryByIds(ids);
		
		for (ProviderDetail providerDetail : list) {
			
			providerDetail.setDestroyTime(System.currentTimeMillis());
			providerDetail.setLastCheckTime(System.currentTimeMillis());
			providerDetail.setStatus(StatusConstantsUtil.INVALID_STATUS);
			providerDetail.setUpdateAt(System.currentTimeMillis());
			modifySelective(providerDetail);
		}
		
		HeartbeatManager.remove(list.get(0).getHeartbeatId());
	}
}