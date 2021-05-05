package io.github.fallingsoulm.easy.archetype.generate.ext.simple;

import io.github.fallingsoulm.easy.archetype.generate.config.TemplateConfig;
import io.github.fallingsoulm.easy.archetype.generate.core.TableInfoEntity;
import io.github.fallingsoulm.easy.archetype.generate.template.AbstractTemplate;

import java.util.Map;

/**
 * Manage实现类模板
 *
 * @author luyanan
 * @since 2021/2/2
 **/
public class ManageImplTemplate extends AbstractTemplate {

	/**
	 * 是否使用mybatisPlus方法
	 *
	 * @since 2021/2/2
	 */
	private boolean mybatisPlus;

	/**
	 * 实体类配置
	 *
	 * @since 2021/2/2
	 */
	private TemplateConfig entityConfig;

	/**
	 * mapper类配置
	 *
	 * @since 2021/2/2
	 */
	private TemplateConfig mapperConfig;

	/**
	 * manage的模板配置文件
	 *
	 * @since 2021/2/2
	 */
	private TemplateConfig manageConfig;

	public ManageImplTemplate(boolean mybatisPlus, TemplateConfig entityConfig, TemplateConfig mapperConfig,
			TemplateConfig manageConfig) {
		this.mybatisPlus = mybatisPlus;
		this.entityConfig = entityConfig;
		this.mapperConfig = mapperConfig;
		this.manageConfig = manageConfig;
	}

	@Override
	public void before(TableInfoEntity tableInfoEntity, TemplateConfig config, Map<String, Object> data) {
		if (mybatisPlus) {
			setImport("com.baomidou.mybatisplus.extension.service.impl.ServiceImpl");
		}
		setImport(entityConfig.getFullPkg(), mapperConfig.getFullPkg(), manageConfig.getFullPkg());
	}

	@Override
	public void after(Map<String, Object> data) {
		data.put("mybatisPlus", mybatisPlus);
		data.put("entityConfig", entityConfig);
		data.put("mapperConfig", mapperConfig);
		data.put("manageConfig", manageConfig);

	}

	@Override
	public String templatePath() {
		return "templates/manageImpl.ftl";
	}

	@Override
	public String fileNameFormat() {
		return "%sManageImpl";
	}

	@Override
	public String pkg() {
		return ".manage.impl";
	}

}
