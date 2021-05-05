package io.github.fallingsoulm.easy.archetype.security.security;

import cn.hutool.core.collection.CollectionUtil;
import io.github.fallingsoulm.easy.archetype.security.core.IgnoringLoginScanner;
import io.github.fallingsoulm.easy.archetype.security.validatecode.ValidateCodeFilter;
import io.github.fallingsoulm.easy.archetype.security.validatecode.ValidateCodeProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * web security配置
 *
 * @author luyanan
 * @since 2021/2/18
 **/
@Configuration
//@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private IgnoringLoginScanner ignoringLoginScanner;

	@Autowired(required = false)
	private HttpSecurityConfigHandler httpSecurityConfigHandler;

	@Autowired(required = false)
	private ValidateCodeProperties validateCodeProperties;

	@Autowired(required = false)
	private ValidateCodeFilter validateCodeFilter;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		List<String> antMatchers = new LinkedList<>();
		for (String s : ignoringLoginScanner.getIgnoringLoginUrl()) {
			antMatchers.add(s);
		}
		if (null != validateCodeFilter && null != validateCodeProperties && null != validateCodeProperties.getValidateCodeFilter() && validateCodeProperties.getValidateCodeFilter().equals(Boolean.TRUE)) {
			http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class);
		}
		http.authorizeRequests().antMatchers(antMatchers.toArray(new String[antMatchers.size()])).permitAll().anyRequest().authenticated();
		if (null != httpSecurityConfigHandler) {
			httpSecurityConfigHandler.configure(http);
		}
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		if (null != httpSecurityConfigHandler) {
			httpSecurityConfigHandler.configure(web);
		}
	}
}
