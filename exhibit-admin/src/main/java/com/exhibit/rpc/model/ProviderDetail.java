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
public class ProviderDetail extends BaseModel {

	private static final long serialVersionUID = 5409185459234711691L;

	/**
	 * 
	 */
	private String providerId;
	/**
	 * ip
	 */
	private String sIp;
	/**
	 * 服务端口
	 */
	private int sPort;
	/**
	 * 接口超时时间
	 */
	private String sReadTimeout;
	/**
	 * 描述
	 */
	private String sDesc;
	/**
	 * 
	 */
	private int sJvmId;
	/**
	 * 最后检查时间
	 */
	private long lastCheckTime;
	/**
	 * 注销时间
	 */
	private long destroyTime;
	/**
	 * 
	 */
	private String heartbeatId;
	/**
	 * 101 正常
	 */
	private int status;
}