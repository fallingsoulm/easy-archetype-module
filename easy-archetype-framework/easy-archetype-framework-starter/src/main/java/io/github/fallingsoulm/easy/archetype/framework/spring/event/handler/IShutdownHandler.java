package io.github.fallingsoulm.easy.archetype.framework.spring.event.handler;

import org.springframework.context.event.ContextClosedEvent;

/**
 * 程序停止的处理器
 *
 * @author luyanan
 * @since 2021/6/27
 **/
public interface IShutdownHandler {

	/**
	 * 程序停止
	 *
	 * @param contextClosedEvent
	 * @return void
	 * @since 2021/6/27
	 */
	void run(ContextClosedEvent contextClosedEvent);

}
