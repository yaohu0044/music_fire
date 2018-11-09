package com.musicfire.common.utiles;

import com.musicfire.common.businessException.BusinessException;
import com.musicfire.common.businessException.ErrorCode;
import sun.misc.BASE64Encoder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5 {
    /**利用MD5进行加密*/
    public static String EncoderByMd5(String str){
        //确定计算方法
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder base64en = new BASE64Encoder();
            return base64en.encode(md5 != null ? md5.digest(str.getBytes(StandardCharsets.UTF_8)) : new byte[0]);
        } catch (NoSuchAlgorithmException e) {
            throw new BusinessException(ErrorCode.PASSWORD_ERR);
        }
    }

}
