package io.github.fallingsoulm.easy.archetype.data.file.server;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import io.github.fallingsoulm.easy.archetype.data.file.FileInfo;

import java.io.InputStream;

/**
 * 文件名生成简单实现
 *
 * @author luyanan
 * @since 2021/2/22
 **/
public class SimpleFileNameGenerator implements FileNameGenerator {

	@Override
	public FileInfo generate(FileInfo fileInfo) {
		fileInfo.setFileName(IdUtil.fastSimpleUUID() + "." + FileUtil.extName(fileInfo.getFileName()));
		return fileInfo;
	}
}
