package io.github.fallingsoulm.easy.archetype.job.invoke;

import io.github.fallingsoulm.easy.archetype.framework.spring.SpringContextHolder;
import io.github.fallingsoulm.easy.archetype.job.entity.JobVo;
import lombok.Data;

import java.util.Date;
import java.util.Map;

/**
 * 定时任务执行的抽象类
 *
 * @author luyanan
 * @since 2021/8/1
 **/
public abstract class AbstractJobInvokeStrategy implements JobInvokeStrategy {


	@Override
	public void invoke(JobVo jobVo) {
		long startTime = System.currentTimeMillis();


		// 发布任务执行的事件
		SpringContextHolder.publishEvent(new JobMonitorEvent(JobMonitorVo.builder().executeStartTime(startTime)
				.jobVo(jobVo).status(JobMonitorVo.Status.RUNNING.getStatus())
				.build()));
		Integer status = null;
		String errorMsg = null;
		try {
			doInvoke(jobVo);
			status = JobMonitorVo.Status.SUCCESS.getStatus();
		} catch (Exception e) {
			status = JobMonitorVo.Status.ERROR.getStatus();
			errorMsg = e.getLocalizedMessage();
			e.printStackTrace();
		} finally {
			long endTime = System.currentTimeMillis();
			SpringContextHolder.publishEvent(new JobMonitorEvent(JobMonitorVo.builder().executeStartTime(startTime)
					.executeEndTime(endTime)
					.errorMsg(errorMsg)
					.jobVo(jobVo).status(status)
					.build()));
		}


	}

	/**
	 * 执行定时任务
	 *
	 * @param jobVo
	 * @return void
	 * @throws Exception 异常
	 * @since 2021/8/1
	 */
	protected abstract void doInvoke(JobVo jobVo) throws Exception;


}
