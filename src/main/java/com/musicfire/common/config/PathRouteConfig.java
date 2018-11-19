package com.musicfire.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class PathRouteConfig {
	private static String seperator = System.getProperty("file.separator");

	private static String winPath;

	private static String linuxPath;

	private static String shopPath;

	private static String headLinePath;

	private static String shopCategoryPath;

	private static Boolean isLogOn;

	@Value("${path.base.win}")
	public void setWinPath(String winPath) {
		PathRouteConfig.winPath = winPath;
	}

	@Value("${path.base.linux}")
	public void setLinuxPath(String linuxPath) {
		PathRouteConfig.linuxPath = linuxPath;
	}

	@Value("${path.relevant.shop}")
	public void setShopPath(String shopPath) {
		PathRouteConfig.shopPath = shopPath;
	}

	@Value("${path.relevant.headline}")
	public void setHeadLinePath(String headLinePath) {
		PathRouteConfig.headLinePath = headLinePath;
	}

	@Value("${shoplog.onoff}")
	public void setLogOn(Boolean isLogOn) {
		PathRouteConfig.isLogOn = isLogOn;
	}


	@Value("${path.relevant.shopcategory}")
	public void setShopCategoryPath(String shopCategoryPath) {
		PathRouteConfig.shopCategoryPath = shopCategoryPath;
	}

	public static String getImgBasePath() {
		String os = System.getProperty("os.name");
		String basePath = "";
		if (os.toLowerCase().startsWith("win")) {
			basePath = winPath;
		} else {
			basePath = linuxPath;
		}
		basePath = basePath.replace("/", seperator);
		log.info("basePath:{}",basePath);
		return basePath;
	}

	public static String getShopImagePath(long shopId) {
		String imagePath = shopPath + shopId + seperator;
		return imagePath.replace("/", seperator);
	}

	public static String getHeadLineImagePath() {
		return headLinePath.replace("/", seperator);
	}

	public static String getShopCategoryPath() {
		return shopCategoryPath.replace("/", seperator);
	}

	public static Boolean getIsLogOn() {
		return isLogOn;
	}


}
