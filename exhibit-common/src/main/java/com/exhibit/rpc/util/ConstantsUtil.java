package com.exhibit.rpc.util;

/**
 * @author: ankang
 */
public class ConstantsUtil {

	public static final String DEFAULT_ENV = "default_env";
	
	public static final String HE_REGISTER_URL = "/exhibit-admin/registerService";
	
	public static final String HE_REGISTER_PROVIDER = "/hessianProvider";
	
	public static final String HE_REGISTER_CONSUMER = "/hessianConsumers";
	
	public static final String HE_DESTROY_PROVIDER = "/destroyHessianProvider";
	
	public static final String HE_DESTROY_CONSUMER = "/destroyHessianConsumers";
	
	public static final String SYSTEM_LOCAL = "system_local";
	
	public static final String SYSTEM_PORT = "system_port";
	
	public static final String SYSTEM_APP_NAME = "system_app_name";
	
	public static final String HE_SERVER_HOST = "he_server_host";
	
	public static final String SYSTEM_APP_AUTHOR = "system_app_author";
	
	public static final String SYSTEM_ENV = "system_env";
	
	public static final String SYSTEM_PID = "system_pid";
	
	public static final String HEARTBEAT_TIME = "heartbeat_time";
	
	public static final String RETRY_TIME = "retry_time";
	
	public static final String RETRY_TIME_INCR = "retry_time_incr";
	
	public static final String RETRY_COUNT = "retry_count";
	
	public static final String BODY = "body";
	
	public static final String ENCODE = "UTF-8";
	
	public static final String POST = "POST";
	
	public static final String GET = "GET";
	
	public static final String CONTEXT_KEY = "context";
	
	public static final String HEARTBEAT_KEY = "heartbeat";
	
	/**
	 * 心跳检测默认间隔时间
	 */
	public static final int HEARTBEAT_TIME_DEFAULT = 60;
	
	/**
	 * 心跳检测连续失效次数，打到即判定此连接断开
	 */
	public static final int HEARTBEAT_INVALID_DEFAULT = 3;
	
	/**
	 * MD5编码
	 */
	public static final String CODE_MD5 				= "MD5";
	
	/**
	 * 竖线分隔符
	 */
	public static final String VERTICALLINE 			= "|";
	
	public static final String DIAGONAL = "/";
	
	public static final String PROTOCOL_HEAD = "http://";
	
	public static final String COLON = ":";
	
	/**
	 * 逗号分隔符
	 */
	public static final String COMMA 					= ",";
	/**
	 * 竖线分隔符的正则表达式
	 */
	public static final String VERTICALLINE_REGEX 		= "\\|";
	
	public static final String WEB_URL_REGEX = "[a-zA-z]+://[a-zA-z.]*";
	
	public static final String WEB_NAME_REGEX = "^/[a-zA-z-_]+";
	
    public static final int CODE_200 = 200;
    public static final int CODE_500 = 500;
}
