package io.github.fallingsoulm.easy.archetype.data.logger;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

/**
 * 日志监听者
 *
 * @author luyanan
 * @since 2021/1/22
 **/
@Slf4j
public class LoggerListener {

	private ObjectProvider<LoggerHandler> loggerHandlers;

	public LoggerListener(ObjectProvider<LoggerHandler> loggerHandlers) {
		this.loggerHandlers = loggerHandlers;
	}

	@Async
	@Order
	@EventListener
	public void listener(LoggerEvent loggerEvent) {
		LoggerVo loggerVo = (LoggerVo) loggerEvent.getSource();
		loggerHandlers.orderedStream().forEach(loggerHandler -> {
			loggerHandler.handler(loggerVo);
		});
	}

}
