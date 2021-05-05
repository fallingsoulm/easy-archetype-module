package io.github.fallingsoulm.easy.archetype.security.validatecode;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码异常
 *
 * @author luyanan
 * @since 2021/2/9
 **/
public class ValidateCodeException extends AuthenticationException {

	private static final long serialVersionUID = 1L;


	public ValidateCodeException(String msg) {
		super(msg);
	}
}
