package io.github.fallingsoulm.easy.archetype.data.file;

import java.io.InputStream;

/**
 * 文件服务
 *
 * @author luyanan
 * @since 2021/2/22
 **/
public interface IFileService {


	/**
	 * 文件上传
	 *
	 * @param path     文件路径
	 * @param fileName 文件名
	 * @param is       文件流
	 * @param rename   是否重命名
	 * @return java.lang.String 文件的路径(相对路径)
	 * @since 2021/2/22
	 */
	String upload(String path, String fileName, InputStream is, boolean rename);


	/**
	 * 根据文件的相对路径删除文件
	 *
	 * @param path 文件的相对路径
	 * @return boolean
	 * @since 2021/2/22
	 */
	boolean removeFile(String path);
}
