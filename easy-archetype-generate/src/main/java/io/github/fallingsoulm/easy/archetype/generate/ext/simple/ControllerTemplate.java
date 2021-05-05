package io.github.fallingsoulm.easy.archetype.generate.ext.simple;

import io.github.fallingsoulm.easy.archetype.generate.config.TemplateConfig;
import io.github.fallingsoulm.easy.archetype.generate.core.TableInfoEntity;
import io.github.fallingsoulm.easy.archetype.generate.template.AbstractTemplate;

import java.util.Map;

/**
 * controller层的实现
 *
 * @author luyanan
 * @since 2021/2/2
 **/
public class ControllerTemplate extends AbstractTemplate {

	/**
	 * service的模板配置文件
	 *
	 * @since 2021/2/2
	 */
	private TemplateConfig serviceConfig;

	/**
	 * swagger开关
	 *
	 * @since 2021/2/2
	 */
	private boolean swagger = false;

	public ControllerTemplate(TemplateConfig serviceConfig) {
		this.serviceConfig = serviceConfig;
	}

	public ControllerTemplate(TemplateConfig serviceConfig, boolean swagger) {
		this.serviceConfig = serviceConfig;
		this.swagger = swagger;
	}

	@Override
	public void after(Map<String, Object> data) {
		data.put("serviceConfig", serviceConfig);
		data.put("swagger", swagger);
	}

	@Override
	public void before(TableInfoEntity tableInfoEntity, TemplateConfig config, Map<String, Object> data) {
		setImport(serviceConfig.getFullPkg());
		if (swagger) {
			setImport("io.swagger.annotations.Api");
		}
		String requestMapping = tableInfoEntity.getTableName().replaceAll("_", "/").toLowerCase();
		data.put("requestMapping", requestMapping);
	}

	@Override
	public String templatePath() {
		return "templates/controller.ftl";
	}

	@Override
	public String fileNameFormat() {
		return "%sController";
	}

	@Override
	public String pkg() {
		return ".controller";
	}

}
