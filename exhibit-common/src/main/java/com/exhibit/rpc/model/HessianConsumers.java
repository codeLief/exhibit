package com.exhibit.rpc.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import com.exhibit.rpc.conf.ConfigAnnotation;
import com.exhibit.rpc.util.ConstantsUtil;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HessianConsumers implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 消费地址
	 */
	@ConfigAnnotation(desc = "地址")
	private String s_url;
	
	/**
	 * 提供者接口名
	 */
	@ConfigAnnotation(desc = "提供者接口名")
	private String s_interface;
	
	/**
	 * 消费者
	 */
	@ConfigAnnotation(desc = "消费者", name = ConstantsUtil.SYSTEM_LOCAL)
	private String c_ip;
	
	@ConfigAnnotation(desc = "消费者端口号", name = ConstantsUtil.SYSTEM_PORT)
	private String c_port;
	
	/**
	 * 环境
	 */
	@ConfigAnnotation(desc = "环境", name = ConstantsUtil.SYSTEM_ENV)
	private String env = ConstantsUtil.DEFAULT_ENV;
	
	/**
	 * jvmPid
	 */
	@ConfigAnnotation(desc = "jvmPid", name = ConstantsUtil.SYSTEM_PID)
	private String jvmPid;
	
	/**
	 * 消费者应用名
	 */
	@ConfigAnnotation(desc = "消费者应用名", name = ConstantsUtil.SYSTEM_APP_NAME)
	private String c_appName; 
	
	/**
	 * 消费者 姓名
	 */
	@ConfigAnnotation(desc = "作者", require = false, name = ConstantsUtil.SYSTEM_APP_AUTHOR)
	private String author;
	
}
