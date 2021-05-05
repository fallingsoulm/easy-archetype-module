package io.github.fallingsoulm.easy.archetype.security.oauth.client;

import io.github.fallingsoulm.easy.archetype.security.oauth.SpringSecurityOauthProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * spring security 客户端配置
 *
 * @author luyanan
 * @since 2021/2/19
 **/
@Data
@ConfigurationProperties(prefix = SpringSecurityOauthClientProperties.PREFIX)
public class SpringSecurityOauthClientProperties {
	public static final String PREFIX = SpringSecurityOauthProperties.PREFIX + ".client";

	/**
	 * 资源id
	 *
	 * @since 2021/2/19
	 */

	private String resourceId;

	/**
	 * 授权中心的host地址
	 *
	 * @since 2021/2/19
	 */

	private String authorizationServerHost;

	/**
	 * 授权中心的服务名
	 *
	 * @since 2021/2/19
	 */

	private String authorizationServerName;

	/**
	 * 客户端id
	 *
	 * @since 2021/2/19
	 */

	private String clientId;

	/**
	 * 客户端密钥
	 *
	 * @since 2021/2/19
	 */

	private String clientSecret;
}
