package io.github.fallingsoulm.easy.archetype.framework.extension;

import io.github.fallingsoulm.easy.archetype.framework.extension.annotation.SPI;

/**
 * 依赖注入的插件
 *
 * @author luyanan
 * @since 2021/3/5
 **/
@SPI("")
public interface InjectExtensionPlugIn {

	/**
	 * 依赖注入的插件
	 *
	 * @param instance    实例
	 * @param classLoader 类加载器q
	 * @return void
	 * @since 2021/3/5
	 */
	void plugIn(Object instance, ClassLoader classLoader);
}
