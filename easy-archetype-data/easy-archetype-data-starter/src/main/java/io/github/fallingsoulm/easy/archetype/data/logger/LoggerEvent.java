package io.github.fallingsoulm.easy.archetype.data.logger;

import org.springframework.context.ApplicationEvent;

/**
 * 日志的事件
 *
 * @author luyanan
 * @since 2021/1/22
 **/
public class LoggerEvent extends ApplicationEvent {

	/**
	 * Create a new {@code ApplicationEvent}.
	 * @param source the object on which the event initially occurred or with which the
	 * event is associated (never {@code null})
	 */
	public LoggerEvent(Object source) {
		super(source);
	}

}
