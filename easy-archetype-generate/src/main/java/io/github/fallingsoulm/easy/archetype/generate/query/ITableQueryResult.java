package io.github.fallingsoulm.easy.archetype.generate.query;

import io.github.fallingsoulm.easy.archetype.generate.core.TableFieldEntity;
import io.github.fallingsoulm.easy.archetype.generate.core.TableIndexEntity;
import io.github.fallingsoulm.easy.archetype.generate.core.TableInfoEntity;

import javax.sql.DataSource;
import java.util.List;

/**
 * 数据库查询表信息
 *
 * @author luyanan
 * @since 2021/2/1
 **/
public interface ITableQueryResult {

	/**
	 * 查询数据库的表
	 * @param dataSource 连接信息
	 * @return java.util.List<TableInfoEntity>
	 * @since 2021/2/1
	 */
	List<TableInfoEntity> getTableInfoEntitys(DataSource dataSource);

	/**
	 * 根据表名称查询表字段
	 * @param tableName 表名称
	 * @param dataSource 连接信息
	 * @return java.util.List<io.github.fallingsoulm.easy.archetype.framework.core.generate.core.TableFieldEntity>
	 * @since 2021/2/1
	 */
	List<TableFieldEntity> getTableFieldEntitys(String tableName, DataSource dataSource);

	/**
	 * 根据表名查询索引
	 * @param tableName 表名称
	 * @param dataSource 连接信息
	 * @return java.util.List<io.github.fallingsoulm.easy.archetype.framework.core.generate.core.TableIndexEntity>
	 * @since 2021/2/1
	 */
	List<TableIndexEntity> getTableIndexEntitys(String tableName, DataSource dataSource);

}
