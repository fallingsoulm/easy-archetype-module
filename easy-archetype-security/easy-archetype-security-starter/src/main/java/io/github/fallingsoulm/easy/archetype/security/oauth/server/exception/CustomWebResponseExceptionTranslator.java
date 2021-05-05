package io.github.fallingsoulm.easy.archetype.security.oauth.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.ClientAuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

/**
 * 自定义异常转换器
 *
 * @author luyanan
 * @since 2021/2/19
 **/
public class CustomWebResponseExceptionTranslator implements WebResponseExceptionTranslator {
	@Override
	public ResponseEntity translate(Exception exception) throws Exception {
//		OAuth2Exception oAuth2Exception = (OAuth2Exception) e;
//		return ResponseEntity
//				.status(oAuth2Exception.getHttpErrorCode())
//				.body(new CustomOauthException(oAuth2Exception.getMessage()));
		if (exception instanceof OAuth2Exception) {
			OAuth2Exception oAuth2Exception = (OAuth2Exception) exception;
			if (exception instanceof ClientAuthenticationException) {
				return ResponseEntity
						.status(HttpStatus.OK)
//						.body(new CustomOauthException(oAuth2Exception.getMessage()));
						.body(new CustomOauthException("用户名或者密码错误"));
			} else {
				return ResponseEntity
						.status(HttpStatus.OK)
						.body(new CustomOauthException(oAuth2Exception.getMessage()));
			}

		} else if (exception instanceof AuthenticationException) {
			AuthenticationException authenticationException = (AuthenticationException) exception;
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(new CustomOauthException(authenticationException.getMessage()));
		}
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(new CustomOauthException(exception.getMessage()));
	}
}
