package io.github.fallingsoulm.easy.archetype.data.file.client;

import cn.hutool.core.lang.Assert;
import io.github.fallingsoulm.easy.archetype.data.file.IFileService;
import lombok.RequiredArgsConstructor;

import java.io.InputStream;

/**
 * 文件服务客户端的实现
 *
 * @author luyanan
 * @since 2021/2/22
 **/
@RequiredArgsConstructor
public class FileServiceClientImpl implements IFileService {
	private final IFileTransport fileTransport;

	@Override
	public String upload(String path, String fileName, InputStream is, boolean rename) {
		Assert.notBlank(path, "文件路径不能为空");
		Assert.isNull(is, "文件流不能为空");
		return fileTransport.upload(path, fileName, is, rename);
	}


	@Override
	public boolean removeFile(String path) {
		Assert.notBlank(path, "文件路径不能为空");
		return fileTransport.removeFile(path);
	}
}
