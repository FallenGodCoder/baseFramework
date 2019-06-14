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
package com.tc.common.tag;


import com.tc.common.utils.RequestUtils;
import org.apache.commons.lang.StringUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/**
 * Created by IntelliJ IDEA.
 * User: TR069
 * Date: 13-1-10
 * Time: 下午1:49
 * To change this template use File | Settings | File Templates.
 */
public class CssTag extends javax.servlet.jsp.tagext.TagSupport {

    private String url;
    private String charset;
    private String themes;

    @Override
    public int doStartTag() throws JspException {

        try {
            JspWriter out = this.pageContext.getOut();
            if (StringUtils.isBlank(url)) {
                out.println("No url Found...");
                return SKIP_BODY;
            }
            StringBuilder output = new StringBuilder(400);
            output.append("<link rel=\"stylesheet\" type=\"text/css\"");
            if (StringUtils.isBlank(charset)) {
                charset = "utf-8";
            }
            output.append(" charset=\"").append(charset).append("\"");
            output.append(" href=\"");
//                    .append(pageContext.getRequest().getServerName());
//            int port = pageContext.getRequest().getServerPort();
//            if (port != 80) {
//                output.append(":").append(port);
//            }
            //
            output.append(RequestUtils.getPageContextPath(pageContext));
            themes = StringUtils.isBlank(themes) ? "" : "/themes/" + themes;
            if (url.charAt(0) != '/') {
                url = "/" + url;
            }
            output.append("/css").append(themes).append(url).append("?v=1").append("\" />");
            out.println(output);
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

    @Override
    public void release() {
        super.release();
        this.url = null;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public void setThemes(String themes) {
        this.themes = themes;
    }
}