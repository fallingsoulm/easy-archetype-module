package io.github.fallingsoulm.easy.archetype.job.invoke;

import io.github.fallingsoulm.easy.archetype.job.entity.JobVo;

/**
 * 任务执行的策略
 *
 * @author luyanan
 * @since 2021/3/18
 **/
public interface JobInvokeStrategy {

	/**
	 * 类型
	 *
	 * @return java.lang.String
	 * @since 2021/3/18
	 */
	String type();

	/**
	 * 任务执行
	 *
	 * @param jobVo 任务
	 * @return void
	 * @since 2021/3/18
	 */
	void invoke(JobVo jobVo);
}
