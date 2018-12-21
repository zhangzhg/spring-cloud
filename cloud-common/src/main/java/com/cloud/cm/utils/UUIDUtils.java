    /**
     * 文件名：UUIDUtil.java
     *
     * 版本信息：
     * 日期：2012-4-20
     * Copyright  Corporation 2012
     * 版权所有 E-vada
     *
     */

package com.cloud.cm.utils;

import java.util.UUID;


/**
 * 此类描述的是： UUID相关处理工具类
 */

public class UUIDUtils {
    public static String generate() {
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}

