package io.github.fallingsoulm.easy.archetype.security.oauth.client;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import io.github.fallingsoulm.easy.archetype.security.core.IgnoringLoginScanner;
import io.github.fallingsoulm.easy.archetype.security.oauth.client.exception.AuthExceptionEntryPoint;
import io.github.fallingsoulm.easy.archetype.security.oauth.client.exception.CustomAccessDeniedHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Set;

/**
 * 资源服务配置
 *
 * @author luyanan
 * @since 2021/2/19
 **/
@Slf4j
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {


	@Autowired
	private IgnoringLoginScanner ignoringLoginScanner;
	@Autowired
	private TokenStore tokenStore;

	@Autowired(required = false)
	private DiscoveryClient discoveryClient;
	@Autowired
	private SpringSecurityOauthClientProperties securityOauthClientProperties;

	@Autowired
	private MyBearerTokenExtractor myBearerTokenExtractor;

//	/**
//	 * 资源服务令牌解析服务
//	 *
//	 * @return
//	 */
//	@Bean
//	public ResourceServerTokenServices tokenServices() {
//
//		//使用远程服务请求授权服务器校验token , 必须指定校验token 的url,client_id,client_secret
//		RemoteTokenServices services = new RemoteTokenServices();
//		String authorizationServerHost = getAuthorizationServerHost();
//		Assert.hasText(authorizationServerHost, "授权服务器的host配置不能为空");
//		services.setCheckTokenEndpointUrl(authorizationServerHost + "/uaa/oauth/check_token");
//		services.setClientId(securityOauthClientProperties.getClientId());
//		services.setClientSecret(securityOauthClientProperties.getClientSecret());
//		return services;
//	}

	/**
	 * 获取授权中心的地址
	 * 如果服务名配置并且是cloud环境,则通过授权服务名从注册中心获取,
	 * 否则通过authorizationServerHost 配置获取
	 *
	 * @return java.lang.String
	 * @since 2021/2/19
	 */
	private String getAuthorizationServerHost() {

		if (null != discoveryClient && StrUtil.isNotBlank(securityOauthClientProperties.getAuthorizationServerName())) {
			List<ServiceInstance> instances = discoveryClient.getInstances(securityOauthClientProperties.getAuthorizationServerName());
			if (CollectionUtil.isNotEmpty(instances)) {
				return instances.stream().map(ServiceInstance::getUri).findFirst().get().getPath();
			}
		}
		return securityOauthClientProperties.getAuthorizationServerHost();
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId(securityOauthClientProperties.getResourceId())
				.tokenStore(tokenStore)
//				.tokenServices(tokenServices())
				.tokenExtractor(myBearerTokenExtractor)
				.stateless(true)
				.authenticationEntryPoint(new AuthExceptionEntryPoint())
				.accessDeniedHandler(new CustomAccessDeniedHandler());
	}


	@Override
	public void configure(HttpSecurity http) throws Exception {
		Set<String> ignoringLoginUrl = ignoringLoginScanner.getIgnoringLoginUrl();
		http.authorizeRequests().antMatchers(ignoringLoginUrl.toArray(new String[ignoringLoginUrl.size()])).permitAll();

	}
}
