package io.github.fallingsoulm.easy.archetype.generate.ext.simple;

import io.github.fallingsoulm.easy.archetype.generate.template.AbstractTemplate;

/**
 * manage层的模板
 *
 * @author luyanan
 * @since 2021/2/2
 **/
public class ManageTemplate extends AbstractTemplate {

	@Override
	public String templatePath() {
		return "templates/manage.ftl";
	}

	@Override
	public String fileNameFormat() {
		return "I%sManage";
	}

	@Override
	public String pkg() {
		return "manage";
	}

}
