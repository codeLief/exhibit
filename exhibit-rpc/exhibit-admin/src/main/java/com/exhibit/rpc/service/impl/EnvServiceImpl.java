package com.exhibit.rpc.service.impl;

import java.util.Map;

import lombok.extern.log4j.Log4j;

import org.springframework.stereotype.Service;

import com.exhibit.rpc.base.BaseServiceImpl;
import com.exhibit.rpc.dao.EnvDAO;
import com.exhibit.rpc.model.Env;
import com.exhibit.rpc.service.EnvService;
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
public class EnvServiceImpl extends BaseServiceImpl<EnvDAO, Env> implements EnvService {
	
	@Override
	public Env create(Env entity) {
		
		Map<String, Object> params = Maps.newHashMap();
		
		params.put("name", entity.getName());
		Env env = findByParams(params);
		
		//创建环境
		if(env == null){
			
			entity.setCreateAt(System.currentTimeMillis());
			save(entity);
			
			env = findById(entity.getId());
			log.info(String.format("create env [%s]", GsonUtil.toJson(env)));
		}
		
		return env;
	}
	
}