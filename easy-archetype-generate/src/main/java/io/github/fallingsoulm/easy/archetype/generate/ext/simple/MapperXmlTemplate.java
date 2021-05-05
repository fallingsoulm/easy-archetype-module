package io.github.fallingsoulm.easy.archetype.generate.ext.simple;

import io.github.fallingsoulm.easy.archetype.generate.config.TemplateConfig;
import io.github.fallingsoulm.easy.archetype.generate.template.AbstractTemplate;

import java.util.Map;

/**
 * mapper的XML文件模板生成
 *
 * @author luyanan
 * @since 2021/2/2
 **/
public class MapperXmlTemplate extends AbstractTemplate {

	/**
	 * 实体类模板配置文件
	 *
	 * @since 2021/2/2
	 */
	private TemplateConfig entityConfig;

	/**
	 * mapper类模板配置文件
	 *
	 * @since 2021/2/2
	 */
	private TemplateConfig mapperConfig;

	public MapperXmlTemplate(TemplateConfig entityConfig, TemplateConfig mapperConfig) {
		this.entityConfig = entityConfig;
		this.mapperConfig = mapperConfig;
	}

	@Override
	public void after(Map<String, Object> data) {
		data.put("entityConfig", entityConfig);
		data.put("mapperConfig", mapperConfig);
	}

	@Override
	public String templatePath() {
		return "templates/mapperXml.ftl";
	}

	@Override
	public String fileNameFormat() {
		return "%sxml";
	}

	@Override
	public String pkg() {
		return "mapper/xml";
	}

	@Override
	public String fileNameSuffix() {
		return ".xml";
	}

}
