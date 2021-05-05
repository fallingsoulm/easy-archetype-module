package io.github.fallingsoulm.easy.archetype.data.logger;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 日志自动化配置
 *
 * @author luyanan
 * @since 2021/1/22
 **/
@ConditionalOnProperty(prefix = LoggerProperties.PREFIX, name = "enable", havingValue = "true", matchIfMissing = true)
@EnableAsync
@RequiredArgsConstructor
@ConditionalOnWebApplication
@EnableConfigurationProperties(LoggerProperties.class)
@Configuration(proxyBeanMethods = false)
public class LoggerAutoConfiguration {

	@Bean
	public LoggerHandler loggerHandler() {
		return new WebLoggerHandlerPrint();
	}

	@Bean
	public LoggerListener loggerListener(ObjectProvider<LoggerHandler> loggerHandlers) {
		return new LoggerListener(loggerHandlers);
	}

	@Bean
	public LoggerAspect loggerAspect() {
		return new LoggerAspect();
	}

	@Bean
	public ApplicationLoggerInitializer applicationLoggerInitializer() {
		return new ApplicationLoggerInitializer();
	}

}
