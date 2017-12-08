package com.tc.common.interceptor;

import com.alibaba.fastjson.JSON;
import org.slf4j.LoggerFactory;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.validation.support.BindingAwareModelMap;

import java.lang.reflect.Method;

/**
 * 记录controller的请求和返回数据
 *
 */
public class RestfulLogInteceptor implements MethodBeforeAdvice, AfterReturningAdvice {

	// 日志
	protected final org.slf4j.Logger logger = LoggerFactory.getLogger(RestfulLogInteceptor.class.getCanonicalName());

	private final String[] IPACKAGE = new String[] { "Request", "Session", "Response" };

	/**
	 * 判断and添加对象日志
	 * 
	 * @param sb
	 * @param args
	 */
	private void appendObjLog(StringBuilder sb, Object... args) {
		if (args == null || args.length == 0) {
			return;
		}
		outer: for (int i = 0; i < args.length; i++) {
			Object arg = args[i];

			if (arg == null) {
				continue;
			}

			String className = arg.getClass().getCanonicalName();
			sb.append(" ");

			// package过滤
			for (String pname : IPACKAGE) {
				if (className.contains(pname)) {
					sb.append("{}");
					continue outer;
				}
				// modelMap
				else if (className.startsWith("org.springframework.validation.support.BindingAwareModelMap")) {
					BindingAwareModelMap target = (BindingAwareModelMap) arg;
					sb.append(target.toString());
					continue outer;
				}
			}

			sb.append(JSON.toJSONString(arg));
		}
	}

	/**
	 * 记录请求日志
	 */
	public void before(Method method, Object[] args, Object target) throws Throwable {
		// 异常日志拼接器
		StringBuilder msg = new StringBuilder();

		msg.append("Recive request!").append(" Class Name:").append(target.getClass().getName())
				.append("  Method Name :  ").append(method.getName()).append("  Params: ");

		appendObjLog(msg, args);

		logger.info(msg.toString());
	}

	/**
	 * 记录返回日志
	 */
	public void afterReturning(Object target, Method method, Object[] args, Object arg3) throws Throwable {

		// 异常日志拼接器
		StringBuilder msg = new StringBuilder();

		msg.append("Send response!").append(" Class Name:").append(arg3.getClass().getName())
				.append("  Method Name :  ").append(method.getName()).append("  Params: ");

		appendObjLog(msg, args);

		msg.append("  Return: ");

		appendObjLog(msg, target);

		logger.info(msg.toString());
	}

}
