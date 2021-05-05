package io.github.fallingsoulm.easy.archetype.data.file.server.storagestrategy;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.Assert;
import io.github.fallingsoulm.easy.archetype.data.file.server.FileServerProperties;
import io.github.fallingsoulm.easy.archetype.data.file.server.IFileStorageStrategy;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.io.InputStream;

/**
 * minno文件存储
 *
 * @author luyanan
 * @since 2021/2/23
 **/
@ConditionalOnProperty(prefix = FileServerProperties.PREFIX, name = "storeType", havingValue = "minio", matchIfMissing = false)
@RequiredArgsConstructor
public class MinioFileStorageStrategy implements IFileStorageStrategy, InitializingBean {

	private final FileServerProperties fileServerProperties;

	private MinioClient minioClient;

	private FileServerProperties.Oss oss;


	@SneakyThrows
	@Override
	public String upload(String path, String fileName, InputStream is) {
		String objectName = (path + "/" + fileName).replace("/+", "/");
		minioClient.putObject(oss.getBucketName(), objectName, is, is.available(), "application/octet-stream");
		return objectName;
	}

	@SneakyThrows
	@Override
	public boolean removeFile(String path) {
		minioClient.removeObject(oss.getBucketName(), path);
		return true;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		oss = fileServerProperties.getOss();
		Assert.notNull(oss, "oss配置不能为空");
		minioClient = new MinioClient(oss.getEndpoint(), oss.getAccessKey(), oss.getSecretKey());
	}
}
