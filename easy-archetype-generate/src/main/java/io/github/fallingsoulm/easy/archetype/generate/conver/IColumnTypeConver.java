package io.github.fallingsoulm.easy.archetype.generate.conver;

/**
 * 字段映射(SQL-JAVA)
 *
 * @author luyanan
 * @since 2021/1/31
 **/
@FunctionalInterface
public interface IColumnTypeConver {

	/**
	 * 根据数据库类型查询Java类型
	 * @param jdbcType 数据库类型
	 * @return io.github.fallingsoulm.easy.archetype.framework.core.generate.conver.IColumnType
	 * @since 2021/1/31
	 */
	IColumnType convert(String jdbcType);

}
