package com.musicfire.common.utiles;

import com.musicfire.common.businessException.BusinessException;
import com.musicfire.common.businessException.ErrorCode;
import org.apache.commons.codec.binary.Hex;
import sun.misc.BASE64Encoder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.Random;

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

    public static String generate(String password) {
        Random r = new Random();
        StringBuilder sb = new StringBuilder(16);
        sb.append(r.nextInt(99999999)).append(r.nextInt(99999999));
        int len = sb.length();
        if (len < 16) {
            for (int i = 0; i < 16 - len; i++) {
                sb.append("0");
            }
        }
        String salt = sb.toString();
        password = md5Hex(password + salt);
        char[] cs = new char[48];
        for (int i = 0; i < 48; i += 3) {
            cs[i] = password.charAt(i / 3 * 2);
            char c = salt.charAt(i / 3);
            cs[i + 1] = c;
            cs[i + 2] = password.charAt(i / 3 * 2 + 1);
        }
        return new String(cs);
    }

    /**
     * 获取十六进制字符串形式的MD5摘要
     */
    public static String md5Hex(String src) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bs = md5.digest(src.getBytes());
            return new String(new Hex().encode(bs));
        } catch (Exception e) {
            return null;
        }
    }
    /**
     * 校验密码是否正确
     */
    public static boolean verify(String password, String md5) {
        char[] cs1 = new char[32];
        char[] cs2 = new char[16];
        for (int i = 0; i < 48; i += 3) {
            cs1[i / 3 * 2] = md5.charAt(i);
            cs1[i / 3 * 2 + 1] = md5.charAt(i + 2);
            cs2[i / 3] = md5.charAt(i + 1);
        }
        String salt = new String(cs2);
        return Objects.equals(md5Hex(password + salt), new String(cs1));
    }

    /**
     * 生成Token
     * @return
     */
    public static String makeToken() {
        String token = (System.currentTimeMillis() + new Random().nextInt(999999999)) + "";
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte md5[] =  md.digest(token.getBytes());
            BASE64Encoder encoder = new BASE64Encoder();
            return encoder.encode(md5);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new BusinessException(ErrorCode.TOKEN_ERR);
        }
    }

    public static void main(String[] args) {
        // 加密+加盐
//        String password1 = generate("123");
//        System.out.println("结果：" + password1 + "   长度："+ password1.length());
////        // 解码
//        System.out.println(Md5.verify("admin", password1));
//        // 加密+加盐
        String password2= generate("123");
        System.out.println("结果：" + password2 + "   长度："+ password2.length());
//        // 解码
        System.out.println(Md5.verify("123", password2));
    }
}
