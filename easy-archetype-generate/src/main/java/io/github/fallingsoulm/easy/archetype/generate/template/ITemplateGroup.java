package io.github.fallingsoulm.easy.archetype.generate.template;

import io.github.fallingsoulm.easy.archetype.generate.config.GlobalConfig;
import io.github.fallingsoulm.easy.archetype.generate.conver.IColumnTypeConver;
import io.github.fallingsoulm.easy.archetype.generate.core.TableInfoEntity;
import io.github.fallingsoulm.easy.archetype.generate.engine.ITemplateEngine;
import io.github.fallingsoulm.easy.archetype.generate.file.OutputFile;
import io.github.fallingsoulm.easy.archetype.generate.query.ITableQueryResult;

import java.util.List;

/**
 * 模板组
 *
 * @author luyanan
 * @since 2021/2/1
 **/
public interface ITemplateGroup {

	/**
	 * 模板组
	 * @return java.lang.String
	 * @since 2021/2/1
	 */
	String name();

	/**
	 * 代码生成
	 * @param tableInfoEntities 表信息
	 * @param templateEngine 模板引擎
	 * @param globalConfig 全局配置
	 * @return java.util.List<io.github.fallingsoulm.easy.archetype.framework.core.generate.file.OutputFile>
	 * @since 2021/2/1
	 */
	List<OutputFile> generate(List<TableInfoEntity> tableInfoEntities, ITemplateEngine templateEngine,
			GlobalConfig globalConfig);

	/**
	 * 查询需要生成的表的信息
	 * @param tableQueryResult 查询引擎
	 * @param globalConfig 全局配置
	 * @param columnTypeConver 类型转换器
	 * @return java.util.List<io.github.fallingsoulm.easy.archetype.framework.core.generate.core.TableInfoEntity>
	 * @since 2021/2/1
	 */
	List<TableInfoEntity> getGenerateTableInfo(ITableQueryResult tableQueryResult, GlobalConfig globalConfig,
			IColumnTypeConver columnTypeConver);

}
