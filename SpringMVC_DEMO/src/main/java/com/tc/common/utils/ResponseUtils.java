/* 
 * @I18N
 * Copyright (c) 2012, 2015, Together R&D Center.All rights reserved.
 * 
 * 注意：这段国际化注释禁止任何人修改！
 * 创建时间：2015-07-02 10:23:09
 * 通过com.tlearning.generator.i18n.ConverJava构建。
 * 
 * @I18N
 */
package com.tc.common.utils;

import org.apache.commons.lang.time.DateUtils;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: TR069
 * Date: 13-1-11
 * Time: 上午10:00
 * To change this template use File | Settings | File Templates.
 */
public final class ResponseUtils {

    /**
     * 设置Response的过期头.
     *
     * @param response  HttpServletResponse
     * @param seconds   秒数.
     * @param isPrivate 是否需要在代理服务器上缓存.
     */
    public static void setExpired(final HttpServletResponse response, final long seconds, final boolean isPrivate) {
        StringBuilder cacheControl = new StringBuilder(128);

        cacheControl.append(isPrivate ? "private" : "public");
        cacheControl.append(",max-age=").append(seconds);

        // 设置头信息
        long nowMs = System.currentTimeMillis();
        //response.setDateHeader("Last-Modified",nowMs);
        response.setHeader("Cache-Control", cacheControl.toString());
        response.setDateHeader("Expires", nowMs + seconds * DateUtils.MILLIS_PER_SECOND);
        response.setDateHeader("Date", nowMs);
    }

    public static void setNoCache(final HttpServletResponse response) {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
    }

    /**
     * 默认构造函数.
     */
    private ResponseUtils() {

        // 工具类不可实例化.
    }
}