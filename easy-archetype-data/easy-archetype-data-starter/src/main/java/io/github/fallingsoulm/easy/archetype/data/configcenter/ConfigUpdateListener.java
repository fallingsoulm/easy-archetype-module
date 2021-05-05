package io.github.fallingsoulm.easy.archetype.data.configcenter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ApplicationListener;

/**
 * 配置修改的监听
 *
 * @author luyanan
 * @since 2021/1/29
 **/
@Slf4j
@RequiredArgsConstructor
public class ConfigUpdateListener implements ApplicationListener<ConfigUpdateEvent> {

	private ConfigCenter configCenter;

	@Override

	public void onApplicationEvent(ConfigUpdateEvent event) {

		log.debug("配置刷新");
		// 加载配置
		configCenter.mergeProperties();
		// 配置刷新
		configCenter.refresh();
	}

}
