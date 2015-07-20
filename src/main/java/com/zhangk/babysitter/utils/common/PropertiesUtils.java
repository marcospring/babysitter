package com.zhangk.babysitter.utils.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.util.PropertiesPersister;

/**
 * Properties Util函数.
 * @author XinWang
 *
 */
public class PropertiesUtils {

	private static final String DEFAULT_ENCODING = "UTF-8";
	
	private static String[] resourcesPaths = new String[]{"/config.properties", "/api.properties"};

	private static Log log = LogFactory.getLog(PropertiesUtils.class);

	private static PropertiesPersister propertiesPersister = new DefaultPropertiesPersister();
	
	private static ResourceLoader resourceLoader = new DefaultResourceLoader();
	
	private static Properties properties;
	
//	private static Properties staticProperties;

	/**
	 * 载入多个properties文件, 相同的属性在最后载入的文件中的值将会覆盖之前的载入.
	 * 文件路径使用Spring Resource格式, 文件编码使用UTF-8.
	 * 
	 * @see org.springframework.beans.factory.config.PropertyPlaceholderConfigurer
	 */
	public static Properties loadProperties(String... resourcesPaths) throws IOException {
		Properties props = new Properties();

		for (String location : resourcesPaths) {

			log.debug("Loading properties file from:" + location);

			InputStream is = null;
			try {
				Resource resource = resourceLoader.getResource(location);
				is = resource.getInputStream();
				propertiesPersister.load(props, new InputStreamReader(is, DEFAULT_ENCODING));
			} catch (IOException ex) {
				log.info("Could not load properties from classpath:" + location + ": " + ex.getMessage());
			} finally {
				if (is != null) {
					is.close();
				}
			}
		}
		return props;
	}
	
	public static Properties load(String path){
		Properties p = new Properties();
		try {
			InputStream in  = ClassLoader.getSystemResourceAsStream(path);
			p.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return p;
	}
	
	public static String getString(String key) {
		try {
			if(properties == null) {
				properties = PropertiesUtils.loadProperties(resourcesPaths);
			}
			return properties.getProperty(key).trim();
		} catch (Exception e) { }
		return "";
	}
	
//	public static String getStaticServer(String key) {
//		try {
//			if(staticProperties == null) {
//				staticProperties = PropertiesUtils.loadProperties("/config/static.properties");
//			}
//			String value = new String((staticProperties.getProperty(key)).getBytes("ISO8859_1"), "utf-8").trim();
//			log.debug(key + ":" + value);
//			return value;
//		} catch (Exception e) { 
//			log.error("读取 static.properties 配置文件失败.", e);
//		}
//		return "";
//	}
	
	/**
	 * 获取整型配置项.
	 * @param key
	 * @param defaultVal
	 * @return
	 */
	public static Integer getInt(String key, Integer defaultVal) {
		try {
			String valStr = getString(key);
			return Integer.parseInt(valStr.trim());
		} catch (Exception e) {	}
		return defaultVal;
	}
	
	/**
	 * 获取布尔配置.
	 * @param key
	 * @param defaultVal
	 * @return
	 */
	public static boolean getBoolean(String key, boolean defaultVal) {
		try {
			String valStr = getString(key);
			return Boolean.parseBoolean(valStr.trim());
		} catch (Exception e) {	}
		return defaultVal;
	}
	
	/**
	 * 获取字符型配置项.
	 * @param key
	 * @param defaultVal
	 * @return
	 */
	public static String getString(String key, String defaultVal) {
		String val = getString(key).trim();
		if(StringUtils.isNotBlank(val)){
			return val;
		}
		return defaultVal;
	}
}
