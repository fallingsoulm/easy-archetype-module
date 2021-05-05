package io.github.fallingsoulm.easy.archetype.data.logger;

import io.github.fallingsoulm.easy.archetype.framework.config.EasyArchetypeFrameworkProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 日志配置文件
 *
 * @author luyanan
 * @since 2021/2/7
 **/
@Data
@ConfigurationProperties(prefix = LoggerProperties.PREFIX)
public class LoggerProperties {

	public static final String PREFIX = EasyArchetypeFrameworkProperties.PREFIX + ".logger";

	/**
	 * 是否开启
	 *
	 * @since 2021/1/23
	 */
	private Boolean enable;

	/**
	 * 日志打印
	 *
	 * @since 2021/2/7
	 */
	private Boolean loggerPrint;

}
