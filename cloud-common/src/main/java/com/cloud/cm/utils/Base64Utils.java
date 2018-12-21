package com.cloud.cm.utils;

import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * Base64工具类
 */
public class Base64Utils {

    /**
     * 把输入流转化为base64的字符串
     *
     * @param input 输入流
     * @return String
     */
    public static String inputStreamToBase64(InputStream input) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int len;
        byte[] b = new byte[1024];
        while ((len = input.read(b, 0, b.length)) != -1) {
            byteArrayOutputStream.write(b, 0, len);
        }
        byte[] buffer = byteArrayOutputStream.toByteArray();
        String base64 = new BASE64Encoder().encode(buffer);
        base64 = org.apache.commons.lang3.StringUtils.replace(org.apache.commons.lang3.StringUtils.replace(base64, "\r", ""), "\n", "");
        byteArrayOutputStream.close();
        input.close();
        return base64;
    }

    public static String getBase64(String s) {
        byte[] bytes = Base64.encodeBase64(s.getBytes(StandardCharsets.UTF_8));
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static String getFromBase64(String s) throws UnsupportedEncodingException {
        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
        byte[] convertBytes = Base64.decodeBase64(bytes);
        return new String(convertBytes, StandardCharsets.UTF_8);
    }

}
