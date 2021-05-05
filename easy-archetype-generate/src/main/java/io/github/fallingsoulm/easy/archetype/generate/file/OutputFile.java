package io.github.fallingsoulm.easy.archetype.generate.file;

import lombok.Data;

/**
 * 输出文件
 *
 * @author luyanan
 * @since 2021/2/1
 **/
@Data
public class OutputFile {

	/**
	 * 输出文件名称
	 *
	 * @since 2021/2/1
	 */
	private String fileName;

	/**
	 * 输出文件路径
	 *
	 * @since 2021/2/1
	 */
	private String filePath;

	/**
	 * 输出内容
	 *
	 * @since 2021/2/1
	 */
	private String content;

}
