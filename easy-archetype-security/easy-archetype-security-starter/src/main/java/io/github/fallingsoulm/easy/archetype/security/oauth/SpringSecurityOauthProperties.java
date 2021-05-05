package io.github.fallingsoulm.easy.archetype.security.oauth;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * spring Oauth 的配置
 *
 * @author luyanan
 * @since 2021/2/19
 **/

@Data
@ConfigurationProperties(prefix = SpringSecurityOauthProperties.PREFIX)
public class SpringSecurityOauthProperties {
	public static final String PREFIX = "easy.archetype.security.oauth";


	/**
	 * <p>使用token 令牌时候的资源服务秘钥</p>
	 *
	 * @author luyanan
	 * @since 2020/9/9
	 */
	private String tokenSigningKey;

}
