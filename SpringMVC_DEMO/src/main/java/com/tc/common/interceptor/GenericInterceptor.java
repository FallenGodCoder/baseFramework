package com.tc.common.interceptor;

import com.tc.common.utils.RequestUtils;
import com.tc.common.utils.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 通用拦截器
 * Created by tangcheng on 2016/3/14.
 */
public class GenericInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(GenericInterceptor.class);

    private UrlPathHelper urlPathHelper = new UrlPathHelper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = urlPathHelper.getLookupPathForRequest(request);
        if (!RequestUtils.isAjaxRequest(request)) {
            int point = url.lastIndexOf(".");
            String path = point == -1 ? url.substring(1) : url.substring(1, point);
            path = path.replaceAll("/", "_");
            request.setAttribute("ccsLocation", path);
        }
        request.setAttribute("contextPath",request.getContextPath());
        request.setAttribute("requestUrl", url);
        //获取contextPath，如果获取不到就添加一个
        RequestUtils.getContextPath(request);
        ResponseUtils.setNoCache(response);

        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        super.afterConcurrentHandlingStarted(request, response, handler);
    }
}
