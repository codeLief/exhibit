package com.exhibit.rpc.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.exhibit.rpc.conf.ConfigAnnotation;
import com.exhibit.rpc.util.ConstantsUtil;

/**
 * 
 * @Description: 注册提供方 
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HessianProvider implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 暴露地址 相对地址
	 */
	@ConfigAnnotation(desc = "暴露地址")
	private String s_url;
	
	/**
	 * 服务器ip地址
	 */
	@ConfigAnnotation(desc = "服务器ip", name = ConstantsUtil.SYSTEM_LOCAL)
	private String s_ip;
	
	/**
	 * 环境
	 */
	@ConfigAnnotation(desc = "环境", name = ConstantsUtil.SYSTEM_ENV)
	private String env;
	
	/**
	 * 端口号
	 */
	@ConfigAnnotation(desc = "端口号", name = ConstantsUtil.SYSTEM_PORT)
	private String port;
	
	/**
	 * jvmPid
	 */
	@ConfigAnnotation(desc = "jvmPid", name = ConstantsUtil.SYSTEM_PID)
	private String jvmPid;
	
	/**
	 * 应用名
	 */
	@ConfigAnnotation(desc = "应用名", name = ConstantsUtil.SYSTEM_APP_NAME)
	private String s_appName;
	
	/**
	 * 接口名
	 */
	@ConfigAnnotation(desc = "接口名")
	private String s_interface;
	
	/**
	 * 超时时间
	 */
	@ConfigAnnotation(desc = "超时时间", require = false)
	private String readTimeout;
	
	/**
	 * 接口描述
	 */
	@ConfigAnnotation(desc = "接口描述", require = false)
	private String s_desc;
	
	/**
	 * 	方法列表
	 */
	@ConfigAnnotation(desc = "方法列表", require = false)
	private String s_method;
	
	/**
	 * 作者
	 */
	@ConfigAnnotation(desc = "作者", require = false, name = ConstantsUtil.SYSTEM_APP_AUTHOR)
	private String author;
	
}
