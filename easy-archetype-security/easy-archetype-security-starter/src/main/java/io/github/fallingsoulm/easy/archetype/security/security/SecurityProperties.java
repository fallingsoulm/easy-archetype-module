package io.github.fallingsoulm.easy.archetype.security.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

/**
 * 安全框架的配置文件
 *
 * @author luyanan
 * @since 2021/2/18
 **/
@Data
@ConfigurationProperties(prefix = SecurityProperties.PREFIX)
public class SecurityProperties {

	public static final String PREFIX = "easy.archetype.security";


	/**
	 * 忽略登录的url
	 * 与加了@IgnoringLogin注解的地址相加
	 *
	 * @since 2021/2/18
	 */
	private Set<String> ignoringLoginUrl;
}
