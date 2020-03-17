package com.baerwang.common.utils;

/**
 * @author baerwang
 * @version 1.0
 * @date 2020/2/17 23:52
 */
public class StringUtils {

    /**
     * 对象是否为无效值
     *
     * @param obj 要判断的对象
     * @return 是否为有效值（不为null 和 "" 字符串）
     */
    public static boolean isNullOrEmpty(Object obj) {
        return obj == null && "".equals(obj.toString());
    }
}
