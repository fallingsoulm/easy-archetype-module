package io.github.fallingsoulm.easy.archetype.data.configcenter;

import java.util.Map;

/**
 * 配置来源
 *
 * @author luyanan
 * @since 2021/1/29
 **/
public interface PropertySource {

	/**
	 * 配置
	 * @return java.util.Map<java.lang.String, java.lang.Object>
	 * @since 2021/1/29
	 */
	Map<String, Object> properties();

}
