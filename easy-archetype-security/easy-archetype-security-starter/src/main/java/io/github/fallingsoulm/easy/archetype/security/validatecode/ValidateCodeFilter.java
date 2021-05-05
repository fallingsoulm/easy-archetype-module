package io.github.fallingsoulm.easy.archetype.security.validatecode;

import cn.hutool.core.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证码过滤器
 *
 * @author luyanan
 * @since 2021/2/10
 **/
@Slf4j
@ConditionalOnProperty(value = ValidateCodeProperties.PREFIX, name = "validateCodeFilter", havingValue = "true", matchIfMissing = false)
public class ValidateCodeFilter extends OncePerRequestFilter {

	@Autowired
	private ValidateCodeProperties validateCodeProperties;

	/**
	 * 验证码校验失败处理器
	 *
	 * @since 2021/2/10
	 */
	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;

	/**
	 * 验证码模板类
	 *
	 * @since 2021/2/10
	 */
	@Autowired
	private ValidateCodeTemplate validateCodeTemplate;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		if (null != validateCodeProperties.getValidateCodeFilter() && validateCodeProperties.getValidateCodeFilter().equals(Boolean.TRUE)
				&& CollectionUtil.isNotEmpty(validateCodeProperties.getValidateCodeUrls())
				&& validateCodeProperties.getValidateCodeUrls().contains(request.getRequestURI()
		) && request.getMethod().toLowerCase().equals("post")
		) {
			String uuid = request.getParameter(validateCodeProperties.getValidateCodeFilterKeyParameter());
			String code = request.getParameter(validateCodeProperties.getValidateCodeFilterCodeParameter());
			// 验证码校验
			log.debug("开始/login接口的验证码校验,{}:{},{}:{}", validateCodeProperties.getValidateCodeFilterKeyParameter(),
					uuid, validateCodeProperties.getValidateCodeFilterCodeParameter(), code);
			try {
				validateCodeTemplate.validate(uuid, code);
			} catch (ValidateCodeException e) {
				authenticationFailureHandler.onAuthenticationFailure(request, response, e);
				return;
			}
		}
		filterChain.doFilter(request, response);
	}
}
