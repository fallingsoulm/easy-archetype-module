package io.github.fallingsoulm.easy.archetype.data.file;

import lombok.Data;

/**
 * 文件详情
 *
 * @author luyanan
 * @since 2021/4/17
 **/
@Data
public class FileInfo {


	/**
	 * 文件路径
	 *
	 * @since 2021/4/17
	 */

	private String path;


	/**
	 * 文件名
	 *
	 * @since 2021/4/17
	 */

	private String fileName;


	/**
	 * 文件数组
	 *
	 * @author Administrator
	 * @since 2021/4/17
	 */
	private byte[] content;




}
