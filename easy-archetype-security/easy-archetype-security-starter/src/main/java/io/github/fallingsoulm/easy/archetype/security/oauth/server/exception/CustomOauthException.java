package io.github.fallingsoulm.easy.archetype.security.oauth.server.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * 自定义Oauth异常
 *
 * @author luyanan
 * @since 2021/2/19
 **/
@JsonSerialize(using = CustomOauthExceptionSerializer.class)
public class CustomOauthException extends OAuth2Exception {
	public CustomOauthException(String msg) {
		super(msg);
	}
}
