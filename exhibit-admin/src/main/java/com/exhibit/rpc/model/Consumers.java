package com.exhibit.rpc.model;


import com.exhibit.rpc.base.BaseModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 
 * @author ankang
 * @version 1.0
 * @since 2017/04/25 14:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Consumers extends BaseModel {

	private static final long serialVersionUID = 5409185459234711691L;

	/**
	 * 
	 */
	private String providerId;
	/**
	 * 服务地址
	 */
	private String sUrl;
	/**
	 * 服务环境
	 */
	private long sEnv;
	/**
	 * 服务接口
	 */
	private String sInterface;
	/**
	 * 消费服务ip
	 */
	private String cIp;
	/**
	 * 消费服务名
	 */
	private String cAppName;
	/**
	 * 
	 */
	private int status;
	/**
	 * 消费人
	 */
	private String author;
	
	/**
	 * 
	 */
	private String heartbeatId;
	/**
	 * 
	 */
	private long destroyTime;
}