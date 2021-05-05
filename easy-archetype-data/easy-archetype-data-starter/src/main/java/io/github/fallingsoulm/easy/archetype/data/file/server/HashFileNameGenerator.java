package io.github.fallingsoulm.easy.archetype.data.file.server;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.crypto.SecureUtil;
import io.github.fallingsoulm.easy.archetype.data.file.FileInfo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.UUID;

/**
 * 使用hash 来分配文件名
 *
 * @author luyanan
 * @since 2021/4/17
 **/
@Slf4j
@AllArgsConstructor
public class HashFileNameGenerator implements FileNameGenerator {
	private final FileServerProperties fileServerProperties;


	@Override
	public FileInfo generate(FileInfo fileInfo) {
		// 根据文件名生成hash
		int hashGroove = fileServerProperties.getFileName().getHashGroove();
		String md5 = SecureUtil.md5(IoUtil.toStream(fileInfo.getContent()));
		int index = Math.abs(md5.hashCode() % hashGroove);
		fileInfo.setPath(fileInfo.getPath() + "/" + index);
		fileInfo.setFileName(md5 + "." + FileUtil.extName(fileInfo.getFileName()));
		return fileInfo;
	}
}
