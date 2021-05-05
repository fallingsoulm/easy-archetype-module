package io.github.fallingsoulm.easy.archetype.generate.engine;

import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.engine.freemarker.FreemarkerEngine;

import java.util.Map;

/**
 * Freemarker模板引擎
 *
 * @author luyanan
 * @since 2021/2/1
 **/
public class FreemarkerTemplateEngine implements ITemplateEngine {

	@Override
	public String process(Map<String, Object> data, String templatePath) {
		String render = "";

		try {
			FreemarkerEngine engine = new FreemarkerEngine();
			String templateContext = FileUtil.readString(this.getClass().getClassLoader().getResource(templatePath),
					"utf-8");
			Template template = engine.getTemplate(templateContext);
			render = template.render(data);
		}
		catch (Exception e) {
			System.out.println("templatePath:" + templatePath + "出现异常了");
			e.printStackTrace();
		}
		return render;
	}

}
