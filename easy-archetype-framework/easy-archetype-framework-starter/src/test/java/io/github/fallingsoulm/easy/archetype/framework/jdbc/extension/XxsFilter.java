package io.github.fallingsoulm.easy.archetype.framework.extension;

/**
 * xxs过滤器
 *
 * @author luyanan
 * @since 2021/3/5
 **/
public class XxsFilter implements Filter {
	@Override
	public String filter(String content) {
		System.out.println("xxs 过滤:" + content);
		return "xxs:" + content;
	}
}
