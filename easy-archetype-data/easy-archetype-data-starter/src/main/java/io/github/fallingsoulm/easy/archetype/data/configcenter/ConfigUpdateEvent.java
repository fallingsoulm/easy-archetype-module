package io.github.fallingsoulm.easy.archetype.data.configcenter;

import org.springframework.context.ApplicationEvent;

/**
 * 配置修改的事件
 *
 * @author luyanan
 * @since 2021/1/29
 **/
public class ConfigUpdateEvent extends ApplicationEvent {

	/**
	 * Create a new {@code ApplicationEvent}.
	 * @param source the object on which the event initially occurred or with which the
	 * event is associated (never {@code null})
	 */
	public ConfigUpdateEvent(Object source) {
		super(source);
	}

}
