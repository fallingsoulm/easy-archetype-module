package io.github.fallingsoulm.easy.archetype.security.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.jwk.JwkTokenStore;
import org.springframework.util.Assert;

/**
 * 令牌配置
 *
 * @author luyanan
 * @since 2021/2/19
 **/
@ConditionalOnMissingBean(TokenConfig.class)
@Configuration
public class TokenConfig {


	@Autowired
	private SpringSecurityOauthProperties springSecurityOauthProperties;

	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}


	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		Assert.hasText(springSecurityOauthProperties.getTokenSigningKey(), "资源服务器密钥不能为空");
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		// 对称密钥,资源服务器使用该密钥进行验证
		converter.setSigningKey(springSecurityOauthProperties.getTokenSigningKey());
		return converter;
	}
}
