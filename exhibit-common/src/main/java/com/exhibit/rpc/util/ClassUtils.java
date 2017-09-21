package com.exhibit.rpc.util;

import java.lang.reflect.Field;

import org.apache.commons.lang3.StringUtils;

/**
 * 类工具
 * 
 * @author ankang
 */
public class ClassUtils {

    /**
     * 由Get Method名称获取Field名
     * 
     * @return
     */
    public static String getFieldNameByGetMethodName(String methodName) {

        int prefixIndex = 0;

        // 必须以get或is开始的
        if (methodName.startsWith("get")) {
            prefixIndex = 3;

        } else if (methodName.startsWith("is")) {

            prefixIndex = 2;

        } else {

            return null;
        }

        String fieldName = methodName.substring(prefixIndex);
        if (fieldName.length() >= 1) {
            String firstCharStr = String.valueOf(fieldName.charAt(0))
                    .toLowerCase();
            if (fieldName.length() > 1) {
                fieldName = firstCharStr + fieldName.substring(1);
            } else {
                fieldName = firstCharStr.toLowerCase();
            }
        }

        return fieldName;
    }

    public static void autowareDefault(Object obj) throws Exception{
    	
    	if (obj == null) {
            throw new Exception("cannot autoware null");
        }
    	
    	try {
    		
			Field[] fields = obj.getClass().getDeclaredFields();
			
			for (Field field : fields) {
				
				field.setAccessible(true);
				
				Object value = field.get(obj);
				
				if(value == null){
					setFieldValeByType(field, obj, StringUtils.EMPTY);
				}
			}
			
		} catch (Exception e) {
			
			throw new Exception("error while autowire obj", e);
		}
    }
    
    /**
     * 根据Field类型设置值
     * 
     * @param field
     */
    public static void setFieldValeByType(Field field, Object obj, String value)
            throws Exception {

        Class<?> type = field.getType();

        String typeName = type.getName();

        if (typeName.equals("int")) {

            if (value.equals("")) {
                value = "0";
            }
            field.set(obj, Integer.valueOf(value));

        } else if (typeName.equals("long")) {

            if (value.equals("")) {
                value = "0";
            }
            field.set(obj, Long.valueOf(value));

        } else if (typeName.equals("boolean")) {

            if (value.equals("")) {
                value = "false";
            }
            field.set(obj, Boolean.valueOf(value));

        } else if (typeName.equals("double")) {

            if (value.equals("")) {
                value = "0.0";
            }
            field.set(obj, Double.valueOf(value));

        } else {

            field.set(obj, value);
        }
    }

    /**
     * 根据Field类型返回值
     * 
     * @param field
     */
    public static Object getValeByType(Class<?> type, Object value)
            throws Exception {

        // 预处理
        if (!(value instanceof String)) {
            value = "";
        }

        // trim
        String dataValue = (String) value;
        dataValue = dataValue.trim();

        // process
        String typeName = type.getName();
        typeName = typeName.toLowerCase();

        if (typeName.equals("int") || typeName.equals("java.lang.integer")) {

            if (value.equals("")) {
                value = "0";
            }

            return Integer.valueOf(dataValue);

        } else if (typeName.equals("long") || typeName.equals("java.lang.long")) {

            if (value.equals("")) {
                value = "0";
            }

            return Long.valueOf(dataValue);

        } else if (typeName.equals("boolean")
                || typeName.equals("java.lang.boolean")) {

            if (value.equals("")) {
                value = "false";
            }

            return Boolean.valueOf(dataValue);

        } else if (typeName.equals("double")
                || typeName.equals("java.lang.double")) {

            if (value.equals("")) {
                value = "0.0";
            }

            return Double.valueOf(dataValue);

        } else {

            return value;
        }
    }
}
