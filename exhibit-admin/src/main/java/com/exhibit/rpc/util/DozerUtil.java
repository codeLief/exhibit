package com.exhibit.rpc.util;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.dozer.Mapper;

/**
 * 
 * @author: ankang
 * @date: 2016-7-7 下午3:00:48
 * 
 *     <bean id="mapper" class="org.dozer.spring.DozerBeanMapperFactoryBean">
        <property name="mappingFiles">
            <list>
                <value>classpath*:dozer/*.xml</value>
            </list>
        </property>
    </bean>
    <bean id="dozerUtil" class="com.qding.project.monitor.util.DozerUtil">
        <property name="mapper" ref="mapper"/>
    </bean>
 * 
 * 
 */
@NoArgsConstructor
@Data
public class DozerUtil {

    private Mapper mapper;
    
    public  <T> List<T> transforList(Class<T> clazz, List<?> sources) {
        List<T> list = new ArrayList<>();
        for(Object o : sources) {
            T t = transfor(clazz, o);
            list.add(t);
        }
        return list;
    }

    public void transfor(Object target, Object source) {
        if(source == null || target == null) {
            return;
        }
        mapper.map(source, target);
    }

    public <T> T transfor(Class<T> target, Object source) {
        if(source == null) {
            try {
                return target.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mapper.map(source, target);
    }
}
