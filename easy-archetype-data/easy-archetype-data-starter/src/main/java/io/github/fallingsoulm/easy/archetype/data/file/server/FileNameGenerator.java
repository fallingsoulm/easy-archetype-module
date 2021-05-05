package io.github.fallingsoulm.easy.archetype.data.file.server;

import io.github.fallingsoulm.easy.archetype.data.file.FileInfo;

import java.io.InputStream;

/**
 * 文件名生成策略
 *
 * @author luyanan
 * @since 2021/2/22
 **/
public interface FileNameGenerator {


	/**
	 * 文件名生成
	 *
	 * @param fileInfo 文件详情
	 * @return java.lang.String 文件名
	 * @since 2021/2/22
	 */
	FileInfo generate(FileInfo fileInfo);
}
