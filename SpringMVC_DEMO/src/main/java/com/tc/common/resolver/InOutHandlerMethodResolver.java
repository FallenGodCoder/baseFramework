package com.tc.common.resolver;

import com.tc.common.form.FileWebInput;
import com.tc.common.form.WebInput;
import com.tc.common.form.WebOutput;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * WebInput,OutInput,FileWebInput参数处理
 * Created by tangc on 2016/3/10.
 */
public class InOutHandlerMethodResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Class<?> clasz = methodParameter.getParameterType();
        return WebInput.class.equals(clasz) || WebOutput.class.equals(clasz) || FileWebInput.class.equals(clasz);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        Class<?> clasz = methodParameter.getParameterType();
        HttpServletRequest request = (HttpServletRequest)nativeWebRequest.getNativeRequest();
        if (WebInput.class.equals(clasz)){
            return WebInput.getInstance(request);
        } else if(FileWebInput.class.equals(clasz)){
            return new FileWebInput((MultipartHttpServletRequest) request);
        } else if(WebOutput.class.equals(clasz)){
            return new WebOutput(request, (HttpServletResponse)nativeWebRequest.getNativeResponse());
        }
        return null;
    }
}
