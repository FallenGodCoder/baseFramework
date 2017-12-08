package com.tc.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;

/**
 * 
 * 该类用来获取配置文件中的配置
 *
 */
public class PropertyTools {

	// 配置文件名称
	private final static String PROPERY_NAME = "default.properties";

	// 静态资源文件
	private static Properties staticProperties;

	// 初始化变量
	static {
		// 如果静态存储
		if (staticProperties == null) {
			initPropertiy();
		}
	}

	/**
	 * 获取配置文件中的配置
	 * 
	 * @param key
	 *            配置名称
	 * @return
	 */
	public static String getValue(String key) {
		return staticProperties.getProperty(key);
	}

	/**
	 * 获取带参数的配置文件中的配置
	 * 
	 * @param key
	 *            配置名称
	 * @param args
	 *            参数
	 * @return
	 */
	public static String getValueWithArg(String key, Object... args) {
		String resultStr = staticProperties.getProperty(key);

		if (StringUtils.isEmpty(resultStr)) {
			resultStr = null;
		} else {
			resultStr = MessageFormat.format(resultStr, args);
		}

		return resultStr;
	}

	/**
	 * 存储MAP初始化方法
	 */
	private static synchronized void initPropertiy() {
		if (staticProperties == null) {
			try {
				// 实例化资源
				Resource resource = new ClassPathResource(PROPERY_NAME);
				// 实例化配置文件
				staticProperties = new Properties();
				// 读取资源数据
				staticProperties.load(resource.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
