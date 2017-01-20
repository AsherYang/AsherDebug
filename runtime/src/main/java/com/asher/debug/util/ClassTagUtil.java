package com.asher.debug.util;

/**
 * Created by ouyangfan on 2017/1/20.
 * <p>
 * TAG Class命名
 */
public class ClassTagUtil {

    public static String asTag(Class<?> cls) {
        // 如果是匿名内部类
        if (cls.isAnonymousClass()) {
            return asTag(cls.getEnclosingClass());
        }
        return cls.getSimpleName();
    }
}
