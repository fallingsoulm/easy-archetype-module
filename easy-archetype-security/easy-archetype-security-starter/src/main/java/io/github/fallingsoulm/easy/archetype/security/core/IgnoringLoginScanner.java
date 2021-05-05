package io.github.fallingsoulm.easy.archetype.security.core;

import cn.hutool.core.collection.CollectionUtil;
import io.github.fallingsoulm.easy.archetype.security.annotation.IgnoringLogin;
import io.github.fallingsoulm.easy.archetype.security.security.SecurityProperties;
import lombok.Getter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 扫码忽略注解的接口
 *
 * @author luyanan
 * @since 2021/2/18
 **/
public class IgnoringLoginScanner implements InitializingBean {

	@Autowired(required = false)
	private SecurityProperties securityProperties;
	/**
	 * 忽略登录校验的url
	 *
	 * @since 2021/2/18
	 */

	private Set<String> ignoringLoginUrl = new HashSet<>();

	@Autowired
	private RequestMappingHandlerMapping requestMappingHandlerMapping;


	@Override
	public void afterPropertiesSet() {
		Map<RequestMappingInfo, HandlerMethod> handlerMethods =
				requestMappingHandlerMapping.getHandlerMethods();
		if (CollectionUtil.isNotEmpty(handlerMethods)) {
			for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
				HandlerMethod method = entry.getValue();
				if (method.hasMethodAnnotation(IgnoringLogin.class) ||
						method.getMethod().getDeclaringClass().isAnnotationPresent(IgnoringLogin.class)) {
					Set<String> patterns = entry.getKey().getPatternsCondition().getPatterns();
					ignoringLoginUrl.addAll(patterns);

				}
			}
		}
	}

	public Set<String> getIgnoringLoginUrl() {
		if (null != securityProperties && CollectionUtil.isNotEmpty(securityProperties.getIgnoringLoginUrl())) {
			ignoringLoginUrl.addAll(securityProperties.getIgnoringLoginUrl());
		}
		return ignoringLoginUrl;
	}
}
