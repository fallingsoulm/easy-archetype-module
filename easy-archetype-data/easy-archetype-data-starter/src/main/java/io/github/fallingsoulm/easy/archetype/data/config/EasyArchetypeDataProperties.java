package io.github.fallingsoulm.easy.archetype.data.config;

import io.github.fallingsoulm.easy.archetype.data.logger.LoggerProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 数据模块自动配置
 *
 * @author luyanan
 * @since 2021/3/15
 **/
@Data
@ConfigurationProperties(prefix = EasyArchetypeDataProperties.PREFIX)
public class EasyArchetypeDataProperties {
	public static final String PREFIX = "easy.archetype.data";
	/**
	 * redis的配置
	 *
	 * @since 2021/1/22
	 */
	private RedisProperties redis;

	/**
	 * 缓存的配置
	 *
	 * @since 2021/1/22
	 */
	private CacheProperties cache;

	/**
	 * Mybatis Plus的配置
	 *
	 * @since 2021/1/22
	 */
	private MybatisPlusProperties mybatisPlus;

	/**
	 * 日志的配置
	 *
	 * @since 2021/1/23
	 */
	private LoggerProperties logger;

	/**
	 * Mybatis Plus的配置
	 *
	 * @author luyanan
	 * @since 2021/1/22
	 */
	@Data
	public static class MybatisPlusProperties {

		/**
		 * 是否开启
		 *
		 * @since 2021/1/22
		 */
		private Boolean enable;

	}

	/**
	 * 缓存的配置
	 *
	 * @author luyanan
	 * @since 2021/1/22
	 */
	@Data
	public static class CacheProperties {

		/**
		 * 是否开启
		 *
		 * @since 2021/1/22
		 */
		private Boolean enable;

	}

	/**
	 * redis的配置
	 *
	 * @author luyanan
	 * @since 2021/1/22
	 */
	@Data
	public static class RedisProperties {

		private Boolean enable;

	}


}
