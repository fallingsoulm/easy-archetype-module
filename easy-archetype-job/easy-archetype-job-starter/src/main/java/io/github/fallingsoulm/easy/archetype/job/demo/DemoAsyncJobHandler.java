package io.github.fallingsoulm.easy.archetype.job.demo;

import io.github.fallingsoulm.easy.archetype.job.annotation.Job;
import io.github.fallingsoulm.easy.archetype.job.invoke.bean.IJobBeanHandler;
import io.github.fallingsoulm.easy.archetype.job.invoke.bean.JobRespEntity;
import lombok.extern.slf4j.Slf4j;

/**
 * demo异步定时任务
 *
 * @author luyanan
 * @since 2021/6/25
 **/
@Slf4j
@Job(name = "异步demo定时任务执行", group = "demo")
public class DemoAsyncJobHandler implements IJobBeanHandler {
	@Override
	public JobRespEntity execute(String params) throws Exception {
		Thread.sleep(1000);
		log.info("demoJobHandler 定时任务开始执行,参数为:{}", params);

		return JobRespEntity.success();
	}

	@Override
	public boolean async() {
		return true;
	}
}
