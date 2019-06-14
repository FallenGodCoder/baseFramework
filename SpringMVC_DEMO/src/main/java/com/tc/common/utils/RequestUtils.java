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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Locale;

public class RequestUtils {

    private static final Logger logger = LoggerFactory.getLogger(RequestUtils.class);

    private static final String contextPathName = "contextPath";
    /**
     * 默认关键字命名.
     */
    public static final String GZIP = "__gzip";


    public static String getContextPath(HttpServletRequest request) {
        String contextPath = (String) request.getAttribute(contextPathName);

        if (contextPath == null) {
            contextPath = request.getContextPath();
            if (contextPath != null && contextPath.length() > 0) {
                if ("/".equals(StringUtils.right(contextPath, 1))) {
                    contextPath = StringUtils.substringBeforeLast(contextPath, "/");
                }
            } else {
                contextPath = "";
            }
            request.setAttribute(contextPathName, contextPath);
        }

        return contextPath;
    }

    /**
     * 获取页面的contextPath
     * @param pageContext
     * @return 如果当前的上下文
     */
    public static String getPageContextPath(PageContext pageContext) {
        String contextPath = pageContext.getServletContext().getContextPath();
        if (contextPath != null && contextPath.length() > 0) {
            if ("/".equals(StringUtils.right(contextPath, 1))) {
                contextPath = StringUtils.substringBeforeLast(contextPath, "/");
            }
        } else {
            contextPath = "";
        }
        return contextPath;
    }

    public static String replaceContextPath(final HttpServletRequest request, String content) {
        return ConvertUtils.replace(content, "\\$\\{contextPath\\}", new ReplaceOperate() {
            public String replace(int i, String pattern) {
                return RequestUtils.getContextPath(request);
            }
        });
    }

    public static int[] getRange(String s) {
        int[] ranges = new int[2];
        ranges[0] = -1;
        ranges[1] = -1;

        s = StringUtils.trimToEmpty(s);
        s = StringUtils.removeStart(s, "bytes=");
        char[] cs = s.toCharArray();

        StringBuffer s1 = new StringBuffer();
        StringBuffer s2 = new StringBuffer();

        boolean first = true;
        if (cs != null && cs.length > 0) {
            for (int i = 0; i < cs.length; i++) {
                if ('-' == cs[i]) {
                    first = false;
                    continue;
                }

                if (first)
                    s1.append(cs[i]);
                else
                    s2.append(cs[i]);

            }
        }

        ranges[0] = NumberUtils.toInt(s1.toString(), -1);
        ranges[1] = NumberUtils.toInt(s2.toString(), -1);

        return ranges;
    }

    /**
     * 判断客户端浏览器是否支持GZip压缩.
     *
     * @param request HttpServletRequest.
     * @return 是否支持.
     */
    public static boolean isSupport(HttpServletRequest request) {

        // 手工设置不使用压缩
        String enableGzip = request.getParameter(GZIP);
        if ("false".equalsIgnoreCase(StringUtils.trimToEmpty(enableGzip))) {
            return false;
        }

        if (isIncluded(request)) {
            return false;
        }

        boolean support = false;
        String header = request.getHeader("Accept-Encoding");

        if (StringUtils.isNotBlank(header)) {
            header = header.toLowerCase(Locale.US);
            // 如果存在字符串gzip就表示支持
            support = header.indexOf("gzip") > -1;
        }

        return support;
    }

    /**
     * Checks if the request uri is an include.
     * These cannot be gzipped.
     *
     * @param request HttpServletRequest.
     * @return 是否是jsp include.
     */
    public static boolean isIncluded(final HttpServletRequest request) {
        final String uri = (String) request.getAttribute("javax.servlet.include.request_uri");
        final boolean includeRequest = !(uri == null);

        if (includeRequest && logger.isDebugEnabled()) {
            logger.debug(request.getRequestURL() + " resulted in an include request. This is unusable, because"
                    + "the response will be assembled into the overrall response. Not gzipping.");
        }
        return includeRequest;
    }


    public static boolean isAjaxRequest(HttpServletRequest request) {
        String header = request.getHeader("X-Requested-With");
        return header != null && "XMLHttpRequest".equals(header);
    }

    /**
     * 获取真实请求路径
     *
     * @param url
     * @return
     */
    public static String getRequestUrl(String url) {
        if (StringUtils.isBlank(url)) {
            return url;
        }
        return StringUtils.substring(url, url.indexOf("/", 1), url.lastIndexOf("") + 3);
    }

    /**
     * 拼装当前路径.
     *
     * @param scheme      协议.
     * @param server      服务名称.
     * @param port        端口号.
     * @param contextPath 上下文路径.
     * @param servletPath Servlet path.
     * @param queryString Query String.
     * @return 完整的URL.
     */
    public static String getCurrentUrl(String scheme, String server, int port, String contextPath, String servletPath,
                                       String queryString) {
        StringBuffer url = new StringBuffer(128);
        //如果打开使用CCS应用配置的端口

        if (port < 0) {
            port = 80;
        }

        url.append(scheme).append("://").append(server);
        if ((scheme.equals("http") && (port != 80)) || (scheme.equals("https") && (port != 443))) {
            url.append(':').append(port);
        }

        url.append(contextPath).append(servletPath);

        if (!StringUtils.isBlank(queryString)) {
            url.append('?').append(queryString);
        }
        String returnUrl = "";
        try {
            returnUrl = URLEncoder.encode(url.toString(), "UTF-8");
        } catch (Exception e) {
            logger.error(e.toString());
        }

        return returnUrl;
    }

    /**
     * 跳转到登录页面
     *
     * @param request
     * @param response
     * @return
     */
    public static String redirectLoginUrl(final String LoginUrl, HttpServletRequest request, HttpServletResponse response) {
        String url = LoginUrl;
        String currentUrl = getCurrentUrl(request.getScheme(), request.getServerName(),
                request.getServerPort(), request.getContextPath(), request.getServletPath(),
                request.getQueryString());

        logger.debug("Get current path: {}", currentUrl);

        try {
            currentUrl = URLEncoder.encode(currentUrl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error(e.toString());
        }

        // 如果是本应用内的路径，则加上context path
        if (url.charAt(0) == '/') {
            String contextPath = request.getContextPath();

            if ("/".equals(contextPath)) {
                contextPath = StringUtils.EMPTY;
            }
            url = new StringBuilder(contextPath).append(url).append("?requestURL=").append(currentUrl).toString();
        }
        return url;
    }

    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}