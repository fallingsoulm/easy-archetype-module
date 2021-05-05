package io.github.fallingsoulm.easy.archetype.generate.template;

import io.github.fallingsoulm.easy.archetype.generate.config.GlobalConfig;
import io.github.fallingsoulm.easy.archetype.generate.config.TemplateConfig;
import io.github.fallingsoulm.easy.archetype.generate.core.TableInfoEntity;

import java.util.List;
import java.util.Map;

/**
 * 模板
 *
 * @author luyanan
 * @since 2021/2/1
 **/
public interface ITemplate {

	/**
	 * 设置全局配置
	 * @param globalConfig 全局配置
	 * @return void
	 * @since 2021/2/1
	 */
	void setGlobalConfig(GlobalConfig globalConfig);

	/**
	 * 设置表信息
	 * @param tableInfoEntity 表信息
	 * @return void
	 * @since 2021/2/1
	 */
	void setTableInfoEntity(TableInfoEntity tableInfoEntity);

	/**
	 * 生成配置文件
	 * @return io.github.fallingsoulm.easy.archetype.framework.core.generate.config.TemplateConfig
	 * @since 2021/2/1
	 */
	TemplateConfig config();

	/**
	 * 前置操作
	 * @param tableInfoEntity 表信息
	 * @param config 模板配置
	 * @param data 数据
	 * @return void
	 * @since 2021/2/1
	 */
	void before(TableInfoEntity tableInfoEntity, TemplateConfig config, Map<String, Object> data);

	/**
	 * 后置操作
	 * @param data 数据
	 * @return void
	 * @since 2021/2/1
	 */
	void after(Map<String, Object> data);

	/**
	 * 模板路径
	 * @return java.lang.String
	 * @since 2021/2/1
	 */
	String templatePath();

	/**
	 * 文件名Format
	 * @return java.lang.String
	 * @since 2021/2/1
	 */
	String fileNameFormat();

	/**
	 * 文件名后缀
	 * @return java.lang.String
	 * @since 2021/2/1
	 */
	String fileNameSuffix();

	/**
	 * 包路径
	 * @return java.lang.String
	 * @since 2021/2/1
	 */
	String pkg();

	/**
	 * 类路径
	 * @return java.lang.String
	 * @since 2021/2/1
	 */
	String classPkg();

	/**
	 * 全路径(类的路径+实体类)
	 * @return java.lang.String
	 * @since 2021/2/1
	 */
	String fullPkg();

	/**
	 * 类名
	 * @return java.lang.String
	 * @since 2021/2/1
	 */
	String className();

	/**
	 * 模板名
	 * @return java.lang.String
	 * @since 2021/2/1
	 */
	String module();

	/**
	 * 需要排除的字段
	 * @return java.util.List<java.lang.String>
	 * @since 2021/2/1
	 */
	List<String> excludeField();

}
