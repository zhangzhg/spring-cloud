package com.cloud.cm.utils;

import java.security.MessageDigest;

import static java.nio.charset.StandardCharsets.UTF_8;

public class MD5Utils {
    public static String encrypt(String source) {
        StringBuffer sb = new StringBuffer(32);
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            // md.digest() 该函数返回值为存放哈希值结果的byte数组
            byte[] array = md.digest(source.getBytes(UTF_8));
            for (int i = 0; i < array.length; i++) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).toUpperCase(), 1, 3);
            }
        } catch (Exception ex) {
            return null;
        }
        return sb.toString();
    }

}
