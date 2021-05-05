package io.github.fallingsoulm.easy.archetype.generate.ext.simple;

import io.github.fallingsoulm.easy.archetype.generate.config.TemplateConfig;
import io.github.fallingsoulm.easy.archetype.generate.core.TableInfoEntity;
import io.github.fallingsoulm.easy.archetype.generate.template.AbstractTemplate;

import java.util.Map;

/**
 * service实现的模板代码
 *
 * @author luyanan
 * @since 2021/2/2
 **/
public class ServiceImplTemplate extends AbstractTemplate {

	/**
	 * manage模板配置文件
	 *
	 * @since 2021/2/2
	 */
	private TemplateConfig manageConfig;

	/**
	 * service配置文件
	 *
	 * @since 2021/2/2
	 */
	private TemplateConfig serviceConfig;

	public ServiceImplTemplate(TemplateConfig manageConfig, TemplateConfig serviceConfig) {
		this.manageConfig = manageConfig;
		this.serviceConfig = serviceConfig;
	}

	@Override
	public void before(TableInfoEntity tableInfoEntity, TemplateConfig config, Map<String, Object> data) {
		setImport(manageConfig.getFullPkg(), serviceConfig.getFullPkg());
	}

	@Override
	public void after(Map<String, Object> data) {
		data.put("manageConfig", manageConfig);
		data.put("serviceConfig", serviceConfig);
	}

	@Override
	public String templatePath() {
		return "templates/serviceImpl.ftl";
	}

	@Override
	public String fileNameFormat() {
		return "%sServiceImpl";
	}

	@Override
	public String pkg() {
		return "service.impl";
	}

}
