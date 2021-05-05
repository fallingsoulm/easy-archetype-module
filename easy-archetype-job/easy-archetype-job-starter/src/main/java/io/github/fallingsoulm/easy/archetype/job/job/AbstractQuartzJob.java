package io.github.fallingsoulm.easy.archetype.job.job;

import cn.hutool.core.exceptions.ExceptionUtil;
import io.github.fallingsoulm.easy.archetype.framework.spring.SpringContextHolder;
import io.github.fallingsoulm.easy.archetype.job.constant.ScheduleConstants;
import io.github.fallingsoulm.easy.archetype.job.entity.JobLogVo;
import io.github.fallingsoulm.easy.archetype.job.entity.JobVo;
import io.github.fallingsoulm.easy.archetype.job.service.JobLogStorageStrategy;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * 抽象quartz调用
 *
 * @author luyanan
 * @since 2021/3/14
 **/
@Slf4j
public abstract class AbstractQuartzJob implements BaseJob {

	private static ThreadLocal<Date> threadLocal = new ThreadLocal<>();

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		JobVo jobVo = new JobVo();
		BeanUtils.copyProperties( context.getMergedJobDataMap().get(ScheduleConstants.TASK_PROPERTIES),jobVo);
		try {
			before(context, jobVo);
			if (null != jobVo) {
				doExecute(context, jobVo);
			}
			after(context, jobVo, null);
		} catch (Exception e) {
			log.error("任务执行异常:{}", e);
			after(context, jobVo, e);
		}
	}

	@Override
	public void before(JobExecutionContext context, JobVo jobVo) {
		threadLocal.set(new Date());
	}

	@Override
	public void after(JobExecutionContext context, JobVo jobVo, Exception e) {
		Date startTime = threadLocal.get();
		threadLocal.remove();
		JobLogVo logVo = new JobLogVo();
		logVo.setJobName(jobVo.getJobName());
		logVo.setJobGroup(jobVo.getJobGroup());
		logVo.setJobId(jobVo.getJobId());
		logVo.setInvokeTarget(jobVo.getInvokeTarget());
		logVo.setStartTime(startTime);
		logVo.setEndTime(new Date());
		long runMs = logVo.getEndTime().getTime() - logVo.getStartTime().getTime();
		logVo.setJobMessage(logVo.getJobName() + " 总共耗时：" + runMs + "毫秒");
		if (null != e) {
			logVo.setStatus(ScheduleConstants.FAIL);
			logVo.setExceptionInfo(ExceptionUtil.getMessage(e));
		} else {
			logVo.setStatus(ScheduleConstants.SUCCESS);
		}
		// 任务处理策略
		JobLogStorageStrategy storageStrategy = SpringContextHolder.getBean(JobLogStorageStrategy.class);
		storageStrategy.save(logVo);
	}


	/**
	 * 执行方法,由子类实现
	 *
	 * @param jobExecutionContext 定时任务上下文
	 * @param jobVo               系统计划任务
	 * @return void
	 * @since 2021/3/18
	 */
	protected abstract void doExecute(JobExecutionContext jobExecutionContext, JobVo jobVo) throws Exception;
}
