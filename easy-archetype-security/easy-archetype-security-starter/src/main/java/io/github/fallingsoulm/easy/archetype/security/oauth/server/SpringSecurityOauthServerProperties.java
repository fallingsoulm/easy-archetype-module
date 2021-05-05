package io.github.fallingsoulm.easy.archetype.security.oauth.server;

import io.github.fallingsoulm.easy.archetype.security.oauth.SpringSecurityOauthProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Oauth 服务端配置
 *
 * @author luyanan
 * @since 2021/2/19
 **/
@Data
@ConfigurationProperties(prefix = SpringSecurityOauthServerProperties.PREFIX)
public class SpringSecurityOauthServerProperties {


	public static final String PREFIX = SpringSecurityOauthProperties.PREFIX + ".server";

	/**
	 * 刷新令牌的默认有效时间
	 *
	 * @since 2021/2/19
	 */

	private Integer refreshTokenValiditySeconds = 7200;

	/**
	 * 令牌的默认有效时间
	 *
	 * @since 2021/2/19
	 */

	private Integer accessTokenValiditySeconds = 259200;

}
