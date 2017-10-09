package com.soauth.core.utils;

import java.util.UUID;

/**
 * Created by zhoujie on 2017/9/25.
 */
public class GidGeneratorUtils {
    public static String generate() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
