package com.exhibit.rpc.util;

import java.lang.management.ManagementFactory;
import java.net.URL;
import java.util.Iterator;
import java.util.Set;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.Query;

/**
 * 
 * @Description: tomcat util 
 * @author: ankang
 * @date: 2017-4-11 下午2:23:32
 */
public class TomcatUtils {
	
	private static final MBeanServer BEANSERVER = ManagementFactory.getPlatformMBeanServer();
	
	/**
	 * 
	 * @Description: 获取tomcat端口号 
	 */
	public static String getTomcatPort(){
		
		try {

			Set<ObjectName> objectNames = BEANSERVER.queryNames(new ObjectName("*:type=Connector,*"), null);
			Query.or(
						Query.match(Query.attr("protocol"), Query.value("HTTP/1.1")), 
						Query.or(Query.match(Query.attr("protocol"), Query.value("org.apache.coyote.http11.Http11NioProtocol")),//HTTP/1.1 nio 实现
								 Query.match(Query.attr("protocol"), Query.value("org.apache.coyote.http11.Http11AprProtocol"))//arp 实现
					 ));
			return objectNames.iterator().next().getKeyProperty("port");
		} catch (MalformedObjectNameException e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	/**
	 * 
	 * @Description: 通过类加载器获取最对地址然后截取出项目名 BOOT目录下无法获取 指定docBase无法获取 指定docBase无法获取
	 */
	public static String getWebAppNameByClass(){
		
		String u = null;
		
		URL url = Thread.currentThread().getContextClassLoader().getResource("/");
		if(url == null){
			
			return u;
		}
		
		u = url.toString();
		
		if(u.contains("webapps") 
				&& u.contains("WEB-INF")){
			
			u = u.split("/webapps|/WEB-INF/")[1];
		}
		
		return u;
	}
	
	/**
	 * 
	 * @Description: 通过xml配置文件获取 多个项目运行于同一个tomcat获取获取第一个，出系统项目以外
	 */
	public static String getWebAppNameByServerXml(){
		
		try {
			
			Set<ObjectName> beans = BEANSERVER.queryNames(new ObjectName("*:type=Host,*"), null);

			String localhost = beans.iterator().next().getKeyProperty("host");
			
			Set<ObjectName> modules = BEANSERVER.queryNames(new ObjectName("*:j2eeType=WebModule,*"), 
					null);
			
			Iterator<ObjectName> objectNames = modules.iterator();
			
			while (objectNames.hasNext()) {
				ObjectName objectName = objectNames.next();
				String appName = objectName.getKeyProperty("name");
				if(!appName.endsWith("examples")
						&& !appName.endsWith("host-manager")
						&& !appName.endsWith("docs")
						&& !appName.endsWith("manager")){
					
					return appName.split(localhost)[1];
				}
			}
			
		} catch (MalformedObjectNameException e) {
			e.printStackTrace();
		}
		return null;
	}
}
