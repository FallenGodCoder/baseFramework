package com.tc.common.interceptor;

import com.alibaba.fastjson.JSON;
import com.tc.common.BaseController;
import com.tc.common.Response;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

public class WebHandlerExceptionResolver extends BaseController implements HandlerExceptionResolver {

	// 日志
	protected final org.slf4j.Logger logger = LoggerFactory.getLogger(WebHandlerExceptionResolver.class.getName());

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		if (handler instanceof HandlerMethod) {
			// 分析注解
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();

			StringBuilder msg = new StringBuilder();
			// 记录发生异常时调用的方法和参数
			msg.append("Exception!").append(" Class Name:").append(method.getClass().getCanonicalName())
					.append("  Method Name :  ").append(method.getName()).append("  Params: ")
					.append(JSON.toJSONString(request.getParameterMap()));

			logger.error(msg.toString(), ex);

			ResponseBody body = handlerMethod.getMethodAnnotation(ResponseBody.class);
			if (body == null) {
				return getCommonView(ERROR, ex.getMessage());
			}
			Response res = new Response();

			try {
				PrintWriter writer = response.getWriter();
				res.setState(Response.STATE_EXCEPTION);
				res.setResult(ex.getMessage());
				writer.write(JSON.toJSONString(res));
				writer.flush();
			} catch (IOException e) {
				logger.error("error get printWriter", e);
			}
		}
		return null;
	}
}
