package io.github.fallingsoulm.easy.archetype.generate.core;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 表字段
 *
 * @author luyanan
 * @since 2021/1/31
 **/
@Data
public class TableFieldEntity implements Serializable {

	private static final long serialVersionUID = 7165087958927083194L;

	/**
	 * 字段名称
	 *
	 * @since 2021/2/1
	 */
	private String jdbcFieldName;

	/**
	 * 字段类型
	 *
	 * @since 2021/2/1
	 */
	private String jdbcType;

	/**
	 * 字段大小
	 *
	 * @since 2021/2/1
	 */
	private String jdbcLength;

	/**
	 * 字段默认值
	 *
	 * @since 2021/2/1
	 */
	private String columnDefault;

	/**
	 * 注释
	 *
	 * @since 2021/2/1
	 */
	private String comment;

	/**
	 * 是否为主键
	 *
	 * @since 2021/2/1
	 */
	private Boolean primaryKey;

	/**
	 * 是否为自增
	 *
	 * @since 2021/2/1
	 */
	private Boolean increment;

	/**
	 * 字段编码
	 *
	 * @since 2021/2/1
	 */
	private String jdbcColumnCharacter;

	/**
	 * 是否可以为空
	 *
	 * @since 2021/2/1
	 */
	private Boolean nullable;

	/**
	 * 字段名称
	 *
	 * @since 2021/2/1
	 */
	private String columnName;

	/**
	 * 字段类型
	 *
	 * @since 2021/2/1
	 */
	private String columnType;

	/**
	 * 字段引用
	 *
	 * @since 2021/2/1
	 */
	private String columnImport;

	/**
	 * 附加信息
	 *
	 * @since 2021/2/1
	 */
	private Map<String, Object> data;

}
