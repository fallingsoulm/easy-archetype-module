package io.github.fallingsoulm.easy.archetype.data.file;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 文件配置文件
 *
 * @author luyanan
 * @since 2021/2/22
 **/
@Data
@ConfigurationProperties(prefix = FileProperties.PREFIX)
public class FileProperties {

	public final static String PREFIX = "easy.archetype.framework.file";

	/**
	 * 文件路径的host
	 *
	 * @since 2021/2/22
	 */
	private String fileHost;






}
