package io.github.fallingsoulm.easy.archetype.generate.engine;

import java.util.Map;

/**
 * 模板引擎
 *
 * @author luyanan
 * @since 2021/2/1
 **/
public interface ITemplateEngine {

	/**
	 * 生成模板文件
	 * @param data 输出到模板的数据
	 * @param templatePath 模板路径
	 * @return java.lang.String
	 * @since 2021/2/1
	 */
	String process(Map<String, Object> data, String templatePath);

}
