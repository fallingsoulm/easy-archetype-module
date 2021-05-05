package io.github.fallingsoulm.easy.archetype.security.oauth.server;

import io.github.fallingsoulm.easy.archetype.security.oauth.SpringSecurityOauthProperties;
import io.github.fallingsoulm.easy.archetype.security.oauth.TokenConfig;
import io.github.fallingsoulm.easy.archetype.security.oauth.server.exception.CustomWebResponseExceptionTranslator;
import io.github.fallingsoulm.easy.archetype.security.security.annotation.EnableSecurity;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Oauth 服务端配置
 *
 * @author luyanan
 * @since 2021/2/19
 **/
@EnableConfigurationProperties({SpringSecurityOauthServerProperties.class,
		SpringSecurityOauthProperties.class
})
@EnableSecurity
@Configuration
@Import({TokenConfig.class, AuthorizationServer.class})
public class OauthServerAutoConfiguration {


	@Bean
	public CustomWebResponseExceptionTranslator exceptionTranslator() {
		return new CustomWebResponseExceptionTranslator();
	}

	// 默认处于安全，会把UsernameNotFoundException转为BadCredentialsException，就是 “坏的凭据”，注入下面配置的bean
	@Bean
	public AuthenticationProvider daoAuthenticationProvider(UserDetailsService userDetailsService) {
		DaoAuthenticationProvider impl = new DaoAuthenticationProvider();
		impl.setUserDetailsService(userDetailsService);
		impl.setHideUserNotFoundExceptions(false);
		return impl;
	}
}
