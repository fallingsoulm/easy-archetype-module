package io.github.fallingsoulm.easy.archetype.data.file.server;

import io.github.fallingsoulm.easy.archetype.data.file.FileProperties;
import io.github.fallingsoulm.easy.archetype.data.file.FileTemplate;
import io.github.fallingsoulm.easy.archetype.data.file.IFileService;
import io.github.fallingsoulm.easy.archetype.data.file.server.storagestrategy.FtpFileStorageStrategy;
import io.github.fallingsoulm.easy.archetype.data.file.server.storagestrategy.MinioFileStorageStrategy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 文件服务 自动配置类
 *
 * @author luyanan
 * @since 2021/2/25
 **/
@EnableConfigurationProperties({FileProperties.class, FileServerProperties.class})
@Configuration
public class FileServiceAutoConfiguration {


	@Bean
	public FileTemplate fileTemplate(FileProperties fileProperties, IFileService fileService) {
		return new FileTemplate(fileService, fileProperties);
	}

	/**
	 * 存储服务
	 *
	 * @param fileStorageStrategy
	 * @return io.github.fallingsoulm.easy.archetype.framework.data.file.IFileService
	 * @since 2021/2/25
	 */
	@Bean
	public IFileService fileService(IFileStorageStrategy fileStorageStrategy, FileNameGenerator fileNameGenerator) {
		return new FileServiceServerImpl(fileStorageStrategy, fileNameGenerator);
	}

	/**
	 * ftp存储
	 *
	 * @param fileServerProperties
	 * @return io.github.fallingsoulm.easy.archetype.framework.data.file.server.IFileStorageStrategy
	 * @since 2021/2/25
	 */
	@Bean
	@ConditionalOnProperty(prefix = FileServerProperties.PREFIX, name = "storetype", havingValue = "ftp", matchIfMissing = false)
	public IFileStorageStrategy ftpFileStorageStrategy(FileServerProperties fileServerProperties) {
		return new FtpFileStorageStrategy(fileServerProperties);
	}

	/**
	 * minio存储
	 *
	 * @param fileServerProperties
	 * @return io.github.fallingsoulm.easy.archetype.framework.data.file.server.IFileStorageStrategy
	 * @since 2021/2/25
	 */
	@Bean
	@ConditionalOnProperty(prefix = FileServerProperties.PREFIX, name = "storetype", havingValue = "minio", matchIfMissing = false)
	public IFileStorageStrategy minioFileStorageStrategy(FileServerProperties fileServerProperties) {
		return new MinioFileStorageStrategy(fileServerProperties);
	}

	/**
	 * 文件服务端点
	 *
	 * @return io.github.fallingsoulm.easy.archetype.framework.data.file.server.FileServiceEndpoint
	 * @since 2021/2/26
	 */
	@ConditionalOnProperty(prefix = FileServerProperties.PREFIX, name = "endpoint", havingValue = "true", matchIfMissing = false)
	@Bean
	public FileServiceEndpoint fileServiceEndpoint() {
		return new FileServiceEndpoint();
	}

	@ConditionalOnProperty(prefix = FileServerProperties.PREFIX, name = "filename.type", havingValue = "simple", matchIfMissing = false)
	@Bean
	public FileNameGenerator simpleFileNameGenerator() {
		return new SimpleFileNameGenerator();
	}


	@ConditionalOnProperty(prefix = FileServerProperties.PREFIX, name = "filename.type", havingValue = "hash", matchIfMissing = true)
	@Bean
	public FileNameGenerator hashFileNameGenerator(FileServerProperties fileServerProperties) {
		return new HashFileNameGenerator(fileServerProperties);
	}
}
