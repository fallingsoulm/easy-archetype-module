package io.github.fallingsoulm.easy.archetype.generate.core;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Map;

/**
 * 索引字段
 *
 * @author luyanan
 * @since 2021/2/1
 **/
@Builder
@Data
@Accessors(chain = true)
public class TableIndexEntity implements Serializable {

	private static final long serialVersionUID = 2694910759732598808L;

	/**
	 * 索引名称
	 *
	 * @since 2021/2/1
	 */
	private String name;

	/**
	 * 索引字段
	 *
	 * @since 2021/2/1
	 */
	private String column;

	/**
	 * 索引类型
	 *
	 * @since 2021/2/1
	 */
	private String indexType;

	/**
	 * 索引方法
	 *
	 * @since 2021/2/1
	 */
	private String indexMethod;

	/**
	 * 索引注释
	 *
	 * @since 2021/2/1
	 */
	private String indexComment;

	/**
	 * 附加信息
	 *
	 * @since 2021/2/1
	 */
	private Map<String, Object> data;

}
