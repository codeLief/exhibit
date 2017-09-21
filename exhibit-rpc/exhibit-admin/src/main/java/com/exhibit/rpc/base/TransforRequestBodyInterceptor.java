package com.exhibit.rpc.base;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.log4j.Log4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;
import com.exhibit.rpc.util.GsonUtil;
import com.google.common.collect.Maps;

/**
 * ?body={} 转换成 Map<String, Object>
 *
 */
@Log4j
public class TransforRequestBodyInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
    	return transferRequest(request, handler);
    }

    private boolean transferRequest(HttpServletRequest request, Object handler) throws Exception {
        Map<String, Object> params = Maps.newHashMap();
        String body = request.getParameter("body");
        if(StringUtils.isNotEmpty(body)) {
	    	JSONObject jsonObj = GsonUtil.stringToJSONOBject(body);
	    	if(jsonObj != null){
	    		Set<String> keySet = jsonObj.keySet();
		         for (String key : keySet) {
		             params.put(key, jsonObj.get(key));
		         }
		    	request.setAttribute("params", params);
		    	logPrint(request);
	    	}
        }
        
        return true;
    }
    /**
     * 
    	 * @Description: 日志输出 
         * @author: qd-ankang
         * @date: 2017-2-24 上午10:02:08
         * @param request
     */
    private void logPrint(HttpServletRequest request){
    	
    	Map<String, String[]> parameterMap = request.getParameterMap();
		Set<String> keySet = parameterMap.keySet();
		String uri = request.getRequestURI();
		StringBuffer toString = new StringBuffer(400);
		for (String key : keySet) {
			toString.append(key).append("=")
					.append(Arrays.toString(parameterMap.get(key))).append(",");
		}

		log.info("request [" + uri + " param : "
				+ toString.toString() + "]");
    }
}