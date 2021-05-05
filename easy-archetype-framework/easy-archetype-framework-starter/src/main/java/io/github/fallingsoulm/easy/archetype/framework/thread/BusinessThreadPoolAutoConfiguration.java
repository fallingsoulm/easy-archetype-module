package io.github.fallingsoulm.easy.archetype.framework.thread;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import io.github.fallingsoulm.easy.archetype.framework.config.EasyArchetypeFrameworkProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Objects;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 异步线程池配置 异步线程池配置 AsyncConfigurer在applicationContext早期初始化，如果需要依赖于其它的bean，尽可能的将它们声明为lazy
 *
 * @author luyanan
 * @since 2021/1/22
 **/
@Configuration
@ConditionalOnProperty(prefix = EasyArchetypeFrameworkProperties.PREFIX, name = "thread.enable", havingValue = "true",
		matchIfMissing = true)
@Slf4j
@EnableAsync
@EnableConfigurationProperties(BusinessThreadPoolProperties.class)
public class BusinessThreadPoolAutoConfiguration implements AsyncConfigurer {

	/**
	 * 定义线程池 使用{@link java.util.concurrent.LinkedBlockingQueue}(FIFO）队列，是一个用于并发环境下的阻塞队列集合类
	 * ThreadPoolTaskExecutor不是完全被IOC容器管理的bean,可以在方法上加上@Bean注解交给容器管理,这样可以将taskExecutor.initialize()方法调用去掉，容器会自动调用
	 *
	 * @return
	 */
	@Primary
	@Bean(BusinessThreadPoolTaskExecutor.BEAN_NAME)
	public BusinessThreadPoolTaskExecutor businessThreadPoolTaskExecutor(
			ObjectProvider<BusinessThreadInterceptor> businessThreadInterceptors,
			BusinessThreadPoolProperties asyncThreadPoolProperties) {
		// Java虚拟机可用的处理器数 *4 (io密集型,非CPU密集性)
		int processors = Runtime.getRuntime().availableProcessors() * 4;
		// 定义线程池
		BusinessThreadPoolTaskExecutor taskExecutor = new BusinessThreadPoolTaskExecutor(businessThreadInterceptors);
		// 核心线程数
		taskExecutor.setCorePoolSize(Objects.nonNull(asyncThreadPoolProperties.getCorePoolSize())
				? asyncThreadPoolProperties.getCorePoolSize() : processors);
		// 线程池最大线程数,默认：40000
		taskExecutor.setMaxPoolSize(Objects.nonNull(asyncThreadPoolProperties.getMaxPoolSize())
				? asyncThreadPoolProperties.getMaxPoolSize() : 4000);
		// 线程队列最大线程数,默认：80000
		taskExecutor.setQueueCapacity(Objects.nonNull(asyncThreadPoolProperties.getMaxPoolSize())
				? asyncThreadPoolProperties.getMaxPoolSize() : 80000);
		// 线程名称前缀
		taskExecutor.setThreadNamePrefix(StrUtil.isNotBlank(asyncThreadPoolProperties.getThreadNamePrefix())
				? asyncThreadPoolProperties.getThreadNamePrefix() : "Business-ThreadPool-");
		// 线程池中线程最大空闲时间，默认：60，单位：秒
		taskExecutor.setKeepAliveSeconds(asyncThreadPoolProperties.getKeepAliveSeconds());
		// 核心线程是否允许超时，默认:false
		taskExecutor.setAllowCoreThreadTimeOut(asyncThreadPoolProperties.isAllowCoreThreadTimeOut());
		// IOC容器关闭时是否阻塞等待剩余的任务执行完成，默认:false（必须设置setAwaitTerminationSeconds）
		taskExecutor
				.setWaitForTasksToCompleteOnShutdown(asyncThreadPoolProperties.isWaitForTasksToCompleteOnShutdown());
		// 阻塞IOC容器关闭的时间，默认：10秒（必须设置setWaitForTasksToCompleteOnShutdown）
		taskExecutor.setAwaitTerminationSeconds(asyncThreadPoolProperties.getAwaitTerminationSeconds());
		/**
		 * 拒绝策略，默认是AbortPolicy AbortPolicy：丢弃任务并抛出RejectedExecutionException异常
		 * DiscardPolicy：丢弃任务但不抛出异常 DiscardOldestPolicy：丢弃最旧的处理程序，然后重试，如果执行器关闭，这时丢弃任务
		 * CallerRunsPolicy：执行器执行任务失败，则在策略回调方法中执行任务，如果执行器关闭，这时丢弃任务
		 */
		taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
		// 初始化
		taskExecutor.initialize();

		return taskExecutor;
	}

	/**
	 * 异步方法执行的过程中抛出的异常捕获
	 *
	 * @return
	 */
	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return (throwable, method, objects) -> {
			String msg = StrUtil.EMPTY;

			if (ArrayUtil.isNotEmpty(objects) && objects.length > 0) {
				msg = String.join(msg, "参数是：");
				for (int i = 0; i < objects.length; i++) {
					msg = String.join(msg, objects[i].toString(), ":");
				}
			}
			if (Objects.nonNull(throwable)) {
				msg = String.join(msg, ExceptionUtil.getMessage(throwable));
			}
			log.error(msg, method.getDeclaringClass());
		};
	}

}
