package com.exhibit.rpc.core;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.exhibit.rpc.conf.ConfigAnnotation;
import com.exhibit.rpc.conf.HeAutowareConfig;
import com.exhibit.rpc.util.ConstantsUtil;
import com.google.common.collect.Maps;

/**
 * 
 * @Description: 公共配置
 */
public class HessianExposeSystemConfig {

	protected static final Logger LOGGER = LoggerFactory.getLogger(HessianExposeSystemConfig.class);
	
	private static final String CONF = "he.properties";
	
	private boolean isLoaded = Boolean.FALSE;
	
	private static HessianExposeSystemConfig INSTANCE = new HessianExposeSystemConfig();
	
	private Map<String, Object> allConfig = Maps.newHashMap();
	
	public static HessianExposeSystemConfig getInstance() {
		return INSTANCE;
	}
	
	private HessianExposeSystemConfig(){}
	
    public synchronized void loadConfig(String filePath) throws Exception {

        if (isLoaded) {
            return;
        }

        String filePathInternal = CONF;

        if (filePath != null) {

            filePathInternal = filePath;
        }

        HeAutowareConfig.autowareConfig(INSTANCE, filePathInternal);

        converConfigNameToMap();
        
        isLoaded = Boolean.TRUE;
    }
    
    private void converConfigNameToMap() throws Exception{
    	
    	try {
            Field[] fields = INSTANCE.getClass().getDeclaredFields();

            for (Field field : fields) {

                if (field.isAnnotationPresent(ConfigAnnotation.class)) {

                    if (Modifier.isStatic(field.getModifiers())) {
                        continue;
                    }
                    
                    ConfigAnnotation config = field.getAnnotation(ConfigAnnotation.class);
                    
                    String property = BeanUtils.getProperty(INSTANCE, field.getName());
                    
                    LOGGER.info(config.name() + ": " + property);
                    
                    allConfig.put(config.name(), property);
                    
                }
            }
        } catch (Exception e) {

            throw new Exception("error while autowire config file", e);
        }
    }
    
    @ConfigAnnotation(name = ConstantsUtil.SYSTEM_LOCAL, desc = "当前系统ip")
	private String currentSysIp;
    
    @ConfigAnnotation(name = ConstantsUtil.SYSTEM_PORT, desc = "当前应用端口号")
	private String currentAppPort;
    
    @ConfigAnnotation(name = ConstantsUtil.SYSTEM_APP_NAME, desc = "当前应用名")
	private String currentAppName;
	
    @ConfigAnnotation(name = ConstantsUtil.HE_SERVER_HOST, desc = "he服务器地址, he_server_host= 127.0.0.1:8088", require = true)
    private String serverHost;
    
    @ConfigAnnotation(name = ConstantsUtil.SYSTEM_APP_AUTHOR, desc = "当前应用负责人")
	private String author;
	
    @ConfigAnnotation(name = ConstantsUtil.HEARTBEAT_TIME, desc = "多长时间检测一次服务 秒")
    private int heartbeatTime = ConstantsUtil.HEARTBEAT_TIME_DEFAULT;
    
    @ConfigAnnotation(name = ConstantsUtil.SYSTEM_ENV, desc = "环境", defaultValue = ConstantsUtil.DEFAULT_ENV, require = true)
	private String env;
	
    @ConfigAnnotation(name = ConstantsUtil.SYSTEM_PID, desc = "jvmPid")
	private String jvmPid;
    
    @ConfigAnnotation(name = ConstantsUtil.RETRY_TIME, desc = "连接失败重试间隔时间 秒", defaultValue = "30")
    private int retryTime;
    
    @ConfigAnnotation(name = ConstantsUtil.RETRY_COUNT, desc = "连接失败重试次数 -1 无限重试", defaultValue = "-1")
    private int retryCount;
    
    @ConfigAnnotation(name = ConstantsUtil.RETRY_TIME_INCR, desc = "连接失败重试间隔时间递增量", defaultValue = "20")
    private int retryTimeIncr;
    
	public String getCurrentSysIp() {
		return currentSysIp;
	}

	public String getCurrentAppPort() {
		return currentAppPort;
	}

	public String getCurrentAppName() {
		return currentAppName;
	}

	public String getServerHost() {
		return serverHost;
	}

	public String getAuthor() {
		return author;
	}

	public String getEnv() {
		return env;
	}

	public String getJvmPid() {
		return jvmPid;
	}
    
    public int getRetryTime() {
		return retryTime;
	}

	public int getRetryCount() {
		return retryCount;
	}
	
	public int getRetryTimeIncr() {
		return retryTimeIncr;
	}

	public int getHeartbeatTime() {
		return heartbeatTime;
	}

	public String getValue(String key){
    	
    	Object value = allConfig.get(key);
    	
    	if(value != null)
    		return String.valueOf(value);
    	
    	return StringUtils.EMPTY;
    }
}
