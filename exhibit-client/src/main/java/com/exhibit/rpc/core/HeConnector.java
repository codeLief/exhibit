package com.exhibit.rpc.core;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.exhibit.rpc.core.http.HttpClientService;
import com.exhibit.rpc.util.ConstantsUtil;
import com.exhibit.rpc.util.GsonUtil;
/**
 * @author: ankang
 */
public class HeConnector {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(HeConnector.class);
	
	private static String BASEURL = StringUtils.EMPTY;
	
	private static int HEARTBEATTIME = ConstantsUtil.HEARTBEAT_TIME_DEFAULT;
	
	private static void init() throws Exception{
		
		if(BASEURL.isEmpty()){
			
			HessianExposeSystemConfig config = HeCoreFactory.getHessianExposeSystemConfig();
			BASEURL = config.getServerHost();
			HEARTBEATTIME = config.getHeartbeatTime();
		}
	}
	public static <T> T submit(String methodUrl, List<?> data, Class<T> clazz) throws Exception{
		
		init();
		
		if(BASEURL.isEmpty()){
			throw new Exception("server host empty");
		}
		if(BASEURL.endsWith(ConstantsUtil.DIAGONAL)){
			BASEURL = BASEURL.substring(0, BASEURL.length() - 1);
		}
		StringBuffer urlSb = new StringBuffer();
		
		if(!BASEURL.startsWith(ConstantsUtil.PROTOCOL_HEAD)){
			urlSb.append(ConstantsUtil.PROTOCOL_HEAD);
		}
		urlSb.append(BASEURL)
			.append(ConstantsUtil.HE_REGISTER_URL);
		
		if(methodUrl.startsWith(ConstantsUtil.DIAGONAL)){
			urlSb.append(methodUrl);
		}else{
			urlSb.append(ConstantsUtil.DIAGONAL)
				.append(methodUrl);
		}
		StringBuffer bodySb = new StringBuffer();
		
		bodySb.append("body={")
			.append(ConstantsUtil.CONTEXT_KEY)
			.append(ConstantsUtil.COLON)
			.append(GsonUtil.toJson(data))
			.append(ConstantsUtil.COMMA)
			.append(ConstantsUtil.HEARTBEAT_TIME)
			.append(ConstantsUtil.COLON)
			.append(HEARTBEATTIME)
			.append("}");
		
		String result = HttpClientService.doService(urlSb.toString(), bodySb.toString());
		
		LOGGER.info(String.format("http client method:[%s] result:[%s]", methodUrl, result));
		
		return GsonUtil.fromJson(result, clazz);
	}
}