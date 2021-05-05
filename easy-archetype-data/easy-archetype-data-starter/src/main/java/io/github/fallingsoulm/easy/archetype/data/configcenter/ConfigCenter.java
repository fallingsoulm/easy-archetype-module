package io.github.fallingsoulm.easy.archetype.data.configcenter;

import cn.hutool.core.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.Map;

/**
 * 配置中心
 *
 * @author luyanan
 * @since 2021/1/29
 **/
@Slf4j
public class ConfigCenter {

	private PropertySource propertySource;

	private ContextRefresher contextRefresher;

	private final static String PROPERTY_SOURCE_NAME = "customizePropertySource";

	private ConfigurableEnvironment configurableEnvironment;

	public ConfigCenter(PropertySource propertySource, ContextRefresher contextRefresher,
			ConfigurableEnvironment configurableEnvironment) {
		this.propertySource = propertySource;
		this.contextRefresher = contextRefresher;
		this.configurableEnvironment = configurableEnvironment;
	}

	/**
	 * 合并配置
	 * @return void
	 * @since 2021/1/29
	 */
	public void mergeProperties() {
		Map<String, Object> properties = propertySource.properties();
		if (CollectionUtil.isEmpty(properties)) {
			return;
		}
		log.debug("加载到的{}配置:{}", PROPERTY_SOURCE_NAME, properties);
		MapPropertySource mapPropertySource = new MapPropertySource(PROPERTY_SOURCE_NAME, properties);
		configurableEnvironment.getPropertySources().addFirst(mapPropertySource);
	}

	/**
	 * 配置刷新
	 * @return void
	 * @since 2021/1/29
	 */
	public void refresh() {
		contextRefresher.refresh();
	}

}
