package com.musicfire.common.utiles;

import com.baomidou.mybatisplus.toolkit.MapUtils;
import io.netty.util.internal.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.logging.log4j.ThreadContext.isEmpty;

@RestController
@Slf4j
@RequestMapping("/fileUpload")
public class FileUpload {

    /**
     * 图片文件上传
     */
    @PostMapping(value = "/upload")
    /**
     * 后期做图片的缩率 和上传服务器
     * @param file 文件
     * @param path 文件存放路径
     * @param fileName 源文件名
     * @return
     */

    @RequestMapping("fileUpload")
    public Result upload(HttpServletRequest httpRequest) {
        MultipartHttpServletRequest request = (MultipartHttpServletRequest) httpRequest;
        MultiValueMap<String, MultipartFile> multiFileMap = request.getMultiFileMap();
        if (ObjectUtils.isEmpty(multiFileMap)) {
            return new Result().fail("上传文件为空");
        }
        if (ObjectUtils.isEmpty(multiFileMap.get("img"))) {
            return new Result().fail("文件属性名为img");
        }
        List<MultipartFile> multipartFiles = multiFileMap.get("img");
        if (multipartFiles.size() > 1) {
            return new Result().fail("目前只支持单张上传");
        }
        MultipartFile file = multipartFiles.get(0);
        // 要上传的目标文件存放路径 测试阶段写死。。。。
        String localPath = "F:/upload";
        Map<String, String> upload = upload(file, localPath, file.getOriginalFilename());

        if (!MapUtils.isEmpty(upload)) {
            return new Result().ok(upload);
        } else {
            return new Result().fail("上传失败");
        }
    }


    private Map<String, String> upload(MultipartFile file, String path, String fileName) {
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
            map = new HashMap<>();
            map.put("realPath", realPath);
            return map;
        } catch (IllegalStateException e) {

            e.printStackTrace();
            return map;
        } catch (IOException e) {

            e.printStackTrace();
            return null;
        }

    }
}
