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
 * Time: 下午5:18
 * To change this template use File | Settings | File Templates.
 */
public class JsTag extends javax.servlet.jsp.tagext.TagSupport {

    private String url;
    private String charset;

    @Override
    public int doStartTag() throws JspException {

        try {
            JspWriter out = this.pageContext.getOut();
            if (StringUtils.isBlank(url)) {
                out.println("No url Found...");
                return SKIP_BODY;
            }
            StringBuilder output = new StringBuilder(400);
            output.append("<script language=\"javascript\" type=\"text/javascript\"");
            if (StringUtils.isBlank(charset)) {
                charset = "utf-8";
            }
            output.append(" charset=\"").append(charset).append("\"");
            output.append(" src=\"");
//                    .append(pageContext.getServletContext().getContextPath());
//            int port = pageContext.getRequest().getServerPort();
//            if (port != 80) {
//                output.append(":").append(port);
//            }
            if (url.charAt(0) != '/') {
                url = "/" + url;
            }
            output.append(RequestUtils.getPageContextPath(pageContext)).append(url).append("?v=1").append("\"></script>");
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
}