package io.github.fallingsoulm.easy.archetype.framework.extension;

import io.github.fallingsoulm.easy.archetype.framework.extension.annotation.Activate;

/**
 * 自定义激活扩展点
 *
 * @author luyanan
 * @since 2021/3/7
 **/
@Activate(group = "111")
public class ActivateFilter implements Filter {

	@Override
	public String filter(String content) {
		System.out.println("Activate:" + content);
		return "Activate:" + content;
	}
}
