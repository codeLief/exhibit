package com.exhibit.rpc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.log4j.Log4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.exhibit.rpc.base.HttpSupport;
import com.exhibit.rpc.base.ModelResult;
import com.exhibit.rpc.model.Consumers;
import com.exhibit.rpc.model.Env;
import com.exhibit.rpc.model.Provider;
import com.exhibit.rpc.model.ProviderDetail;
import com.exhibit.rpc.model.ProviderVo;
import com.exhibit.rpc.service.ConsumersService;
import com.exhibit.rpc.service.EnvService;
import com.exhibit.rpc.service.ProviderDetailService;
import com.exhibit.rpc.service.ProviderService;
import com.exhibit.rpc.util.DozerUtil;
import com.google.common.collect.Maps;

@Log4j
@Controller
@RequestMapping("/show")
public class ShowHessianController extends HttpSupport{
	
	@Autowired
	private ProviderService providerService;
	
	@Autowired
	private ConsumersService consumersService;
	
	@Autowired
	private ProviderDetailService detailService;
	
	@Autowired
	private EnvService envService;
	
	@Autowired
	private DozerUtil dozerUtil;
	
	@RequestMapping(value = "/pageProvider")
	@ResponseBody
	public ModelResult<ProviderVo> pageProvider() {
		
		ModelResult<ProviderVo> result = new ModelResult<ProviderVo>(ModelResult.CODE_200);
		
		try {
			
			List<Provider> list = providerService.page(getParams(), getOffset(), getPageSize());
			
			List<ProviderVo> hps = dozerUtil.transforList(ProviderVo.class, list);
			
			Map<String, Object> params = Maps.newHashMap();
			
			for (ProviderVo providerVo : hps) {
				
				params.put("providerId", providerVo.getId());
				
				providerVo.setProviderCount(detailService.count(params));
				
				providerVo.setConsumerCount(consumersService.count(params));
			}
			
			result.setData("list", hps);
			result.setData("total", providerService.count(getParams()));
		} catch (Exception e) {
			log.error("查询失败", e);
			result.setCode(ModelResult.CODE_500);
		}
		return result;
	}
	
	@RequestMapping(value = "/pageConsumer")
	@ResponseBody
	public ModelResult<Consumers> pageConsumer() {
		
		ModelResult<Consumers> result = new ModelResult<Consumers>(ModelResult.CODE_200);
		
		try {
			result.setData("list", consumersService.page(getParams(), getOffset(), getPageSize()));
			result.setData("total", consumersService.count(getParams()));
		} catch (Exception e) {
			log.error("查询失败", e);
			result.setCode(ModelResult.CODE_500);
		}
		return result;
	}
	
	@RequestMapping(value = "/listEnv")
	@ResponseBody
	public ModelResult<Env> listEnv() {
		
		ModelResult<Env> result = new ModelResult<Env>(ModelResult.CODE_200);
		
		try {
			
			List<Env> list = envService.query(new HashMap<String, Object>());
			
			result.setList(list);
			
		} catch (Exception e) {
			log.error("查询失败", e);
			result.setCode(ModelResult.CODE_500);
		}
		return result;
	}
	
	@RequestMapping(value = "/listProviderDetail")
	@ResponseBody
	public ModelResult<ProviderDetail> listProviderDetail() {
		
		ModelResult<ProviderDetail> result = new ModelResult<ProviderDetail>(ModelResult.CODE_200);
		
		try {
			
			result.setData("list", detailService.page(getParams(), getOffset(), getPageSize()));
			result.setData("total", detailService.count(getParams()));
			
		} catch (Exception e) {
			log.error("查询失败", e);
			result.setCode(ModelResult.CODE_500);
		}
		return result;
	}
	
	@RequestMapping(value = "/removeProviderDetail")
	@ResponseBody
	public ModelResult<?> removeProviderDetail() {
		
		ModelResult result = new ModelResult(ModelResult.CODE_200);
		
		try {
			detailService.remove(getParamStr("proId"));
		} catch (Exception e) {
			log.error("删除提供者失败", e);
			result.setCode(ModelResult.CODE_500);
		}
		return result;
	}
	
	@RequestMapping(value = "/removeConsumer")
	@ResponseBody
	public ModelResult<?> removeConsumer() {
		
		ModelResult result = new ModelResult(ModelResult.CODE_200);
		
		try {
			consumersService.remove(getParamStr("conId"));
		} catch (Exception e) {
			log.error("删除提供者失败", e);
			result.setCode(ModelResult.CODE_500);
		}
		return result;
	}
}
