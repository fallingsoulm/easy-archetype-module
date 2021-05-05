package io.github.fallingsoulm.easy.archetype.data.file.client;

import io.github.fallingsoulm.easy.archetype.data.file.FileProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 文件服务客户端的配置
 *
 * @author luyanan
 * @since 2021/2/26
 **/
@Data
@ConfigurationProperties(prefix = FileClientProperties.PREFIX)
public class FileClientProperties {

	public static final String PREFIX = FileProperties.PREFIX + ".client";


	/**
	 * 传输类型
	 *
	 * @since 2021/2/26
	 */
	private String transport;


	/**
	 * http传输类型的时候的服务端的host
	 *
	 * @since 2021/2/26
	 */
	private String serverHost;

	/**
	 * 服务端 服务名
	 *
	 * @since 2021/2/26
	 */
	private String serverServiceName;
}
