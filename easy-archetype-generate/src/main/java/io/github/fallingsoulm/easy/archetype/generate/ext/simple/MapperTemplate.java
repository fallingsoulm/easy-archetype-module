package io.github.fallingsoulm.easy.archetype.generate.ext.simple;

import io.github.fallingsoulm.easy.archetype.generate.config.TemplateConfig;
import io.github.fallingsoulm.easy.archetype.generate.core.TableInfoEntity;
import io.github.fallingsoulm.easy.archetype.generate.template.AbstractTemplate;

import java.util.Map;

/**
 * Mapper模板
 *
 * @author luyanan
 * @since 2021/2/2
 **/
public class MapperTemplate extends AbstractTemplate {

	/**
	 * 实体类模板配置文件
	 *
	 * @since 2021/2/2
	 */
	private TemplateConfig entityConfig;

	/**
	 * mybatis plus开关
	 *
	 * @since 2021/2/2
	 */
	private boolean mybatisPlus = false;

	public MapperTemplate(TemplateConfig entityConfig, boolean mybatisPlus) {
		this.entityConfig = entityConfig;
		this.mybatisPlus = mybatisPlus;
	}

	public MapperTemplate(TemplateConfig entityConfig) {
		this.entityConfig = entityConfig;
	}

	@Override
	public void before(TableInfoEntity tableInfoEntity, TemplateConfig config, Map<String, Object> data) {
		data.put("mybatisPlus", mybatisPlus);
		data.put("entityConfig", this.entityConfig);
		if (mybatisPlus) {
			setImport("com.baomidou.mybatisplus.core.mapper.BaseMapper");
		}
		setImport(this.entityConfig.getFullPkg());
		setImport("org.apache.ibatis.annotations.Mapper");
		setAnnotations("@Mapper");
	}

	@Override
	public String templatePath() {
		return "templates/mapper.ftl";
	}

	@Override
	public String fileNameFormat() {
		return "%sMapper";
	}

	@Override
	public String pkg() {
		return "mapper";
	}

}
