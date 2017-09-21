package com.exhibit.rpc.conf;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Properties;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.exhibit.rpc.util.ClassUtils;
import com.exhibit.rpc.util.ConfigLoaderUtils;
import com.exhibit.rpc.util.ConstantsUtil;
import com.exhibit.rpc.util.IPAddrUtil;
import com.exhibit.rpc.util.JVMUtil;
import com.exhibit.rpc.util.TomcatUtils;
/**
 * @author: ankang
 */
public final class HeAutowareConfig {

	protected static final Logger LOGGER = LoggerFactory.getLogger(HeAutowareConfig.class);

	/**
     * 先用TOMCAT模式进行导入配置文件，若找不到，则用项目目录模式进行导入
     */
    private static Properties getProperties(final String propertyFilePath) {

        try {

            // 使用全路径的配置文件载入器
            return ConfigLoaderUtils.loadConfig(propertyFilePath);
        } catch (Exception e) {

            try {

                // 只用文件名 来载入试试
                String filename = FilenameUtils.getName(propertyFilePath);
                return ConfigLoaderUtils.loadConfig(filename);

            } catch (Exception e1) {

                LOGGER.error(String.format("read properties file %s error", propertyFilePath), e1);
            }

        }
        return null;
    }
    
    private static void autowareConfig(final Object obj, Properties prop) throws Exception {

        if (null == prop || obj == null) {
            throw new Exception("cannot autowareConfig null");
        }

        try {

            Field[] fields = obj.getClass().getDeclaredFields();

            for (Field field : fields) {

                if (field.isAnnotationPresent(ConfigAnnotation.class)) {

                    if (Modifier.isStatic(field.getModifiers())) {
                        continue;
                    }
                    
                    String value;
                    
                    ConfigAnnotation config = field.getAnnotation(ConfigAnnotation.class);
                    String name = config.name();
                    String defaultValue = config.defaultValue();
                    
                    String valueByJM = getValueByJM(name);
                    
                    value = prop.getProperty(name, StringUtils.isNotEmpty(valueByJM)? valueByJM : defaultValue);
                    
                    if (StringUtils.isNotEmpty(value)) {

                        try {
                        	
                        	field.setAccessible(true);
                        	
                        	ClassUtils.setFieldValeByType(field, obj, value);
                        } catch (Exception e) {

                            LOGGER.error(String.format("invalid config: %s", name), e);
                        }
                    }
                }
            }
        } catch (Exception e) {

            throw new Exception("error while autowire config file", e);
        }
    }
    
    /**
     * 自动导入某个配置文件
     *
     * @throws Exception
     */
    public static void autowareConfig(final Object obj, final String propertyFilePath) throws Exception {

        // 读配置文件
        Properties prop = getProperties(propertyFilePath);
        if (null == prop || obj == null) {
            throw new Exception("cannot autowareConfig " + propertyFilePath);
        }

        autowareConfig(obj, prop);
    }
    
    private static String getValueByJM(String name){
    	
    	if(ConstantsUtil.SYSTEM_LOCAL.equals(name)){
    		return IPAddrUtil.localAddress();
    	}else if(ConstantsUtil.SYSTEM_PORT.equals(name)){
    		return TomcatUtils.getTomcatPort();
    	}else if(ConstantsUtil.SYSTEM_APP_NAME.equals(name)){
//    		String appName = TomcatUtils.getWebAppNameByClass();
//    		if(StringUtils.isEmpty(appName)) appName = TomcatUtils.getWebAppNameByServerXml();
//    		SpringApplicationContextUtil.getApplicationContext().getApplicationName();
    		return TomcatUtils.getWebAppNameByServerXml();
    	}else if(ConstantsUtil.SYSTEM_PID.equals(name)){
    		return JVMUtil.getJVMPID();
    	}
    	
    	return StringUtils.EMPTY;
    }
}
