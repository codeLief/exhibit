package com.exhibit.rpc.core.http;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;

import com.exhibit.rpc.util.ConstantsUtil;

public class HttpClientService {

	protected static final Logger LOGGER = LoggerFactory.getLogger(HttpClientService.class);
	
	public static String doService(String url, String reqParam) {

		HttpMethod curMethod = HttpMethod.valueOf(ConstantsUtil.POST);

		switch (curMethod) {
			case GET:
				return get(url, "?" + reqParam);
			case POST:
				return post(url, reqParam);
			default:
				return StringUtils.EMPTY;
		}
	}

	private static String get(String url, String reqParam) {
		LOGGER.debug(String.format("http client url:[%s] reqParam:[%s]", url, reqParam));
		return HttpClientUtils.sendGetRequest(reqParam, ConstantsUtil.ENCODE);
	}

	private static String post(String url, String reqParam) {
		LOGGER.info(String.format("http client url:[%s] reqParam:[%s]", url, reqParam));
		return HttpClientUtils.sendPostRequest(url, reqParam, true);
	}
}
