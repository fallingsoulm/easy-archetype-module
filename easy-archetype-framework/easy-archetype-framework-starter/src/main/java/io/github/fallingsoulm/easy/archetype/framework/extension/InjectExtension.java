package io.github.fallingsoulm.easy.archetype.framework.extension;

import java.util.Set;

/**
 * 依赖注入
 *
 * @author luyanan
 * @since 2021/3/4
 **/
public interface InjectExtension<T> {


	/**
	 * 实现扩展类的依赖注入
	 *
	 * @param instance     实例
	 * @param extensionDir 扩展类路径
	 * @param classLoader  类加载器
	 * @return T
	 * @since 2021/3/4
	 */
	T injectExtension(T instance, Set<String> extensionDir, ClassLoader classLoader);
}
