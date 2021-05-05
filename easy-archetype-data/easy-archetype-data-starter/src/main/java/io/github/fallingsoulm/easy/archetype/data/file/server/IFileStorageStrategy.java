package io.github.fallingsoulm.easy.archetype.data.file.server;

import cn.hutool.core.io.IoUtil;
import io.github.fallingsoulm.easy.archetype.data.file.FileInfo;

import java.io.InputStream;

/**
 * 文件存储策略
 *
 * @author luyanan
 * @since 2021/2/22
 **/
public interface IFileStorageStrategy {
	/**
	 * 文件上传
	 *
	 * @param fileInfo 文件
	 * @return java.lang.String 文件的路径(相对路径)
	 * @since 2021/2/22
	 */
	default String upload(FileInfo fileInfo) {
		return upload(fileInfo.getPath(), fileInfo.getFileName(), IoUtil.toStream(fileInfo.getContent()));
	}


	String upload(String path, String fileName, InputStream is);

	/**
	 * 根据文件的相对路径删除文件
	 *
	 * @param path 文件的相对路径
	 * @return boolean
	 * @since 2021/2/22
	 */
	boolean removeFile(String path);

}
