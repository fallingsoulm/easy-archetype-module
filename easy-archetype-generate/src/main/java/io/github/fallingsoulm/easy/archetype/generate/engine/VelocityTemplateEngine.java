package io.github.fallingsoulm.easy.archetype.generate.engine;

import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.engine.velocity.VelocityEngine;

import java.util.Map;

/**
 * Velocity模板引擎
 *
 * @author luyanan
 * @since 2021/2/1
 **/
public class VelocityTemplateEngine implements ITemplateEngine {

	@Override
	public String process(Map<String, Object> data, String templatePath) {
		VelocityEngine velocityEngine = new VelocityEngine();
		String templateContext = FileUtil.readString(this.getClass().getClassLoader().getResource(templatePath),
				"utf-8");
		Template template = velocityEngine.getTemplate(templateContext);
		return template.render(data);
	}

}
