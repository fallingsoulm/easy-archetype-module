package io.github.fallingsoulm.easy.archetype.job.job;

import io.github.fallingsoulm.easy.archetype.job.entity.JobVo;
import org.quartz.*;

/**
 * Job 基类，主要是在 {@link org.quartz.Job} 上再封装一层，只让我们自己项目里的Job去实现
 *
 * @author luyanan
 * @since 2021/3/14
 **/
public interface BaseJob extends Job {


	/**
	 * 前置执行
	 *
	 * @param context 任务调度工作执行上下文对象
	 * @param jobVo   计划任务
	 * @return void
	 * @since 2021/3/14
	 */
	void before(JobExecutionContext context, JobVo jobVo);


	/**
	 * 后置执行
	 *
	 * @param context 工作执行上下文对象
	 * @param jobVo   计划任务
	 * @param e       异常
	 * @return void
	 * @since 2021/3/14
	 */
	void after(JobExecutionContext context, JobVo jobVo, Exception e);
}
