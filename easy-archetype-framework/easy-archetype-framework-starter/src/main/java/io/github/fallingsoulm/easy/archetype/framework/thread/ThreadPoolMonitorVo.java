package io.github.fallingsoulm.easy.archetype.framework.thread;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 线程池监控类
 *
 * @author luyanan
 * @since 2021/1/22
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ThreadPoolMonitorVo {

	/**
	 * 线程类前缀
	 *
	 * @since 2021/1/22
	 */
	private String threadNamePrefix;

	/**
	 * 线程总数
	 *
	 * @since 2021/1/22
	 */
	private Long taskCount;

	/**
	 * 已经完成的线程数量
	 *
	 * @since 2021/1/22
	 */
	private Long completedTaskCount;

	/**
	 * 活跃线程数量
	 *
	 * @since 2021/1/22
	 */
	private Integer activeCount;

	/**
	 * 队列大小
	 *
	 * @since 2021/1/22
	 */
	private Integer queueSize;

	/**
	 * 拒绝策略
	 *
	 * @since 2021/1/22
	 */
	private String rejectedExecutionHandler;

	/**
	 * 核心线程池大小
	 *
	 * @since 2021/1/22
	 */
	private Integer corePoolSize;

	/**
	 * 最大线程池大小
	 *
	 * @since 2021/1/22
	 */

	private Integer maxPoolSize;

}
