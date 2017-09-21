package com.exhibit.rpc.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.log4j.Log4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.exhibit.rpc.base.HttpSupport;
import com.exhibit.rpc.model.HessianConsumers;
import com.exhibit.rpc.model.HessianProvider;
import com.exhibit.rpc.model.RegisterServiceResponse;
import com.exhibit.rpc.service.ConsumersService;
import com.exhibit.rpc.service.ProviderDetailService;
import com.exhibit.rpc.service.ProviderService;
import com.exhibit.rpc.util.ConstantsUtil;
import com.exhibit.rpc.util.GsonUtil;

/**
 * 
 * @Description: 注册服务 
 */
@Log4j
@Controller
@RequestMapping("/registerService")
public class RegisterHessianServiceController extends HttpSupport {

	@Autowired
	private ProviderService providerService;
	
	@Autowired
	private ProviderDetailService detailService;
	
	@Autowired
	private ConsumersService consumersService;
	
	/**
	 * 
		 * @Description: 注册提供者 
	 */
	@RequestMapping(value = "/hessianProvider")
	@ResponseBody
	public RegisterServiceResponse hessianProvider() {
		
		RegisterServiceResponse response = new RegisterServiceResponse(ConstantsUtil.CODE_200);
		
		try {
			
			HessianProvider[] array = getParamTArray(ConstantsUtil.CONTEXT_KEY, HessianProvider.class);
			
			log.info("hessianProvider:" + GsonUtil.toJson(array));
			
			if(array != null && array.length > 0){
				List<String> list = new ArrayList<String>();
				
				for (HessianProvider hessianProvider : array) {
					
					String providerId = providerService.save(hessianProvider);
					
					list.add(detailService.save(hessianProvider, providerId, getParamInt(ConstantsUtil.HEARTBEAT_TIME)));
				}
				response.setLists(list);
			}
		} catch (Exception e) {
			log.error("注册提供者失败", e);
			response.setCode(ConstantsUtil.CODE_500);
		}
		return response;
	}
	
	/**
	 * 
		 * @Description: 销毁提供者 
	 */
	@RequestMapping(value = "/destroyHessianProvider")
	@ResponseBody
	public RegisterServiceResponse destroyHessianProvider() {
		
		try {
			String[] array = getParamTArray(ConstantsUtil.CONTEXT_KEY, String.class);
			
			System.out.println("destroyHessianProvider:" + GsonUtil.toJson(array));
			
			if(array != null && array.length > 0){
				
				detailService.destroy(Arrays.asList(array));
			}
			
			return new RegisterServiceResponse(ConstantsUtil.CODE_200);
		} catch (Exception e) {
			log.error("销毁提供者失败", e);
			return new RegisterServiceResponse(ConstantsUtil.CODE_500);
		}
	}
	
	/**
	 * 
		 * @Description: 注册消费者 
	 */
	@RequestMapping(value = "/hessianConsumers")
	@ResponseBody
	public RegisterServiceResponse hessianConsumers() {
		
		RegisterServiceResponse response = new RegisterServiceResponse(ConstantsUtil.CODE_200);
		
		try {
			HessianConsumers[] array = getParamTArray(ConstantsUtil.CONTEXT_KEY, HessianConsumers.class);
			
			log.info("hessianConsumers:" + GsonUtil.toJson(array));
			if(array != null && array.length > 0){
				
				List<String> list = new ArrayList<String>();
				
				for (HessianConsumers hc : array) {
					
					list.add(consumersService.save(hc, getParamInt(ConstantsUtil.HEARTBEAT_TIME)));
				}
				response.setLists(list);
			}
			
		} catch (Exception e) {
			log.error("注册消费者失败", e);
			response.setCode(ConstantsUtil.CODE_500);
		}
		return response;
	}
	
	/**
	 * 
		 * @Description: 销毁消费者 
	 */
	@RequestMapping(value = "/destroyHessianConsumers")
	@ResponseBody
	public RegisterServiceResponse destroyHessianConsumers() {
		
		try {
			String[] array = getParamTArray(ConstantsUtil.CONTEXT_KEY, String.class);
			
			System.out.println("destroyHessianConsumers:" + GsonUtil.toJson(array));
			
			if(array != null && array.length > 0){
				
				consumersService.destroy(Arrays.asList(array));
			}
			
			return new RegisterServiceResponse(ConstantsUtil.CODE_200);
		} catch (Exception e) {
			log.error("销毁消费者失败", e);
			return new RegisterServiceResponse(ConstantsUtil.CODE_500);
		}
	}
	public static void main(String[] args) throws IOException {
		
		/*ServerSocket soc = new ServerSocket(0);
		
		System.out.println(soc.getLocalPort());*/
		
		String url = "http://devboss.qdingnet.com/atbg/remote/activityRPCService";
		
		String regex = "[a-zA-z]+://[a-zA-z.]*";
		System.out.println(url.split(regex)[1]);
		
		Pattern p = Pattern.compile(regex);
		Matcher matcher = p.matcher(url);
		if(matcher.lookingAt()){
			url = url.split(regex)[1];
		}
		
		System.out.println(url);
		
		Pattern p1 = Pattern.compile("^/[a-zA-z-_]+");
		Matcher matcher1 = p1.matcher(url);
		
		System.out.println(matcher1.lookingAt());
		
		int end = matcher1.end();
		
		System.out.println(url.substring(0, end));
		System.out.println(url.substring(end));
		
//		System.out.println(url.split("/[a-zA-z-_]+")[1]);
		System.out.println(url.indexOf("/"));
		System.out.println(url.indexOf("/", 0));
		System.out.println(url.indexOf("/", 1));
		System.out.println(url.indexOf("/", 2));
		System.out.println(url.indexOf("/", 3));
		
		
	}
}
