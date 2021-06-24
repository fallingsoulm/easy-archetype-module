package io.github.fallingsoulm.easy.archetype.data.file.server.storagestrategy;

import cn.hutool.core.lang.Assert;
import cn.hutool.extra.ftp.Ftp;
import io.github.fallingsoulm.easy.archetype.data.file.FileFilterArgs;
import io.github.fallingsoulm.easy.archetype.data.file.exception.FileException;
import io.github.fallingsoulm.easy.archetype.data.file.server.FileServerProperties;
import io.github.fallingsoulm.easy.archetype.data.file.server.IFileStorageStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Ftp文件存储
 *
 * @author luyanan
 * @since 2021/2/23
 **/
@RequiredArgsConstructor
public class FtpFileStorageStrategy implements IFileStorageStrategy {

	private final FileServerProperties fileServerProperties;

	private Ftp ftpClient;

	@PostConstruct
	public void init() {
		Assert.notNull(fileServerProperties.getFtp(), "ftp的配置不能为空");
		Assert.notBlank(fileServerProperties.getFtp().getHost(), "ftp的host配置不能为空");
		FileServerProperties.Ftp ftp = fileServerProperties.getFtp();
		ftpClient = new Ftp(ftp.getHost(), ftp.getPort(), ftp.getUser(), ftp.getPassword(), Charset.forName(ftp.getCharset()));
	}


	@Override
	public String upload(String path, String fileName, InputStream is) {

		if (!ftpClient.upload(path, fileName, is)) {
			throw new FileException(path + "/" + fileName + ":文件上传失败");
		}
		return path + "/" + fileName;
	}

	@Override
	public boolean removeFile(String path) {
		return ftpClient.delFile(path);
	}

	@Override
	public List<String> loopFiles(FileFilterArgs fileFilterArgs) {

		return ftpClient.ls(fileFilterArgs.getPrefix());
	}


}
