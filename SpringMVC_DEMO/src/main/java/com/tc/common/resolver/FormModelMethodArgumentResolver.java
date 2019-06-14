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
package com.tc.common.resolver;

import com.tc.common.annotation.FormModel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.util.Map;

public class FormModelMethodArgumentResolver implements HandlerMethodArgumentResolver {

    protected Log logger = LogFactory.getLog(this.getClass());

    /**
     * @return true if the parameter is annotated with {@link FormModel}
     *         or in default resolution mode also if it is not a simple type.
     */
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(FormModel.class);
    }

    /**
     * Resolve the argument from the model or if not found instantiate it with
     * its default if it is available. The model attribute is then populated
     * with request values via data binding and optionally validated
     * if {@code @java.validation.Valid} is present on the argument.
     *
     * @throws org.springframework.validation.BindException if data binding and validation result in an error
     *                       and the next method parameter is not of type {@link org.springframework.validation.Errors}.
     * @throws Exception     if WebDataBinder initialization fails.
     */
    public final Object resolveArgument(
            MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest request, WebDataBinderFactory binderFactory)
            throws Exception {

        String name = parameter.getParameterAnnotation(FormModel.class).value();
        Object target = (mavContainer.containsAttribute(name)) ?
                mavContainer.getModel().get(name) : createAttribute(name, parameter, binderFactory, request);

        WebDataBinder binder = binderFactory.createBinder(request, target, name);
        if (binder.getTarget() != null) {
            bindRequestParameters(binder, request, name);
            validateIfApplicable(binder, parameter);
            if (binder.getBindingResult().hasErrors()) {
                if (isBindExceptionRequired(binder, parameter)) {
                    throw new BindException(binder.getBindingResult());
                }
            }
        }

        // Add resolved attribute and BindingResult at the end of the model

        Map<String, Object> bindingResultModel = binder.getBindingResult().getModel();
        mavContainer.removeAttributes(bindingResultModel);
        mavContainer.addAllAttributes(bindingResultModel);

        return binder.getTarget();
    }

    private void bindRequestParameters(WebDataBinder binder, NativeWebRequest request, String prefix) {
        HttpServletRequest nativeRequest = (HttpServletRequest) request.getNativeRequest();
        ServletRequestDataBinder servletBinder = (ServletRequestDataBinder) binder;
        servletBinder.setFieldDefaultPrefix(prefix + ".");
        servletBinder.bind(nativeRequest);
    }

    /**
     * Extension point to create the model attribute if not found in the model.
     * The default implementation uses the default constructor.
     *
     * @param attributeName the name of the attribute, never {@code null}
     * @param parameter     the method parameter
     * @param binderFactory for creating WebDataBinder instance
     * @param request       the current request
     * @return the created model attribute, never {@code null}
     */
    protected Object createAttribute(String attributeName, MethodParameter parameter,
                                     WebDataBinderFactory binderFactory, NativeWebRequest request) throws Exception {

        return BeanUtils.instantiateClass(parameter.getParameterType());
    }

    /**
     * Validate the model attribute if applicable.
     * <p>The default implementation checks for {@code @javax.validation.Valid}.
     *
     * @param binder    the DataBinder to be used
     * @param parameter the method parameter
     */
    protected void validateIfApplicable(WebDataBinder binder, MethodParameter parameter) {
        Annotation[] annotations = parameter.getParameterAnnotations();
        for (Annotation annot : annotations) {
            if (annot.annotationType().getSimpleName().startsWith("Valid")) {
                Object hints = AnnotationUtils.getValue(annot);
                binder.validate(hints instanceof Object[] ? (Object[]) hints : new Object[]{hints});
            }
        }
    }

    /**
     * Whether to raise a {@link org.springframework.validation.BindException} on bind or validation errors.
     * The default implementation returns {@code true} if the next method
     * argument is not of type {@link org.springframework.validation.Errors}.
     *
     * @param binder    the data binder used to perform data binding
     * @param parameter the method argument
     */
    protected boolean isBindExceptionRequired(WebDataBinder binder, MethodParameter parameter) {
        int i = parameter.getParameterIndex();
        Class<?>[] paramTypes = parameter.getMethod().getParameterTypes();
        boolean hasBindingResult = (paramTypes.length > (i + 1) && Errors.class.isAssignableFrom(paramTypes[i + 1]));

        return !hasBindingResult;
    }
}