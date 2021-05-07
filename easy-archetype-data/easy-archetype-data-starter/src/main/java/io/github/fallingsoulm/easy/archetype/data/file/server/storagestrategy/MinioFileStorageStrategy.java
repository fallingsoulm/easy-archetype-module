package io.github.fallingsoulm.easy.archetype.data.file.server.storagestrategy;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.Assert;
import io.github.fallingsoulm.easy.archetype.data.file.server.FileServerProperties;
import io.github.fallingsoulm.easy.archetype.data.file.server.IFileStorageStrategy;
import io.minio.*;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
		minioClient.putObject(PutObjectArgs.builder()
				.bucket(oss.getBucketName())
				.stream(is, is.available(), -1)
				.contentType("application/octet-stream")
				.object(objectName)
				.build());
		return objectName;
	}

	@SneakyThrows
	@Override
	public boolean removeFile(String path) {
		minioClient.removeObject(RemoveObjectArgs.builder().bucket(oss.getBucketName())
				.object(path).build());
		return true;
	}

	@SneakyThrows
	@Override
	public List<String> loopFiles(String dir) {


		ListObjectsArgs listObjectsArgs = ListObjectsArgs.builder().prefix(dir).bucket(oss.getBucketName()).build();
		List<String> fileNames = new ArrayList<>();
		Iterable<Result<Item>> results = minioClient.listObjects(listObjectsArgs);

		if (CollectionUtil.isEmpty(results)) {
			return fileNames;
		}
		for (Result<Item> result : results) {
			fileNames.add(result.get().objectName());
		}
		return fileNames;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		oss = fileServerProperties.getOss();
		Assert.notNull(oss, "oss配置不能为空");
		minioClient = MinioClient.builder().endpoint(oss.getEndpoint()).credentials(oss.getAccessKey(), oss.getSecretKey()).build();
	}
}
