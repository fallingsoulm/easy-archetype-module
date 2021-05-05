package io.github.fallingsoulm.easy.archetype.generate.config;

/**
 * 配置处理
 *
 * @author luyanan
 * @since 2021/1/31
 **/
@FunctionalInterface
public interface ConfigHandler {

	/**
	 * 配置处理
	 * @param globalConfig 全局配置
	 * @return void
	 * @since 2021/1/31
	 */
	void handler(GlobalConfig globalConfig);

}
