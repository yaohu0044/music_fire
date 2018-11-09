package com.musicfire.common.utiles;

public class FileNameUtils {

    /**
     * 获取文件后缀
     * @param fileName 文件名
     * @return 后缀
     */
    public static String getSuffix(String fileName){
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 生成新的文件名
     * @param fileOriginName 源文件名
     * @return 新文件名
     */
    public static String getFileName(String fileOriginName){
        return UUIDTool.getUUID() + FileNameUtils.getSuffix(fileOriginName);
    }

}
