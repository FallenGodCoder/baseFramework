package com.tc.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.Map.Entry;


public class BaseController {

	protected final org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 公用成功失败页
	 */
	protected final String VIEW_COMMON_RESULT = "common/CommMessage";
	/**
	 * 公用FACNY成功失败页
	 */
	protected final String VIEW_FANCY_RESULT = "common/ModalMessage";
	/**
	 * 公用FACNY成功code
	 */
	protected static final String SUCCESS = "success";
	/**
	 * 公用FACNY失败code
	 */
	protected static final String ERROR = "error";

	/**
	 * 返回公共成功失败页
	 * 
	 * @param code
	 * @param message
	 * @return
	 */
	protected ModelAndView getCommonView(String code, String message) {
		ModelAndView modelAndView = new ModelAndView(VIEW_COMMON_RESULT);
		modelAndView.addObject("code", code);
		modelAndView.addObject("message", message);

		return modelAndView;
	}

	/**
	 * 返回公共成功失败页
	 * 
	 * @param code
	 * @param message
	 * @param map
	 * @return
	 */
	protected String getCommonView(String code, String message, ModelMap map) {
		map.addAttribute("code", code);
		map.addAttribute("message", message);

		return VIEW_COMMON_RESULT;
	}

	/**
	 * 返回FANCY公共成功失败页
	 * 
	 * @param code
	 * @param message
	 * @return
	 */
	protected ModelAndView getFancyCommonView(String code, String message) {
		ModelAndView modelAndView = new ModelAndView(VIEW_FANCY_RESULT);
		modelAndView.addObject("code", code);
		modelAndView.addObject("message", message);

		return modelAndView;
	}

	/**
	 * 返回FANCY公共成功失败页
	 * 
	 * @param code
	 * @param message
	 * @param map
	 * @return
	 */
	protected String getFancyCommonView(String code, String message, ModelMap map) {
		map.addAttribute("code", code);
		map.addAttribute("message", message);

		return VIEW_FANCY_RESULT;
	}

	/**
	 * 获取Session中用户
	 * 
	 * @param session
	 * @return
	 */
	/*
	 * protected UserInfo getSessionUser(HttpSession session) { return
	 * (UserInfo)session.getAttribute(Constant.SESSION_USER); }
	 */

	/**
	 * @param t
	 * @param response
	 */
	public <T> void handleJsonResponse(T t, HttpServletResponse response) {
		try {
			MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
			MediaType jsonMimeType = MediaType.APPLICATION_JSON;
			if (jsonConverter.canWrite(t.getClass(), jsonMimeType)) {
				jsonConverter.write(t, jsonMimeType, new ServletServerHttpResponse(response));
			}
		} catch (Exception e) {
			logger.info("out put json error", e);
			e.printStackTrace();
		}
	}

	/**
	 * JSON打印返回结果
	 * 
	 * @param obj
	 */
	protected void printObjJson(Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		String json;
		try {
			json = mapper.writeValueAsString(obj);
			System.out.println("返回结果为:" + json);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	public String getUserFromSession(HttpServletRequest request, String... key) {
		HttpSession session = request.getSession();
		if (key != null && key.length > 0) {
			return (String) session.getAttribute(key[0]);
		}
		return (String) session.getAttribute(CommonConstants.USER_ID);
	}

	public void getParemeters(HttpServletRequest request, Object obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Enumeration<?> names = request.getParameterNames();
			while (names.hasMoreElements()) {
				String name = (String) names.nextElement();
				Object value = request.getParameterValues(name);
				if (null != value && !"".equals(value)) {
					map.put(name, value);
				}
			}
			Iterator<?> entries = map.entrySet().iterator();
			while (entries.hasNext()) {
				Entry<?, ?> entry = (Entry<?, ?>) entries.next();
				String[] data = ((String) entry.getKey()).split("\\.");
				String property = "";
				if (data != null && data.length == 2) {
					property = data[1];
				} else {
					continue;
				}
				BeanUtils.setProperty(obj, property, entry.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	public Response constructError(String msg) {
		Response response = new Response();
		response.setState(Response.STATE_EXCEPTION);
		response.setResult(msg);
		return response;
	}
}