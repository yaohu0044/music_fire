package com.musicfire.common.utiles;

import org.apache.log4j.Logger;

import java.util.Properties;

/**
 * 文件配置信息拾取
 */
public class ConfigProperty {
	public static String getProperty(String entry) {
		return confProps.getProperty(entry);
	}

	private final static Properties confProps = new Properties();

	static {
		Logger log = Logger.getLogger(ConfigProperty.class);
		try {
			confProps.loadFromXML(ConfigProperty.class
					.getResourceAsStream("/sysconfig.xml"));
		} catch (Exception ex) {
			log.warn("Load Edrive Config file failed.", ex);
		}
	}
}
