package io.github.fallingsoulm.easy.archetype.framework.thread;

import io.github.fallingsoulm.easy.archetype.framework.config.EasyArchetypeFrameworkProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 业务线程池配置文件
 *
 * @author luyanan
 * @since 2021/1/22
 **/
@Data
@ConfigurationProperties(prefix = "thread")
public class BusinessThreadPoolProperties {

	/**
	 * 是否开启
	 *
	 * @since 2021/1/22
	 */
	private Boolean enable;

	/**
	 * 核心线程数量,默认为java虚拟机可用线程数
	 *
	 * @since 2021/1/22
	 */
	private Integer corePoolSize;

	/**
	 * 线程池最大线程数,默认为40000
	 *
	 * @since 2021/1/22
	 */
	private Integer maxPoolSize;

	/**
	 * 最大等待线程数量,默认为:80000
	 *
	 * @since 2021/1/22
	 */
	private Integer queueCapacity;

	/**
	 * 自定义线程名前缀,默认为:Async-ThreadPool-
	 *
	 * @since 2021/1/22
	 */
	private String threadNamePrefix;

	/**
	 * 线程池中线程的最大空闲时间,默认为:60,单位为:s
	 *
	 * @since 2021/1/22
	 */
	private Integer keepAliveSeconds = 60;

	/**
	 * 核心线程是否允许超时,默认为false
	 *
	 * @since 2021/1/22
	 */
	private boolean allowCoreThreadTimeOut;

	/**
	 * IOC容器关闭的时候是否阻塞等待剩余的任务执行完成,默认为:fasle（必须设置setAwaitTerminationSeconds）
	 *
	 * @since 2021/1/22
	 */
	private boolean waitForTasksToCompleteOnShutdown;

	/**
	 * 阻塞IOC容器关闭的时间,默认为10s(必须设置setWaitForTasksToCompleteOnShutdown)
	 *
	 * @since 2021/1/22
	 */
	private int awaitTerminationSeconds = 10;

}
