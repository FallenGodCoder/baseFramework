/* 
 * @I18N
 * Copyright (c) 2012, 2015, Together R&D Center.All rights reserved.
 * 
 * 注意：这段国际化注释禁止任何人修改！
 * 创建时间：2015-07-02 10:23:08
 * 通过com.tlearning.generator.i18n.ConverJava构建。
 * 
 * @I18N
 */
package com.tc.common.form;

import com.tc.common.utils.ConvertUtils;
import com.tc.common.utils.PageHelper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WebInput implements Serializable {

    private final static Logger logger = LoggerFactory.getLogger(WebInput.class);

    private HttpServletRequest request;

    public static WebInput getInstance(HttpServletRequest request) {
        return new WebInput(request);
    }

    public WebInput(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletRequest getRequest() {
        return request;
    }
    public HttpServletRequest setRequest(HttpServletRequest request) {
        return this.request = request;
    }

    public String getString(String name, String defaultValue) {
        String result = StringUtils.trimToNull(request.getParameter(name));
        if (result == null)
            return defaultValue;
        try{
            return java.net.URLDecoder.decode(result, "UTF-8");
        }catch (UnsupportedEncodingException ioException){
            return result;
        }
    }

    public String getString(String name) {
        return StringUtils.trimToNull(request.getParameter(name));
    }

    public String getLikeString(String name) {
        return ConvertUtils.makeLikeString(getString(name), null);
    }

    public PageHelper getPageHelper(int page, int size){
        return new PageHelper(getInt("page", page), getInt("rows", size));
    }


    public int getInt(String name, int defaultValue) {
        int result = defaultValue;
        try {
            result = Integer.parseInt(request.getParameter(name));
        } catch (NumberFormatException e) {
            //
        }
        return result;
    }

    public Integer getInt(String name, Integer defaultValue) {
        Integer result = defaultValue;
        try {
            result = Integer.valueOf(request.getParameter(name));
        } catch (NumberFormatException e) {
            //
        }
        return result;
    }

    public Integer getInt(String name) {
        try {
            return Integer.valueOf(request.getParameter(name));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public long getLong(String name, long defaultValue) {
        long result = defaultValue;
        try {
            result = Long.parseLong(request.getParameter(name));
        } catch (NumberFormatException e) {
            //
        }
        return result;
    }

    public Long getLong(String name, Long defaultValue) {
        Long result = defaultValue;
        try {
            result = Long.valueOf(request.getParameter(name));
        } catch (NumberFormatException e) {
            //
        }
        return result;
    }

    public Long getLong(String name) {
        try {
            return Long.valueOf(request.getParameter(name));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public double getDouble(String name, double defaultValue) {
        double result = defaultValue;
        try {
            result = Double.parseDouble(request.getParameter(name));
        } catch (NumberFormatException e) {
            //
        }
        return result;
    }

    public boolean getBoolean(String name) {
        return getBoolean(name, false);
    }

    public boolean getBoolean(String name, boolean defaultValue) {
        boolean result = defaultValue;
        String s = StringUtils.trimToNull(request.getParameter(name));
        if (s == null)
            return result;

        s = s.toLowerCase();
        if ("yes".equals(s) || "true".equals(s) || "1".equals(s)
                || "ok".equals(s) || "t".equals(s)) {
            result = true;
        } else if ("no".equals(s) || "false".equals(s) || "0".equals(s)
                || "f".equals(s)) {
            result = false;
        }
        return result;
    }

    public Date getDate(String name, String pattern, Date defaultValue) {
        Date result = defaultValue;
        try {
            String value = request.getParameter(name);
            DateFormat df = new SimpleDateFormat(pattern);
            if (StringUtils.isNotBlank(value))
                result = df.parse(value);
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    public String[] getStrings(String name) {
        return request.getParameterValues(name);
    }

    public int[] getInts(String name) {
        return getInts(name, 0);
    }

    public int[] getInts(String name, int defaultValue) {
        String[] values = getStrings(name);
        if (values == null || values.length < 1)
            return null;

        int[] results = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            results[i] = defaultValue;
            try {
                results[i] = Integer.parseInt(values[i]);
            } catch (NumberFormatException e) {
                //null;
            }
        }
        return results;
    }

    public long[] getLongs(String name) {
        return getLongs(name, 0L);
    }

    public long[] getLongs(String name, long defaultValue) {
        String[] values = getStrings(name);
        if (values == null || values.length < 1)
            return null;

        long[] results = new long[values.length];
        for (int i = 0; i < values.length; i++) {
            results[i] = defaultValue;
            try {
                results[i] = Long.parseLong(values[i]);
            } catch (NumberFormatException e) {
                //null;
            }
        }
        return results;
    }

    public Long[] getLongObjects(String name) {
        return getLongObjects(name, 0L);
    }

    public Long[] getLongObjects(String name, long defaultValue) {
        long[] longs = this.getLongs(name, defaultValue);
        if (longs == null || longs.length < 1)
            return null;
        Long[] results = new Long[longs.length];
        for (int i = 0; i < longs.length; i++) {
            results[i] = longs[i];
        }
        return results;
    }

    public String getCookieValue(String name) {
        Cookie[] cs = request.getCookies();

        if (cs == null || cs.length < 1) {
            return null;
        }

        for (Cookie c : cs) {
            String key = c.getName();
            String value = c.getValue();
            if (name.equals(key))
                return value;
        }

        return null;
    }

    public String getClientIP() {
        String xForwardedFor = null;

        xForwardedFor = StringUtils.trimToNull(request.getHeader("$wsra"));
        if (xForwardedFor != null) {
            return xForwardedFor;
        }

        xForwardedFor = StringUtils.trimToNull(request.getHeader("X-Real-IP"));
        if (xForwardedFor != null) {
            return xForwardedFor;
        }

        xForwardedFor = StringUtils.trimToNull(request.getHeader("X-Forwarded-For"));
        if (xForwardedFor != null) {
            int spaceIndex = xForwardedFor.indexOf(',');
            if (spaceIndex > 0) {
                return xForwardedFor.substring(0, spaceIndex);
            } else {
                return xForwardedFor;
            }
        }

        return request.getRemoteAddr();
    }

    public String getUserAgent(){
        String Agent = request.getHeader("User-Agent");
        return Agent;
    }
    public void setAttribute(String name, Object value) {
        request.setAttribute(name, value);
    }

    public Object getAttribute(String name) {
        return request.getAttribute(name);
    }
}