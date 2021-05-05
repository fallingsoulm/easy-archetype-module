package io.github.fallingsoulm.easy.archetype.job.config;

import io.github.fallingsoulm.easy.archetype.framework.jdbc.JdbcExecutor;
import io.github.fallingsoulm.easy.archetype.job.controller.JobController;
import io.github.fallingsoulm.easy.archetype.job.controller.JobLogController;
import io.github.fallingsoulm.easy.archetype.job.dao.JobDao;
import io.github.fallingsoulm.easy.archetype.job.dao.JobLogDao;
import io.github.fallingsoulm.easy.archetype.job.invoke.JobInvokeFactory;
import io.github.fallingsoulm.easy.archetype.job.invoke.JobInvokeStrategy;
import io.github.fallingsoulm.easy.archetype.job.invoke.SpringBeanJobInvokeStrategy;
import io.github.fallingsoulm.easy.archetype.job.service.JobLogStorageStrategy;
import io.github.fallingsoulm.easy.archetype.job.service.JobService;
import io.github.fallingsoulm.easy.archetype.job.service.impl.JdbcJobLogStorageStrategy;
import io.github.fallingsoulm.easy.archetype.job.service.impl.JobServiceImpl;
import io.github.fallingsoulm.easy.archetype.job.task.DemoTask;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;

/**
 * 定时任务自动注入
 *
 * @author luyanan
 * @since 2021/3/20
 **/
@ConditionalOnProperty(prefix = JobProperties.PREFIX, name = "enable", havingValue = "true", matchIfMissing = false)
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(JobProperties.class)
@Import(ScheduleConfig.class)
public class ScheduleAutoConfiguration {

	@Bean
	public JdbcExecutor jdbcExecutor(DataSource dataSource) {
		JdbcExecutor jdbcExecutor = new JdbcExecutor();
		jdbcExecutor.setDataSource(dataSource);
		return jdbcExecutor;
	}


	@Bean
	public JobDao jobDao() {
		return new JobDao();
	}

	@Bean
	public JobLogDao jobLogDao() {
		return new JobLogDao();
	}


	@Bean
	public JobInvokeStrategy springBeanJobInvokeStrategy() {
		return new SpringBeanJobInvokeStrategy();
	}

	@Bean
	public JobInvokeFactory jobInvokeFactory() {
		return new JobInvokeFactory();
	}

	@Bean
	public JobLogStorageStrategy jobLogStorageStrategy() {
		return new JdbcJobLogStorageStrategy();
	}

	@Bean
	public JobService jobService() {
		return new JobServiceImpl();
	}


	@Bean
	public JobController jobController() {
		return new JobController();
	}

	@Bean
	public JobLogController jobLogController() {
		return new JobLogController();
	}


	/**
	 * 测试的任务
	 *
	 * @return io.github.fallingsoulm.easy.archetype.job.task.DemoTask
	 * @since 2021/4/6
	 */
	@Bean("demoTask")
	public DemoTask demoTask() {
		return new DemoTask();
	}
}
