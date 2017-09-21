package com.exhibit.rpc.conf;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 配置文件中的项标注
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ConfigAnnotation {

    String name() default "";

    String desc() default "";
    
    boolean require() default false;
    
    String defaultValue() default "";
}