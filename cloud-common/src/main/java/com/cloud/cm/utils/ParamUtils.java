package com.cloud.cm.utils;


import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 参数工具类
 */
public class ParamUtils {

    /**
     * 判断字符串是否为空
     */
    public static boolean isEmpty(String value) {
        return value == null || "".equals(value.trim());
    }


    /**
     * 判断字符串数组是否为空
     */
    public static boolean isEmpty(String[] value) {
        return value == null || value.length <= 0;
    }

    /**
     * 判断文件数组是否为空
     * @return true 是,false 否
     */
    public static boolean isEmpty(File[] value) {
        return value == null || value.length <= 0;
    }

    /**
     * 判断数组是否为空
     * @return true 是,false 否
     */
    public static boolean isEmpty(int[] value) {
        return value == null || value.length == 0;
    }

    /**
     * 判断字符串数组是否为空
     * @return true 是,false 否
     */
    public static boolean isEmpty(double[] value) {
        return value == null || value.length == 0;
    }

    /**
     * 判断字符串数组是否为空
     * @return true 是,false 否
     */
    public static boolean isEmpty(char[] value) {
        return value == null || value.length == 0;
    }

    /**
     * 判断List是否为空
     * @return true 是,false 否
     */
    public static boolean isEmpty(List<?> value) {
        return value == null || value.size() == 0;
    }

    /**
     * 判断List是否为空
     */
    public static boolean isEmpty(Map<?, ?> value) {
        return value == null || value.size() == 0;
    }

    /**
     * 判断两个对象是否相同
     */
    public static boolean isEqual(Object value1, Object value2) {
        if (isNull(value1) || isNull(value2)) {
            return false;
        }

        return value1.equals(value2);
    }

    /**
     * 判断字符串是否为空
     */
    public static boolean isNull(Object value) {
        return value == null;
    }

    public static boolean isNotNull(Object value) {
        return value != null;
    }

    /**
     * 判断数组对象是否为空
     */
    public static boolean isNull(Object[] value) {
        return value == null || value.length <= 0;
    }

    /**
     * String 和 Integer 转换
     */
    public static int s2i(String value) {
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * String 和 Integer 转换
     */
    public static String i2s(int value) {
        return String.valueOf(value);
    }

    /**
     * String 和 Long 转换
     */
    public static Long s2l(String value) {
        try {
            return Long.valueOf(value);
        } catch (NumberFormatException e) {
            return 0L;
        }
    }

    /**
     * String 和 Long 转换
     */
    public static String l2s(long value) {
        return String.valueOf(value);
    }

    /**
     * String 转换为 InputStream
     */
    public static InputStream String2InputStream(String str) {
        return new ByteArrayInputStream(str.getBytes());
    }

    /**
     * InputStream 转换为 String
     */
    public static String inputStream2String(InputStream is) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        StringBuilder buffer = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            buffer.append(line);
        }
        return buffer.toString();
    }

    /**
     * 贪婪匹配
     * @param exp 输入的匹配条件 eg：paramString =a*b  str =...a...b..这种格式都是匹配的
     */
    public static boolean isMatch(String str, String exp) {
        if (str == null || exp == null) {
            return false;
        }
        String regex = exp;
        if (exp.indexOf("*") != -1) {
            regex = exp.replaceAll("\\*", ".*");
        }
        Pattern pat = Pattern.compile(regex);
        Matcher mat = pat.matcher(str);
        return mat.find();
    }
}
