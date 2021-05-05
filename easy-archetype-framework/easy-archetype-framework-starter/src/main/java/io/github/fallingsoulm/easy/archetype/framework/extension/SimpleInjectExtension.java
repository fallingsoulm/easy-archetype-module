package io.github.fallingsoulm.easy.archetype.framework.extension;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import io.github.fallingsoulm.easy.archetype.framework.extension.annotation.Inject;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

/**
 * 依赖注入实现
 *
 * @author luyanan
 * @since 2021/3/5
 **/
public class SimpleInjectExtension<T> implements InjectExtension<T> {
	@Override
	public T injectExtension(T instance, Set<String> extensionDir, ClassLoader classLoader) {
		// 获取实例的所有字段
		for (Field field : ReflectUtil.getFields(instance.getClass())) {
			Object extension = null;
			// 只给加了 @Inject的字段赋值
			if (field.isAnnotationPresent(Inject.class)) {
				Inject inject = field.getAnnotation(Inject.class);
				String name = inject.value();
				if (StrUtil.isBlank(name)) {
					name = ExtensionLoader.getExtensionLoader(field.getType()).getDefaultExtensionName();
				}
				extension = ExtensionLoader.getExtensionLoader(field.getType()).getExtension(name);
				ReflectUtil.setFieldValue(inject, field, extension);
			}
		}
		// 这里执行插件

		List<InjectExtensionPlugIn> allInjectExtensionPlugIn = ExtensionLoader.getExtensionLoader(InjectExtensionPlugIn.class).getExtension();
		if (CollectionUtil.isNotEmpty(allInjectExtensionPlugIn)) {
			for (InjectExtensionPlugIn plugIn : allInjectExtensionPlugIn) {
				plugIn.plugIn(instance, classLoader);
			}
		}
		return instance;
	}
}
