package com.exhibit.rpc.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import com.caucho.hessian.client.HessianProxyFactory;
import com.exhibit.rpc.core.HeClient;

public class HeHessianProxyFactory implements FactoryBean<Object>, InitializingBean{

	protected static final Logger LOGGER = LoggerFactory.getLogger(HeHessianProxyFactory.class);
	
	private String serviceUrl;
	
	private Class<?> serviceInterface;
	
    private int readTimeout = 15000;
    
    private Boolean overloadEnabled = true;

    private HessianProxyFactory proxyFactory = new HessianProxyFactory();
    
    private Object hassionObject;

	@Override
	public void afterPropertiesSet() throws Exception {
		
		proxyFactory.setChunkedPost(false);
		proxyFactory.setOverloadEnabled(overloadEnabled);
		proxyFactory.setReadTimeout(getReadTimeout());

		hassionObject = proxyFactory.create(getServiceInterface(), serviceUrl);
		
		HeClient.addConsumer(serviceUrl, serviceInterface.getCanonicalName(), null, null, null, null);
	}
	
	@Override
	public Object getObject() throws Exception {
		
		return hassionObject;
	}

	@Override
	public Class<?> getObjectType() {
		return getServiceInterface();
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public String getServiceUrl() {
		return serviceUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	public Class<?> getServiceInterface() {
		return serviceInterface;
	}
	
	public void setServiceInterface(Class<?> serviceInterface) {
		this.serviceInterface = serviceInterface;
	}
	
	public int getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}

	public Boolean getOverloadEnabled() {
		return overloadEnabled;
	}

	public void setOverloadEnabled(Boolean overloadEnabled) {
		this.overloadEnabled = overloadEnabled;
	}

}
