package io.github.fallingsoulm.easy.archetype.framework.spring.event;

import io.github.fallingsoulm.easy.archetype.framework.spring.event.handler.IStartHandler;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * 程序启动监听
 *
 * @author luyanan
 * @since 2021/6/27
 **/
public class StartListener implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private ObjectProvider<IStartHandler> startHandlers;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {


		startHandlers.stream().forEach(startHandler -> {
			try {
				startHandler.run(contextRefreshedEvent);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}
