package com.musicfire.common.utiles;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class FileUploadUtil {


    public static Map<String, String> upload(MultipartFile file, String path, String fileName) {
        Map<String, String> map = null;
        // 生成新的文件绝对路径
        String realPath = path + "/" + FileNameUtils.getFileName(fileName);
        File dest = new File(realPath);
        //判断文件父目录是否存在
        if (!dest.getParentFile().exists()) {
            boolean mkdir = dest.getParentFile().mkdir();
            if (!mkdir) {
                return null;
            }
        }
        try {
            //保存文件
            file.transferTo(dest);
            //生产缩率图
            String realPath1= path +"/" + System.currentTimeMillis()+FileNameUtils.getFileName(fileName);
            String smallPicture = zoomImageScale(dest, realPath1, 50);
            map = new HashMap<>();
            map.put("realPath", realPath);
            map.put("smallPicture", smallPicture);
            return map;
        } catch (IllegalStateException e) {

            e.printStackTrace();
            return map;
        } catch (IOException e) {

            e.printStackTrace();
            return null;
        }

    }
    private static String zoomImageScale(File imageFile, String newPath, int newWidth) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(imageFile);
        int originalWidth = bufferedImage.getWidth();
        int originalHeight = bufferedImage.getHeight();
        double scale = (double)originalWidth / (double)newWidth;    // 缩放的比例

        int newHeight =  (int)(originalHeight / scale);

        return FileUploadUtil.zoomImageUtils(imageFile, newPath, bufferedImage, newWidth, newHeight);
    }

    private static String zoomImageUtils(File imageFile, String newPath, BufferedImage bufferedImage, int width, int height)
            throws IOException{

        String suffix = StringUtils.substringAfterLast(imageFile.getName(), ".");
        File imgFile = new File(newPath);
        // 处理 png 背景变黑的问题
        if(suffix.trim().toLowerCase().endsWith("png") || suffix.trim().toLowerCase().endsWith("gif")){
            BufferedImage to= new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = to.createGraphics();
            to = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
            g2d.dispose();
            g2d = to.createGraphics();
            Image from = bufferedImage.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);
            g2d.drawImage(from, 0, 0, null);
            g2d.dispose();

            ImageIO.write(to, suffix, imageFile);
        }else{
            // 高质量压缩，其实对清晰度而言没有太多的帮助
            BufferedImage newImage = new BufferedImage(width, height, bufferedImage.getType());
            Graphics g = newImage.getGraphics();
            g.drawImage(bufferedImage, 0, 0, width, height, null);
            g.dispose();

            boolean newFile = imgFile.createNewFile();
            if(newFile){
                ImageIO.write(newImage, suffix, imgFile);
            }
        }
        return imageFile.getPath();
    }
}
