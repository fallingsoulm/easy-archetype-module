package io.github.fallingsoulm.easy.archetype.security.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;

/**
 * HttpSecurity的安全访问配置
 *
 * @author luyanan
 * @since 2021/2/18
 **/
@FunctionalInterface
public interface HttpSecurityConfigHandler {

	/**
	 * 安全配置
	 *
	 * @param http
	 * @return void
	 * @throws Exception
	 * @since 2021/2/18
	 */
	void configure(HttpSecurity http) throws Exception;


	/**
	 * 安全配置
	 *
	 * @param web
	 * @return void
	 * @since 2021/2/19
	 */
	default void configure(WebSecurity web) {

	}
}
