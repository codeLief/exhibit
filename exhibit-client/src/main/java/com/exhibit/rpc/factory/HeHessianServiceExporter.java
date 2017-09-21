package com.exhibit.rpc.factory;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.util.NestedServletException;

import com.exhibit.rpc.core.HeClient;

/**
 * Created by qd on 2016/1/13.
 */

public class HeHessianServiceExporter extends HessianServiceExporter implements InitializingBean{

	protected static final Logger LOGGER = LoggerFactory.getLogger(HeHessianServiceExporter.class);
	
	private String methodUrl;
	
	private String serviceDesc;
	
    /**
     * Processes the incoming Hessian request and creates a Hessian response.
     */
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!"POST".equals(request.getMethod())) {
            throw new HttpRequestMethodNotSupportedException(request.getMethod(),
                    new String[] {"POST"}, "HessianServiceExporter only supports POST requests");
        }

        response.setContentType(CONTENT_TYPE_HESSIAN);
        try {
            invoke(request.getInputStream(), response.getOutputStream());
        }
        catch (Throwable ex) {
            throw new NestedServletException("Hessian skeleton invocation failed", ex);
        }
    }
	
	@PostConstruct
	public void addProvider(){
		//整理服务提供者数据
		HeClient.addProvider(methodUrl, null, null, null, getServiceInterface().getCanonicalName(), null, serviceDesc, null);
	}
	
	public void setMethodUrl(String methodUrl) {
		this.methodUrl = methodUrl;
	}

	public void setServiceDesc(String serviceDesc) {
		this.serviceDesc = serviceDesc;
	}
}
