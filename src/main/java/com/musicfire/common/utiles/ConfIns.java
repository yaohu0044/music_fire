package com.musicfire.common.utiles;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfIns {
	public String getValue(String key){
		return getValue(key, null);
	}

	public String getValue(String key, String conf){
		InputStream inputStream = this.getClass().getClassLoader()
				.getResourceAsStream(ObjUtil.ifNull(conf, "conf.properties"));
		Properties p = new Properties();
		try {
			p.load(inputStream);
			inputStream.close();
		} catch (IOException e1) {
			e1.printStackTrace();

			if (inputStream != null)
				try {
					inputStream.close();
				} catch (Exception e) {

				}
		}
		return p.getProperty(key);
	}
}
