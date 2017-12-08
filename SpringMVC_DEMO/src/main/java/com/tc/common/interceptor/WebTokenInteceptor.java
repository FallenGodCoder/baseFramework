package com.tc.common.interceptor;

import com.tc.common.annotation.WebToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * 用于web token防止重复提交
 *
 * 利用Session防止表单重复提交:
 * 		在服务器端生成一个唯一的随机标识号，专业术语称为Token(令牌)，同时在当前用户的Session域中保存这个Token。然后
 * 将Token发送到客户端的Form表单中，在Form表单中使用隐藏域来存储这个Token，表单提交的时候连同这个Token一起提交到服务
 * 器端，然后在服务器端判断客户端提交上来的Token与服务器端生成的Token是否一致，如果不一致，那就是重复提交了，此时服
 * 务器端就可以不处理重复提交的表单。如果相同则处理表单提交，处理完后清除当前用户的Session域中存储的标识号。
 *
 * 在下列情况下，服务器程序将拒绝处理用户提交的表单请求：
 * 1.存储Session域中的Token(令牌)与表单提交的Token(令牌)不同。
 * 2.当前用户的Session中不存在Token(令牌)。
 * 3.用户提交的表单数据中没有Token(令牌)。
 */
public class WebTokenInteceptor extends HandlerInterceptorAdapter {

	// 日志
//	protected final Logger logger = LoggerFactory.getLogger(WebTokenInteceptor.class.getCanonicalName());

	/**
	 * 方法执行前，进行操作
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (handler instanceof HandlerMethod) {
			// 分析注解
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			WebToken annotation = method.getAnnotation(WebToken.class);

			// 为需要处理注解时
			if (annotation != null) {
				if (annotation.create()) {
					request.getSession(true).setAttribute("webToken", UUID.randomUUID().toString());
//					request.setAttribute();
				} else if (annotation.remove()) {
					// 重复提交，不做任何处理
					if (isRepeatSubmit(request)) {
						return false;
					}
					request.getSession(true).removeAttribute("webToken");
				}
			}
		}

		return super.preHandle(request, response, handler);
	}

	/**
	 * 用于判断是否为重复提交
	 * 
	 * @param request
	 * @return
	 */
	private boolean isRepeatSubmit(HttpServletRequest request) {
		String serverToken = (String) request.getSession(true).getAttribute("webToken");

		if (serverToken == null) {
			return true;
		}

		//获取客户端传过来的token
		String clinetToken = request.getParameter("webToken");

		if (clinetToken == null) {
			return true;
		}

		return serverToken.equals(clinetToken);
	}
}
