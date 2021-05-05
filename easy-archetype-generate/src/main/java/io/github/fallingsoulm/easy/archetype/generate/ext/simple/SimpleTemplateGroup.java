package io.github.fallingsoulm.easy.archetype.generate.ext.simple;

import io.github.fallingsoulm.easy.archetype.generate.config.TemplateConfig;
import io.github.fallingsoulm.easy.archetype.generate.template.AbstractTemplateGroup;
import io.github.fallingsoulm.easy.archetype.generate.template.ITemplate;
import io.github.fallingsoulm.easy.archetype.generate.template.TemplateBuilder;

import java.util.List;

/**
 * 默认模板分组
 *
 * @author luyanan
 * @since 2021/2/2
 **/
public class SimpleTemplateGroup extends AbstractTemplateGroup {

	/**
	 * swagger开关
	 *
	 * @since 2021/2/2
	 */
	private boolean swagger = false;

	/**
	 * mybatis plus 开关
	 *
	 * @since 2021/2/2
	 */
	private boolean mybatisPlus = false;

	public SimpleTemplateGroup(boolean swagger, boolean mybatisPlus) {
		this.swagger = swagger;
		this.mybatisPlus = mybatisPlus;
	}

	@Override
	public String name() {
		return this.getClass().getName();
	}

	@Override
	public void registryTemplateList(List<ITemplate> templates, TemplateBuilder templateBuilder) {
		ITemplate entityTemplate = templateBuilder.build(new EntityTemplate(swagger, mybatisPlus));
		TemplateConfig entityConfig = entityTemplate.config();
		templates.add(entityTemplate);
		ITemplate mapperTemplate = templateBuilder.build(new MapperTemplate(entityConfig, mybatisPlus));
		templates.add(mapperTemplate);
		TemplateConfig mapperConfig = mapperTemplate.config();
		// mapper xml
		ITemplate mapperXmlTemplate = templateBuilder.build(new MapperXmlTemplate(entityConfig, mapperConfig));
		templates.add(mapperXmlTemplate);
		// mansage层 模板
		ITemplate manageTemplate = templateBuilder.build(new ManageTemplate());
		TemplateConfig manageConfig = manageTemplate.config();
		templates.add(manageTemplate);
		ITemplate manageImplTemplate = templateBuilder
				.build(new ManageImplTemplate(mybatisPlus, entityConfig, mapperConfig, manageConfig));
		templates.add(manageImplTemplate);
		// service层
		ITemplate serviceTemplate = templateBuilder.build(new ServiceTemplate());
		TemplateConfig serviceConfig = serviceTemplate.config();
		templates.add(serviceTemplate);
		ITemplate serviceImplTemplate = templateBuilder.build(new ServiceImplTemplate(manageConfig, serviceConfig));
		templates.add(serviceImplTemplate);
		// controller层
		ITemplate controllerTemplate = templateBuilder.build(new ControllerTemplate(serviceConfig, swagger));
		templates.add(controllerTemplate);

	}

}
