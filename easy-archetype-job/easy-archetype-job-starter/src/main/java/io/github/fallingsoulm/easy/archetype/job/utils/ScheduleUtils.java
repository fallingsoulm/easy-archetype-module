package io.github.fallingsoulm.easy.archetype.job.utils;

import cn.hutool.core.util.StrUtil;
import io.github.fallingsoulm.easy.archetype.job.constant.ScheduleConstants;
import io.github.fallingsoulm.easy.archetype.job.entity.JobVo;
import io.github.fallingsoulm.easy.archetype.job.exception.JobException;
import org.quartz.*;

/**
 * 定时任务工具类
 *
 * @author luyanan
 * @since 2021/3/14
 **/
public class ScheduleUtils {

	/**
	 * 得到Quartz 任务类
	 *
	 * @param sysJob 执行计划
	 * @return java.lang.Class<? extends org.quartz.Job> 具体执行任务类
	 * @since 2021/3/18
	 */
	private static Class<? extends Job> getQuartzJobClass(JobVo sysJob) {
		boolean isConcurrent = "0".equals(sysJob.getConcurrent());
		return isConcurrent ? QuartzJobExecution.class : QuartzDisallowConcurrentExecution.class;
	}


	/**
	 * 构建任务触发对象
	 *
	 * @param jobId    任务id
	 * @param jobGroup 任务分组
	 * @return org.quartz.TriggerKey
	 * @since 2021/3/18
	 */
	public static TriggerKey getTriggerKey(String jobId, String jobGroup) {
		return TriggerKey.triggerKey(ScheduleConstants.TASK_CLASS_NAME + jobId, jobGroup);
	}

	/**
	 * 构建任务对象
	 *
	 * @param jobId    任务id
	 * @param jobGroup 任务分组
	 * @return org.quartz.JobKey
	 * @since 2021/3/18
	 */
	public static JobKey getJobKey(String jobId, String jobGroup) {
		return JobKey.jobKey(ScheduleConstants.TASK_CLASS_NAME + jobId, jobGroup);
	}


	/**
	 * 创建定时任务
	 *
	 * @param scheduler
	 * @param jobVo
	 * @return void
	 * @since 2021/3/18
	 */
	public static void createScheduleJob(Scheduler scheduler, JobVo jobVo) throws SchedulerException {
		if (StrUtil.isBlank(jobVo.getCronExpression())) {
			throw new JobException("cron表达式不能为空");
		}
		Class<? extends Job> jobClass = getQuartzJobClass(jobVo);
		// 构建job信息
		String jobId = jobVo.getJobId();
		String jobGroup = jobVo.getJobGroup();
		JobKey jobKey = getJobKey(jobId, jobGroup);
		JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobKey).build();


		// 表达式调度构建器
		CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(jobVo.getCronExpression());
		cronScheduleBuilder = handleCronScheduleMisfirePolicy(jobVo, cronScheduleBuilder);
		// 按新的cronExpression表达式构建一个新的trigger
		CronTrigger cronTrigger = TriggerBuilder
				.newTrigger()
				.withIdentity(getTriggerKey(jobId, jobGroup))
				.withSchedule(cronScheduleBuilder)
				.build();
		//放入参数,运行时的方法可以获取
		jobDetail.getJobDataMap().put(ScheduleConstants.TASK_PROPERTIES, jobVo);
		// 判断是否存在
		if (scheduler.checkExists(jobKey)) {
			// 防止创建时存在数据问题,先移除,然后再执行创建操作
			scheduler.deleteJob(jobKey);
		}
		scheduler.scheduleJob(jobDetail, cronTrigger);
		// 暂停任务
		if (jobVo.getStatus().equals(ScheduleConstants.Status.PAUSE.getValue())) {
			scheduler.pauseJob(jobKey);
		}
	}

	/**
	 * 设置定时任务策略
	 *
	 * @param jobVo
	 * @param cronScheduleBuilder
	 * @return org.quartz.CronScheduleBuilder
	 * @since 2021/3/18
	 */
	private static CronScheduleBuilder handleCronScheduleMisfirePolicy(JobVo jobVo, CronScheduleBuilder cronScheduleBuilder) {

		switch (jobVo.getMisfirePolicy()) {
			case ScheduleConstants.MISFIRE_DEFAULT:
				return cronScheduleBuilder;
			case ScheduleConstants.MISFIRE_IGNORE_MISFIRES:
				return cronScheduleBuilder.withMisfireHandlingInstructionIgnoreMisfires();
			case ScheduleConstants.MISFIRE_FIRE_AND_PROCEED:
				return cronScheduleBuilder.withMisfireHandlingInstructionDoNothing();
			default:
				throw new JobException("The task misfire policy '" + jobVo.getMisfirePolicy()
						+ "' cannot be used in cron schedule tasks");
		}

	}

}
