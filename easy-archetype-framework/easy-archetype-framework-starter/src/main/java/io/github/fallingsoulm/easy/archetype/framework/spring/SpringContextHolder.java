package io.github.fallingsoulm.easy.archetype.framework.spring;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Spring工具类
 *
 * @author luyanan
 * @since 2021/1/23
 **/

@Configuration
@Slf4j
@Lazy(false)
public class SpringContextHolder extends org.springframework.web.util.WebUtils
		implements ApplicationContextAware, DisposableBean {

	private static ApplicationContext applicationContext = null;

	/**
	 * 取得存储在静态变量中的ApplicationContext.
	 *
	 * @return org.springframework.context.ApplicationContext
	 * @since 2021/1/23
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 清除SpringContextHolder中的ApplicationContext为Null.
	 *
	 * @return void
	 * @since 2021/1/23
	 */
	public static void clearHolder() {
		if (log.isDebugEnabled()) {
			log.debug("清除SpringContextHolder中的ApplicationContext:" + applicationContext);
		}
		applicationContext = null;
	}

	/**
	 * 从静态变量applicationContext 中取得Bean, 自动转型为所赋值的对象的类型
	 *
	 * @param name bean的名称
	 * @return T bean对象
	 * @since 2021/1/23
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		if (null == applicationContext) {
			return null;
		}
		return (T) applicationContext.getBean(name);
	}

	/**
	 * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 *
	 * @param requiredType
	 * @return T
	 * @since 2021/1/23
	 */
	public static <T> T getBean(Class<T> requiredType) {
		if (null == applicationContext) {
			return null;
		}
		return applicationContext.getBean(requiredType);
	}

	/**
	 * 发布事件
	 *
	 * @param event 事件
	 * @return void
	 * @since 2021/1/23
	 */
	public static void publishEvent(ApplicationEvent event) {
		if (applicationContext == null) {
			return;
		}
		applicationContext.publishEvent(event);
	}

	/**
	 * 实现DisposableBean接口, 在Context关闭时清理静态变量.
	 *
	 * @return void
	 * @since 2021/1/23
	 */
	@Override
	public void destroy() throws Exception {
		SpringContextHolder.clearHolder();
	}

	/**
	 * 实现ApplicationContextAware 接口, 注入到Context的静态变量中
	 *
	 * @param applicationContext
	 * @return void
	 * @since 2021/1/23
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextHolder.applicationContext = applicationContext;
	}

	/**
	 * 获取HttpServletRequest
	 *
	 * @return javax.servlet.http.HttpServletRequest
	 * @since 2021/1/23
	 */
	public static HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		return null == request ? null : request;
	}

	/**
	 * 获取HttpServletResponse
	 *
	 * @return javax.servlet.http.HttpServletResponse
	 * @since 2021/1/23
	 */
	public static HttpServletResponse getResponse() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
	}

	/**
	 * 设置cookie
	 *
	 * @param response        返回对象
	 * @param name            cookie 名称
	 * @param value           cookie value
	 * @param maxAgeInSeconds 存活时间
	 * @param domain          cookie域
	 * @return void
	 * @since 2021/1/23
	 */
	public static void setCookie(HttpServletResponse response, String name, String value, String domain,
								 int maxAgeInSeconds) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		cookie.setMaxAge(maxAgeInSeconds);
		cookie.setHttpOnly(true);
		if (StrUtil.isNotBlank(domain)) {
			cookie.setDomain(domain);
		}
		response.addCookie(cookie);
	}

	/**
	 * 设置cookie
	 *
	 * @param response        返回对象
	 * @param name            cookie 名称
	 * @param value           cookie value
	 * @param maxAgeInSeconds 存活时间
	 * @return void
	 * @since 2021/1/23
	 */
	public static void setCookie(HttpServletResponse response, String name, String value, int maxAgeInSeconds) {
		setCookie(response, name, value, null, maxAgeInSeconds);
	}

	/**
	 * 删除cookie
	 *
	 * @param response http 返回对象
	 * @param name     cookie name
	 * @param domain   cookie域
	 * @return void
	 * @since 2021/1/23
	 */
	public static void removeCookie(HttpServletResponse response, String name, String domain) {
		setCookie(response, name, null, domain, 0);
	}

	/**
	 * 获取cookie的值
	 *
	 * @param request http request对象
	 * @param name    cookie的名称
	 * @return java.lang.String
	 * @since 2021/1/23
	 */
	public static String getCookieVal(HttpServletRequest request, String name) {
		Cookie cookie = getCookie(request, name);
		return cookie != null ? cookie.getValue() : null;
	}

	/**
	 * 是否返回的是json请求, 返回含有 ResponseBody 或者 RestController注解
	 *
	 * @param handlerMethod handlerMethod
	 * @return boolean
	 * @since 2021/1/23
	 */
	public static boolean isBody(HandlerMethod handlerMethod) {
		return (AnnotationUtils.findAnnotation(handlerMethod.getMethod(), ResponseBody.class) != null) ||
				(AnnotationUtils.findAnnotation(handlerMethod.getBean().getClass(), RestController.class) != null);
	}

}
