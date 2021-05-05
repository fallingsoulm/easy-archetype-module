package io.github.fallingsoulm.easy.archetype.framework.extension;

import io.github.fallingsoulm.easy.archetype.framework.extension.annotation.Adaptive;

/**
 * 自适应扩展点
 *
 * @author luyanan
 * @since 2021/3/7
 **/
@Adaptive
public class AdaptiveFilter implements Filter {
	@Override
	public String filter(String content) {
		System.out.println("自适应扩展点");
		return ExtensionLoader.getExtensionLoader(Filter.class).getDefaultExtension().filter("自适应扩展点处理:" + content);
	}
}
