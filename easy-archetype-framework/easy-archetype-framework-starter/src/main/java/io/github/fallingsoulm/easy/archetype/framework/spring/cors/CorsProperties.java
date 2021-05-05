package io.github.fallingsoulm.easy.archetype.framework.spring.cors;

import io.github.fallingsoulm.easy.archetype.framework.config.EasyArchetypeFrameworkProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 跨域配置类
 *
 * @author luyanan
 * @since 2021/2/10
 **/
@Data
@ConfigurationProperties(CorsProperties.PREFIX)
public class CorsProperties {
	public static final String PREFIX = EasyArchetypeFrameworkProperties.PREFIX + ".spring.cors";

	/**
	 * 是否启动
	 *
	 * @since 2021/2/10
	 */
	private Boolean enable = false;

	/**
	 * 允许跨域的路径
	 *
	 * @since 2021/2/10
	 */
	private String pathPattern = "/**";


	/**
	 * 允许跨域的域名,多个用,分割
	 *
	 * @since 2021/2/10
	 */
	private String origins = "*";


	/**
	 * 允许跨域的请求方法,多个用,分割
	 *
	 * @since 2021/2/10
	 */
	private String methods = "*";

	/**
	 * 允许跨域的header,多个用,分割
	 *
	 * @since 2021/2/10
	 */
	private String headers = "*";
	/**
	 * 是否允许携带cookie信息
	 *
	 * @since 2021/2/10
	 */
	private boolean allowCredentials = true;

	/**
	 * maxAge表明在maxAge秒内，不需要再发送预检验请求，可以缓存该结果
	 *
	 * @since 2021/2/10
	 */
	private long maxAge = 3600;

	/**
	 * 排除的请求头
	 *
	 * @since 2021/2/10
	 */
	private String exposedHeaders;
}
