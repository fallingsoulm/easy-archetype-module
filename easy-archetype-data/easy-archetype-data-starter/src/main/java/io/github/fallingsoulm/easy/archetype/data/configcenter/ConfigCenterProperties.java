package io.github.fallingsoulm.easy.archetype.data.configcenter;

import io.github.fallingsoulm.easy.archetype.framework.config.EasyArchetypeFrameworkProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 配置中心配置文件
 *
 * @author luyanan
 * @since 2021/1/30
 **/
@Data
@ConfigurationProperties(prefix = ConfigCenterProperties.PREFIX)
public class ConfigCenterProperties {

	public static final String PREFIX = EasyArchetypeFrameworkProperties.PREFIX + ".config.center";

	/**
	 * 是否开启
	 *
	 * @since 2021/1/30
	 */
	private Boolean enable;

}
