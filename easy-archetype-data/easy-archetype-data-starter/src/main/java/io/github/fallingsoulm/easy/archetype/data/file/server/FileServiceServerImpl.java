package io.github.fallingsoulm.easy.archetype.data.file.server;

import cn.hutool.core.io.IoUtil;
import io.github.fallingsoulm.easy.archetype.data.file.FileFilterArgs;
import io.github.fallingsoulm.easy.archetype.data.file.FileInfo;
import io.github.fallingsoulm.easy.archetype.data.file.IFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.List;

/**
 * 文件服务端实现
 *
 * @author luyanan
 * @since 2021/2/22
 **/
@Slf4j
@RequiredArgsConstructor
public class FileServiceServerImpl implements IFileService {

	private final IFileStorageStrategy fileStorageStrategy;

	private final FileNameGenerator fileNameGenerator;

	@Override
	public String upload(String path, String fileName, InputStream is, boolean rename) {
		log.debug("文件上传,文件路径:{},文件名:{},是否重命名:{}", path, fileName, rename);

		FileInfo fileInfo = new FileInfo();
		fileInfo.setFileName(fileName);
		fileInfo.setPath(path);
		fileInfo.setContent(IoUtil.readBytes(is));
		if (rename) {
			//文件名重命名
			fileInfo = fileNameGenerator.generate(fileInfo);
		}
		return fileStorageStrategy.upload(fileInfo);
	}


	@Override
	public boolean removeFile(String path) {
		log.debug("删除文件:{}", path);
		return fileStorageStrategy.removeFile(path);
	}

	@Override
	public List<String> loopFiles(FileFilterArgs fileFilterArgs) {

		return fileStorageStrategy.loopFiles(fileFilterArgs);
	}
}
