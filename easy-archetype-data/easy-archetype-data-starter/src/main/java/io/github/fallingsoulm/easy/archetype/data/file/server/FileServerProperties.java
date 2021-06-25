package io.github.fallingsoulm.easy.archetype.data.file.server;

import io.github.fallingsoulm.easy.archetype.data.file.FileProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 文件服务端的配置
 *
 * @author luyanan
 * @since 2021/2/23
 **/
@Data
@ConfigurationProperties(FileServerProperties.PREFIX)
public class FileServerProperties {

	public final static String PREFIX = FileProperties.PREFIX + ".server";

	/**
	 * 存储类型(file/minio)
	 *
	 * @since 2021/2/25
	 */
	private String storetype;

	/**
	 * ftp的配置类
	 *
	 * @since 2021/2/23
	 */

	private Ftp ftp;

	/**
	 * Oss对象存储的配置
	 *
	 * @since 2021/2/23
	 */

	private Oss oss;

	/**
	 * 端点开启
	 *
	 * @since 2021/2/26
	 */
	private Boolean endpoint;

	/**
	 * 文件名生成策略
	 *
	 * @since 2021/4/17
	 */

	private FileNameGenerate filename = new FileNameGenerate();


	/**
	 * 文件名生成
	 *
	 * @author Administrator
	 * @since 2021/4/17
	 */
	@Data
	public static class FileNameGenerate {

		/**
		 * 类型
		 *
		 * @since 2021/4/17
		 */
		private String type = "hash";

		/**
		 * Hash槽的大小
		 *
		 * @since 2021/4/17
		 */

		private int hashGroove = 12;
	}


	@Data

	public static class Ftp {

		/**
		 * 域名/ip
		 *
		 * @since 2021/2/23
		 */

		private String host;


		/**
		 * 端口号
		 *
		 * @since 2021/2/23
		 */

		private int port = 21;

		/**
		 * 用户名
		 *
		 * @since 2021/2/23
		 */

		private String user = "anonymous";

		/**
		 * 密码
		 *
		 * @since 2021/2/23
		 */

		private String password = "";

		/**
		 * 编码
		 *
		 * @since 2021/2/23
		 */

		private String charset = "utf-8";
	}

	/**
	 * 对象存储的配置文件
	 *
	 * @author luyan
	 * @since 2021/2/23
	 */
	@Data
	public static class Oss {

		/**
		 * 对存服务的URL
		 *
		 * @since 2021/2/23
		 */

		private String endpoint;

		/**
		 * 区域
		 *
		 * @since 2021/2/23
		 */

		private String region;


		/**
		 * Access key就像用户ID，可以唯一标识你的账户
		 *
		 * @since 2021/2/23
		 */
		private String accessKey;


		/**
		 * 密钥
		 *
		 * @since 2021/2/23
		 */

		private String secretKey;

		/**
		 * 桶名
		 *
		 * @since 2021/2/23
		 */

		private String bucketName;
	}
}
