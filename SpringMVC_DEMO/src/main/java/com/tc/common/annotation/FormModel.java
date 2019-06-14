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
package com.tc.common.annotation;

import java.lang.annotation.*;


/**
 * 这个注解用在controller中来为对象属性赋值
 *
 * Created with IntelliJ IDEA.
 * User: tangcheng
 * Date: 13-9-24
 * Time: 上午10:04
 * To change this template use File | Settings | File Templates.
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FormModel {
    String value() default "";
}