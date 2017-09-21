package com.exhibit.rpc.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class DestroyHessianConsumers implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 接口名
	 */
	private String sInterface;
	
	/**
	 * 消费者ip
	 */
	private String cIp;
	
	/**
	 * jvmPid
	 */
	private String jvmPid;
	
	/**
	 * 环境
	 */
	private String env;
}
