package io.github.fallingsoulm.easy.archetype.generate.ext.simple;

import cn.hutool.core.util.StrUtil;
import io.github.fallingsoulm.easy.archetype.generate.config.TemplateConfig;
import io.github.fallingsoulm.easy.archetype.generate.core.TableFieldEntity;
import io.github.fallingsoulm.easy.archetype.generate.core.TableInfoEntity;
import io.github.fallingsoulm.easy.archetype.generate.template.AbstractTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 实体类模板
 *
 * @author luyanan
 * @since 2021/2/2
 **/
public class EntityTemplate extends AbstractTemplate {

	/**
	 * swagger开关
	 *
	 * @since 2021/2/2
	 */
	private boolean swagger = false;

	/**
	 * mybatisplus 开关
	 *
	 * @since 2021/2/2
	 */
	private boolean mybatisPlus = false;

	/**
	 * swagger的包导入
	 *
	 * @since 2021/2/2
	 */
	private String[] swaggerImport = { "io.swagger.annotations.ApiModel", " io.swagger.annotations.ApiModelProperty" };

	/**
	 * swagger的注解
	 *
	 * @since 2021/2/2
	 */
	private String[] swaggerAnnotation = {};

	/********************** Mybatis PLus ******************************************/

	/**
	 * mybatis plus的import
	 *
	 * @since 2021/2/2
	 */
	private String[] mybatisPlusImport = { "com.baomidou.mybatisplus.annotation.*" };

	/**
	 * mybatis plus 的注解
	 *
	 * @since 2021/2/2
	 */
	private String[] mybatisPlusAnnotation = {};

	public EntityTemplate(boolean swagger, boolean mybatisPlus) {
		this.swagger = swagger;
		this.mybatisPlus = mybatisPlus;
	}

	public EntityTemplate() {
	}

	@Override
	public void before(TableInfoEntity tableInfoEntity, TemplateConfig config, Map<String, Object> data) {
		data.put("swagger", this.swagger);
		data.put("mybatisPlus", mybatisPlus);
		if (swagger) {
			setImport(swaggerImport);
			setAnnotations(swaggerAnnotation);
		}
		if (mybatisPlus) {
			setImport(mybatisPlusImport);
			setAnnotations(mybatisPlusAnnotation);
		}
		List<String> tableFieldImports = tableInfoEntity.getTableFieldEntityList().stream()
				.map(TableFieldEntity::getColumnImport).filter(a -> StrUtil.isNotBlank(a)).collect(Collectors.toList());

		setImport(tableFieldImports);
	}

	@Override
	public String templatePath() {
		return "templates/entity.ftl";
	}

	@Override
	public String fileNameFormat() {
		return "%sDo";
	}

	@Override
	public String pkg() {
		return "entity";
	}

}
