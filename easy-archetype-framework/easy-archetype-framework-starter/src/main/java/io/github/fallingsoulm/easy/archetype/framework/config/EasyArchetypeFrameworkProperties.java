package io.github.fallingsoulm.easy.archetype.framework.config;

import io.github.fallingsoulm.easy.archetype.framework.thread.BusinessThreadPoolProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 配置文件
 *
 * @author luyanan
 * @since 2021/1/22
 **/
@Data
@ConfigurationProperties(EasyArchetypeFrameworkProperties.PREFIX)
public class EasyArchetypeFrameworkProperties {

	public static final String PREFIX = "easy.archetype.framework";

	/**
	 * 是否开启
	 *
	 * @author luyanan
	 * @since 2021/1/22
	 */
	private Boolean enable;

	/**
	 * 线程池配置
	 *
	 * @since 2021/1/22
	 */
	private BusinessThreadPoolProperties thread;



	/**
	 * spring的配置
	 *
	 * @since 2021/1/24
	 */
	private SpringProperties spring;

	/**
	 * spring的配置
	 *
	 * @author luyanan
	 * @since 2021/1/24
	 */
	@Data
	public static class SpringProperties {

		/**
		 * 开启开关
		 *
		 * @since 2021/1/24
		 */
		private Boolean enable;

	}



}
