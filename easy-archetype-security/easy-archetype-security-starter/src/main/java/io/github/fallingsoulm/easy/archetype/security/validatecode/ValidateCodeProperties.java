package io.github.fallingsoulm.easy.archetype.security.validatecode;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashSet;
import java.util.Set;

/**
 * 验证码配置文件
 *
 * @author luyanan
 * @since 2021/2/18
 **/
@ConfigurationProperties(prefix = ValidateCodeProperties.PREFIX)
@Data
public class ValidateCodeProperties {

	public static final String PREFIX = "easy.archetype.security.validatecode";

	/**
	 * 是否开启
	 *
	 * @since 2021/2/18
	 */

	private Boolean enable;
	/**
	 * 验证码的类型
	 * math(算数)/char(字母)
	 *
	 * @since 2021/2/18
	 */

	private String captchaType;

	/**
	 * 使用redis存储的时候的key
	 *
	 * @since 2021/2/18
	 */

	private String redisStorageKey = "captcha_codes:{0}";


	/**
	 * 是否开启验证码Filter过滤
	 *
	 * @since 2021/2/18
	 */

	private Boolean validateCodeFilter;

	/**
	 * 验证码过滤的url
	 *
	 * @since 2021/2/18
	 */

	private Set<String> validateCodeUrls = new HashSet<>();
	/**
	 * 验证码的过滤的key
	 *
	 * @since 2021/2/18
	 */

	private String validateCodeFilterKeyParameter = "uuid";

	/**
	 * 验证码过滤的code
	 *
	 * @since 2021/2/18
	 */

	private String validateCodeFilterCodeParameter = "code";

}
