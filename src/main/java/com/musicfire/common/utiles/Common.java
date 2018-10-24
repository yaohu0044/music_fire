package com.musicfire.common.utiles;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * session和cookie的操作类
 * </p>
 * 
 * @version 1.0.0
 */
public class Common {

	static Logger logger = Logger.getLogger(Common.class);

	@Autowired
	private static HttpServletRequest request;

	private static final String[] VIDEO = {"MP4"};
	private static final String[] IMAGE = {"JPEG","BMP","JPG","PNG","TIF","GIF","PCX","TGA","EXIF","FPX","SVG","PSD","CDR","PCD","DXF","UFO","EPS","AI","RAW","WMF","WEBP"};

	public static enum Format{
		VIDEO,IMAGE
	}

	public static List<Map<String,Object>> getFileList(String file,Format ft){
		if(IsEmpty.Is(file)){
			String[] files = file.split(",");
			List<Map<String,Object>> filelist = new ArrayList<>();
			try {
				for (String fl : files) {
					String[] fileattr = fl.split("@");
					if(fileattr.length!=2) break;
					Map<String, Object> map = new HashMap<>();
					map.put("format", fileattr[0]);
					map.put("url", fileattr[1]);
					if (Format.VIDEO == ft) {
						for (String vi : VIDEO) {
							if (fileattr[0].trim().toUpperCase().equals(vi)) {
								filelist.add(map);
								break;
							}
						}
					} else {
						for (String vi : IMAGE) {
							if (fileattr[0].trim().toUpperCase().equals(vi)) {
								filelist.add(map);
								break;
							}
						}
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return filelist;
		}else {
			return new ArrayList<>();
		}
	}

	/**
	 * 替换加深字段
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String,Object>> SearchStyle(List<Map<String,Object>> list,String search,String ... fields) throws Exception{
		String style=Common.getDomain("search");
		if(!IsEmpty.Is(style)){
			style = "<span style='color:red;'>SEARCH</span>";
		}
		for(Map<String,Object> map:list){
			for(String field:fields){
				if(IsEmpty.Is(map,field)){
					style=style.replace("SEARCH",search);
					map.put(field+"_new",map.get(field).toString().replace(search,style));
				}
			}
		}
		return list;
	}


	public static String createIDCard() {
		StringBuilder sb = new StringBuilder("LS");

		// 获取生日
		Calendar cal = Calendar.getInstance();
		Long MAX = cal.getTimeInMillis();
		Random random = new Random();
		int randomNumber = random.nextInt(630720000) + 1;

		long differ = Long.valueOf(String.valueOf(randomNumber)) * 1000;

		cal.setTimeInMillis((MAX - differ));
		String date = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(cal
				.getTime());
		date = date.substring(0, date.length() - 1);
		sb.append(date);
		return sb.toString();
	}

	/**
	 * 获取服务域名
	 * 
	 * @param key
	 *            包括userDomain，messageDomain,appDaomain
	 * @return
	 */
	public static String getDomain(String key) {
		return ConfigProperty.getProperty(key);
	}

	public static String getTomcat(){
		return System.getProperty("catalina.home");	//tomcat路径
	}

	public static String getWebPath(String path){
		String tomcat = getTomcat() + File.separator + "webapps";
		if(path!=null && !"".equals(path.trim())){
			tomcat += File.separator + path;
		}
		return tomcat ;
	}

	/**
	 * 资源统一存放路径
	 * @return
	 */
	public static String getFilePath(){
		return  getWebPath(Common.getDomain("FILE_INTER_PATH"));
	}

	/**
	 * 获取文件服务器的资源路径
	 * @param request
	 * @return
	 */
	public static String getServiceFilePath(HttpServletRequest request){
		String basePath = request.getScheme()+"://"+request.getServerName();
		int port = request.getServerPort();
		if(port != 80){
			basePath += ":"+port;
		}
		basePath += "/" + Common.getDomain("FILE_INTER_PATH");
		return basePath;
	}
}
