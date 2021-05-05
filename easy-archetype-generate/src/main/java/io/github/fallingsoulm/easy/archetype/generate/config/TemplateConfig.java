package io.github.fallingsoulm.easy.archetype.generate.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 模板配置
 *
 * @author luyanan
 * @since 2021/1/31
 **/
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TemplateConfig {

	/**
	 * 需要导入的包(import)
	 *
	 * @since 2021/1/31
	 */
	private Set<String> imports;

	/**
	 * 需要往类上添加的注解
	 *
	 * @since 2021/1/31
	 */
	private Set<String> annotations;

	/**
	 * 类名
	 *
	 * @author luyanan
	 * @since 2021/1/31
	 */
	private String className;

	/**
	 * 类的路径(package),相对于模板路径之后的路径
	 *
	 * @author luyanan
	 * @since 2021/1/31
	 */
	private String classPkg;

	/**
	 * 全路径
	 *
	 * @since 2021/1/31
	 */
	private String fullPkg;

	/**
	 * id的字段名
	 *
	 * @since 2021/1/31
	 */
	private String idFieldName;

	/**
	 * id的字段的类型
	 *
	 * @since 2021/1/31
	 */
	private String idFieldType;

	/**
	 * 首字母小写的类名
	 *
	 * @since 2021/1/31
	 */
	private String uncapFirstClassName;

	/**
	 * 附加配置
	 *
	 * @since 2021/1/31
	 */
	private Map<String, Object> data = new HashMap<>();

}
