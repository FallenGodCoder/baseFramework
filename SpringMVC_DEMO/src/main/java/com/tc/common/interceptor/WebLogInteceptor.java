package com.tc.common.interceptor;

import com.alibaba.fastjson.JSON;
import com.tc.common.CommonConstants;
import com.tc.common.utils.IpHelp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

/**
 * 记录日志
 * 
 */
public class WebLogInteceptor extends HandlerInterceptorAdapter {

	// 日志
	protected final Logger logger = LoggerFactory.getLogger(WebLogInteceptor.class.getCanonicalName());

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelandview) throws Exception {

		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();

			String userId = "";
			if (request != null) {
				HttpSession session = request.getSession(false);
				if (session != null) {
					userId = (String) session.getAttribute(CommonConstants.USER_ID);
				}
			}

			StringBuilder msg = new StringBuilder();
			// 记录发生异常时调用的方法和参数
			msg.append("For check!").append(" Class Name:").append(handlerMethod.getBeanType().getCanonicalName())
					.append("  Method Name :  ").append(method.getName()).append(" IP:")
					.append(IpHelp.getIpAddr(request)).append(" UserId:").append(userId).append("  Params: ")
					.append(JSON.toJSONString(request.getParameterMap()));

			logger.info(msg.toString());
		}
	}
}
