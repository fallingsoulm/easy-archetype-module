package io.github.fallingsoulm.easy.archetype.job;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import io.github.fallingsoulm.easy.archetype.job.annotation.Job;
import io.github.fallingsoulm.easy.archetype.job.constant.ScheduleConstants;
import io.github.fallingsoulm.easy.archetype.job.entity.JobVo;
import io.github.fallingsoulm.easy.archetype.job.invoke.bean.IJobBeanHandler;
import io.github.fallingsoulm.easy.archetype.job.service.JobService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author luyanan
 * @since 2021/6/26
 **/

public class JobInitializingBean implements InitializingBean {
	@Autowired
	ObjectProvider<IJobBeanHandler> jobHandlers;

	@Value("${spring.application.name}")
	private String applicationName;
	@Autowired
	private JobService jobService;

	@Override
	public void afterPropertiesSet() throws Exception {


		if (CollectionUtil.isEmpty(jobHandlers)) {
			return;
		}
		List<JobVo> jobVoList = jobHandlers.stream().map(jobHandler -> {
			Job annotation = jobHandler.getClass().getAnnotation(Job.class);
			return JobVo.builder().jobName(annotation.name())
					.concurrent(annotation.concurrent() ? "0" : "1")
					.jobGroup(annotation.group())
					.cronExpression(annotation.cron())
					.invokeTarget(jobHandler.getClass().getName())
					.misfirePolicy(ScheduleConstants.MISFIRE_DEFAULT)
					.invokeType("bean")
					.createBy(applicationName)
					.updateBy(applicationName)
					.build();
		}).collect(Collectors.toList());
		jobService.saveOrUpdateBeanJob(jobVoList);

	}

	/**
	 * 获取任务名称
	 *
	 * @param clazz
	 * @return java.lang.String
	 * @since 2021/6/27
	 */
	private String getInvokeTarget(Class clazz, String params) {
//        demoTask.ryParams('ry')
		String simpleName = clazz.getSimpleName();
		return StrUtil.lowerFirst(simpleName) + "('" + params + "')";
	}
}
