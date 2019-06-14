package com.tc.common.utils;

/**
 * Created with IntelliJ IDEA.
 * User: New
 * Date: 13-2-2
 * Time: 下午5:05
 * To change this template use File | Settings | File Templates.
 */

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * .
 *
 * @author yzhao (yzhao@wiscom.com.cn)
 * @version V1.0, 10-12-23 上午5:45
 */
public class CookieUtils {

    /**
     * 设置cookie.
     *
     * @param response
     * @param name
     * @param value
     */
    public static void setCookie(final HttpServletResponse response, final String name, final String value) {
        setCookie(response, name, value, "/", "", "", -1);
    }

    public static void setCookie(final HttpServletResponse response, final String name, final String value,final int expired) {
        setCookie(response, name, value, "/", "", "", expired);
    }

    public static void removeCookie(final HttpServletResponse response, final String name) {
        setCookie(response, name, "", "/", "", "", 0);
    }

    /**
     * 编辑cookie。
     *
     * @param response
     * @param name
     * @param value
     * @param expired
     */
    public static void setCookie(final HttpServletResponse response, final String name, final String value,
                                 String path, String comment, String domain, final int expired) {
        Cookie cookie = new Cookie(name, value);

        cookie.setPath(path);
        cookie.setComment(comment);
        if (!StringUtils.isBlank(domain)) {
            cookie.setDomain(domain);
        }
        cookie.setMaxAge(expired);
        response.addCookie(cookie);
    }

    /**
     * 获取cookie值.
     *
     * @param request HttpServletRequest
     * @param name    cookie名称
     * @return cookie的值
     */
    public static String getCookieValue(final HttpServletRequest request, final String name) {
        Cookie[] cookies = request.getCookies();

        // 添加cookie数组可能为空的判断.
        if (cookies == null) {
            return null;
        }

        String cvalue = null;
        Cookie cookie;
        for (int i = 0, n = cookies.length; i < n; i++) {
            cookie = cookies[i];
            if (cookie.getName().equals(name)) {
                cvalue = cookie.getValue();
                break;
            }
        }

        return cvalue;
    }

    /**
     * 编辑loginname的cookie。
     *
     * @param response
     * @param value
     */
    public static void setLoginNameCookie(final HttpServletResponse response, final String value) {
        //setCookie(response, "userLoginName", StringUtils.encodeBase64(value));
    }

    /**
     * 获取loginname的cookie值.
     *
     * @param request
     * @return
     */
    public static String getLoginNameCookieValue(final HttpServletRequest request) {
        String cv = getCookieValue(request, "userLoginName");

        //return cv == null ? null : StringUtils.decodeBase64(cv);
        return null;
    }

    /**
     * 删除loginname的cookie值
     *
     * @param response
     */
    public static void removeLoginNameCookie(final HttpServletResponse response) {
        removeCookie(response, "userLoginName");
    }
}