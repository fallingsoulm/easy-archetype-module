package io.github.fallingsoulm.easy.archetype.data.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文件过滤参数
 *
 * @author luyanan
 * @since 2021/6/13
 **/
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class FileFilterArgs {


	/**
	 * 前缀
	 *
	 * @author luyanan
	 * @since 2021/6/13
	 */
	private String prefix;


	/**
	 * 是否递归查找，如果是false,就模拟文件夹结构查找。
	 *
	 * @author luyanan
	 * @since 2021/6/13
	 */
	private Boolean recursive;


	/**
	 * 最大条数
	 *
	 * @author luyanan
	 * @since 2021/6/13
	 */
	private int size = 100;

	/**
	 * 以什么文件开始
	 *
	 * @author luyanan
	 * @since 2021/6/13
	 */
	private String startAfter;
}
