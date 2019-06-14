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

import com.tc.common.utils.JsonUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-9-17
 * Time: 下午1:46
 * To change this template use File | Settings | File Templates.
 */
public class JsonTag extends javax.servlet.jsp.tagext.TagSupport {

    private Object items;

    @Override
    public int doStartTag() throws JspException {

        try {
            JspWriter out = this.pageContext.getOut();
            if (items == null) {
                out.println("false");
                return SKIP_BODY;
            }
            String ss = JsonUtils.toString(items);
            out.println(ss);
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
        this.items = null;
    }

    public void setItems(Object items) {
        this.items = items;
    }
}