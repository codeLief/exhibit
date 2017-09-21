package com.exhibit.rpc.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class DestroyHessianProvider implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 服务器ip地址
	 */
	private String sIp;
	
	/**
	 * 端口号
	 */
	private String port;
	
	/**
	 * 应用名
	 */
	private String sAppName;
	
	/**
	 * jvmPid
	 */
	private String jvmPid;
	
	/**
	 * 接口名
	 */
	private String sInterface;
	
	/**
	 * 环境
	 */
	private String env;
}
