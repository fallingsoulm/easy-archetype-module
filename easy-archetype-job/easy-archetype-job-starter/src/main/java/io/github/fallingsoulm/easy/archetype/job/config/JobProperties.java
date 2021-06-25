package io.github.fallingsoulm.easy.archetype.job.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 任务的配置文件
 *
 * @author luyanan
 * @since 2021/3/14
 **/
@Data
@ConfigurationProperties(prefix = JobProperties.PREFIX)
public class JobProperties {
	public static final String PREFIX = "easy.archetype.job";

	/**
	 * 是否开启
	 *
	 * @since 2021/3/20
	 */

	private Boolean enable;

	/**
	 * 实例名称
	 *
	 * @since 2021/3/14
	 */

	private String instanceName = "Scheduler";
}
