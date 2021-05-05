package io.github.fallingsoulm.easy.archetype.generate.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 表信息
 *
 * @author luyanan
 * @since 2021/2/1
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TableInfoEntity implements Serializable {

	private static final long serialVersionUID = -1928615724129283736L;

	/**
	 * 表名称
	 *
	 * @since 2021/2/1
	 */
	private String tableName;

	/**
	 * 处理这个后的名称
	 *
	 * @since 2021/2/1
	 */
	private String entityName;

	/**
	 * 注释
	 *
	 * @since 2021/2/1
	 */
	private String comment;

	/**
	 * 数据库引擎
	 *
	 * @since 2021/2/1
	 */
	private String engine;

	/**
	 * 创建时间
	 *
	 * @since 2021/2/1
	 */
	private Date createTime;

	/**
	 * 数据库编码
	 *
	 * @since 2021/2/1
	 */
	private String encode;

	/**
	 * 字段信息
	 *
	 * @since 2021/2/1
	 */
	private List<TableFieldEntity> tableFieldEntityList;

	/**
	 * 索引信息
	 *
	 * @since 2021/2/1
	 */
	private List<TableIndexEntity> tableIndexEntityList;

	/**
	 * 附加信息
	 *
	 * @since 2021/2/1
	 */
	private Map<String, Object> data;

	/**
	 * id字段名称
	 *
	 * @since 2021/2/1
	 */
	private String idFieldName;

	/**
	 * id的字段类型
	 *
	 * @since 2021/2/1
	 */
	private String idFieldType;

}
