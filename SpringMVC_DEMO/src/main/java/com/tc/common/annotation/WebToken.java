package com.tc.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * web token用于防止重复提交
 *
 * 在进入表单页面生成一个token，提交之后remove掉token。
 * 如果遇到后台有校验的，不通过，需要用户改正之后再提交，那么需要写一个不加@WebToken的校验controller，校验通过后再转发
 * 到提交的带@WebToken的Controller中。
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface WebToken {
	
	/**
	 * 用于标识，是否需要生成token并塞入session
	 * @return
	 */
	boolean create() default false;
	
	/**
	 * 用于标识，是否需要从session中移除token
	 * @return
	 */
	boolean remove() default false;
}
