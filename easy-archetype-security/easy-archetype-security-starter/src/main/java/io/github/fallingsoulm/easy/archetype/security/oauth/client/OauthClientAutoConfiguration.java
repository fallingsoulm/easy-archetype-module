package io.github.fallingsoulm.easy.archetype.security.oauth.client;

import io.github.fallingsoulm.easy.archetype.security.oauth.SpringSecurityOauthProperties;
import io.github.fallingsoulm.easy.archetype.security.oauth.TokenConfig;
import io.github.fallingsoulm.easy.archetype.security.security.annotation.EnableSecurity;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Oauth2.0 客户端自动配置类
 *
 * @author luyanan
 * @since 2021/2/19
 **/
@Configuration
@EnableSecurity
@EnableConfigurationProperties({SpringSecurityOauthProperties.class,
		SpringSecurityOauthClientProperties.class})
@Import({TokenConfig.class, ResourceServerConfig.class})
public class OauthClientAutoConfiguration {
	@Bean
	public MyBearerTokenExtractor myBearerTokenExtractor() {
		return new MyBearerTokenExtractor();
	}
}
