package io.github.fallingsoulm.easy.archetype.framework.spring.event.handler;

import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * 程序启动的处理器
 *
 * @author luyanan
 * @since 2021/6/27
 **/
public interface IStartHandler {

	/**
	 * 程序启动
	 *
	 * @param contextRefreshedEvent
	 * @return void
	 * @since 2021/6/27
	 */
	void run(ContextRefreshedEvent contextRefreshedEvent);

}
