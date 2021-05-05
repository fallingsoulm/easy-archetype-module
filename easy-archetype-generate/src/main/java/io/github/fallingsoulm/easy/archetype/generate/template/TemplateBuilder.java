package io.github.fallingsoulm.easy.archetype.generate.template;

import cn.hutool.core.lang.Assert;
import io.github.fallingsoulm.easy.archetype.generate.config.GlobalConfig;
import io.github.fallingsoulm.easy.archetype.generate.core.TableInfoEntity;

/**
 * 模板Builder
 *
 * @author luyanan
 * @since 2021/2/2
 **/
public class TemplateBuilder {

	/**
	 * 全局配置
	 *
	 * @since 2021/2/2
	 */
	private GlobalConfig globalConfig;

	/**
	 * 表信息
	 *
	 * @since 2021/2/2
	 */
	private TableInfoEntity tableInfoEntity;

	public static TemplateBuilder builder() {
		return new TemplateBuilder();
	}

	public TemplateBuilder globalConfig(GlobalConfig globalConfig) {
		this.globalConfig = globalConfig;
		return this;
	}

	public TemplateBuilder tableInfoEntity(TableInfoEntity tableInfoEntity) {
		this.tableInfoEntity = tableInfoEntity;
		return this;
	}

	public ITemplate build(ITemplate iTemplate) {
		Assert.notNull(globalConfig, "globalConfig 全局配置不能为空");
		Assert.notNull(globalConfig, "tableInfoEntity 表信息不能为空");
		iTemplate.setGlobalConfig(globalConfig);
		iTemplate.setTableInfoEntity(tableInfoEntity);
		return iTemplate;
	}

}
