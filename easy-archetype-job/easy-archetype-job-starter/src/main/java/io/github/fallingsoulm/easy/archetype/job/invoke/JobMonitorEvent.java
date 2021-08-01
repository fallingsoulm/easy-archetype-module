package io.github.fallingsoulm.easy.archetype.job.invoke;

import org.springframework.context.ApplicationEvent;

/**
 * 任务监控事件
 *
 * @author luyanan
 * @since 2021/8/1
 **/
public class JobMonitorEvent extends ApplicationEvent {


	public JobMonitorEvent(Object source) {
		super(source);
	}
}
