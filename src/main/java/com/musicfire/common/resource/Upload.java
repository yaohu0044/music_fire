package com.musicfire.common.resource;

import com.baomidou.mybatisplus.toolkit.MapUtils;
import com.musicfire.common.utiles.FileUploadUtil;
import com.musicfire.common.utiles.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/fileUpload")
public class Upload {
    /**
     * 后期做图片的缩率 和上传服务器
     * @return
     */
    @RequestMapping("fileUpload")
    public Result upload(HttpServletRequest httpRequest) throws IOException {
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
        String localPath = "E:/I`m/upload";

        Map<String, String> upload = FileUploadUtil.upload(file, localPath, file.getOriginalFilename());

        if (!MapUtils.isEmpty(upload)) {
            return new Result().ok(upload);
        } else {
            return new Result().fail("上传失败");
        }
    }
}
