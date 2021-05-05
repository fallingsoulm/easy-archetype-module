package io.github.fallingsoulm.easy.archetype.framework.extension;

import org.junit.jupiter.api.Test;

import java.util.List;

public class ExtensionLoaderTest {

	/**
	 * 根据扩展点名称获取扩展点
	 *
	 * @return void
	 * @since 2021/3/5
	 */
	@Test
	public void getExtension() {
		System.out.println("xxs 扩展点");
		Filter filter = ExtensionLoader.getExtensionLoader(Filter.class).getExtension("xxs");
		filter.filter("我只是一段对话");
	}


	/**
	 * 获取默认的扩展点
	 *
	 * @return void
	 * @since 2021/3/5
	 */
	@Test
	public void getDefaultExtension() {
		System.out.println("默认的扩展点过滤");
		Filter filter = ExtensionLoader.getExtensionLoader(Filter.class).getDefaultExtension();
		filter.filter("默认的扩展点");
	}


	/**
	 * 根据名称查询是否包含此扩展点
	 *
	 * @return void
	 * @since 2021/3/5
	 */
	@Test
	public void hasExtension() {
		System.out.println("是否包含xxs的扩展点:" + ExtensionLoader.getExtensionLoader(Filter.class).hasExtension("xxs"));
		System.out.println("是否包含xxs1的扩展点:" + ExtensionLoader.getExtensionLoader(Filter.class).hasExtension("xxs1"));
	}


	/**
	 * 获取默认扩展点的名称
	 *
	 * @return void
	 * @since 2021/3/5
	 */
	@Test
	public void getDefaultExtensionName() {

		String defaultExtensionName = ExtensionLoader.getExtensionLoader(Filter.class).getDefaultExtensionName();
		System.out.println("默认的扩展点名称:" + defaultExtensionName);
	}

	/**
	 * 获取自适应扩展点
	 *
	 * @return void
	 * @since 2021/3/7
	 */
	@Test
	public void getAdaptiveExtension() {

		Filter adaptiveExtension = ExtensionLoader.getExtensionLoader(Filter.class).getAdaptiveExtension();
		adaptiveExtension.filter("自适应扩展点:");
	}


	/**
	 * 自动激活扩展点
	 *
	 * @return void
	 * @since 2021/3/7
	 */
	@Test
	public void getActivateExtension() {
		ExtensionLoader<Filter> extensionLoader = ExtensionLoader.getExtensionLoader(Filter.class);

		List<Filter> adaptiveExtension = extensionLoader
				.getAdaptiveExtension(Params.builder().addGroup("111"));
		System.out.println("自动激活扩展点:" + adaptiveExtension);
	}


	/**
	 * 获取所有扩展点的名称
	 *
	 * @return void
	 * @since 2021/3/7
	 */
	@Test
	public void getExtensionNames() {

		List<String> extensionNames = ExtensionLoader.getExtensionLoader(Filter.class).getExtensionNames();
		System.out.println("所有扩展点的名称:" + extensionNames);
	}


	@Test
	public void getExtensionList() {

		List<Filter> extension = ExtensionLoader.getExtensionLoader(Filter.class).getExtension();
		System.out.println("所有扩展点:" + extension);
	}
}