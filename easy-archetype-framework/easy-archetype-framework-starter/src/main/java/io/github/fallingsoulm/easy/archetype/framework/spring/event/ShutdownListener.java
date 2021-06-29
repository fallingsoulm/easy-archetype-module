package io.github.fallingsoulm.easy.archetype.framework.spring.event;

import io.github.fallingsoulm.easy.archetype.framework.spring.event.handler.IShutdownHandler;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.core.annotation.Order;

/**
 * 程序停止的监听
 *
 * @author luyanan
 * @since 2021/6/27
 **/

@Order(1)
public class ShutdownListener implements ApplicationListener<ContextClosedEvent> {

	@Autowired
	private ObjectProvider<IShutdownHandler> shutdownHandlers;

	@Override
	public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {

		shutdownHandlers.stream().forEach(shutdownHandler -> {
			try {
				shutdownHandler.run(contextClosedEvent);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}
