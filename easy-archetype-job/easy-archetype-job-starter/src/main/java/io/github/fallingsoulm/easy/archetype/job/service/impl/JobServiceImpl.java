package io.github.fallingsoulm.easy.archetype.job.service.impl;

import cn.hutool.core.util.IdUtil;
import io.github.fallingsoulm.easy.archetype.framework.page.PageInfo;
import io.github.fallingsoulm.easy.archetype.framework.page.PageRequestParams;
import io.github.fallingsoulm.easy.archetype.job.constant.ScheduleConstants;
import io.github.fallingsoulm.easy.archetype.job.dao.JobDao;
import io.github.fallingsoulm.easy.archetype.job.entity.JobVo;
import io.github.fallingsoulm.easy.archetype.job.service.JobService;
import io.github.fallingsoulm.easy.archetype.job.utils.CronUtils;
import io.github.fallingsoulm.easy.archetype.job.utils.ScheduleUtils;
import io.github.fallingsoulm.easy.archetype.security.core.LoginUserService;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

/**
 * 任务实现类
 *
 * @author luyanan
 * @since 2021/3/20
 **/
public class JobServiceImpl implements JobService {

	@Autowired
	private Scheduler scheduler;

	@Autowired
	private JobDao jobDao;

	@Autowired(required = false)
	private LoginUserService loginUserService;

	/**
	 * 项目启动的时候,初始化定时器,主要是防止手动修改数据库导致未同步到定时任务处理
	 *
	 * @return void
	 * @since 2021/3/20
	 */
	@PostConstruct
	public void init() throws SchedulerException {
		scheduler.clear();
		List<JobVo> jobVos = jobDao.selectList(new JobVo());
		for (JobVo jobVo : jobVos) {
			ScheduleUtils.createScheduleJob(scheduler, jobVo);
		}
	}

	@Override
	public PageInfo<JobVo> page(PageRequestParams<JobVo> pageRequestParams) {
		return jobDao.selectByPage(pageRequestParams);
	}

	@Override
	public List<JobVo> list(JobVo jobVo) {
		return jobDao.selectList(jobVo);
	}

	@Override
	public JobVo findById(String jobId) {
		return jobDao.selectById(jobId);
	}

	@Override
	public int pauseJob(JobVo jobVo) throws SchedulerException {
		String jobId = jobVo.getJobId();
		String jobGroup = jobVo.getJobGroup();
		jobVo.setStatus(ScheduleConstants.Status.PAUSE.getValue());
		int row = jobDao.update(jobVo);
		if (row > 0) {
			scheduler.pauseJob(ScheduleUtils.getJobKey(jobId, jobGroup));
		}
		return row;
	}

	@Override
	public int resumeJob(JobVo jobVo) throws SchedulerException {
		jobVo.setStatus(ScheduleConstants.Status.NORMAL.getValue());
		int row = jobDao.update(jobVo);
		if (row > 0) {
			scheduler.resumeJob(ScheduleUtils.getJobKey(jobVo.getJobId(), jobVo.getJobGroup()));
		}
		return row;
	}

	@Override
	public int deleteJob(JobVo jobVo) throws SchedulerException {
		int rows = jobDao.delete(jobVo.getJobId());
		if (rows > 0) {
			scheduler.deleteJob(ScheduleUtils.getJobKey(jobVo.getJobId(), jobVo.getJobGroup()));
		}
		return rows;
	}

	@Override
	public void deleteJobByIds(String[] jobs) throws SchedulerException {

		for (String jobId : jobs) {
			JobVo jobVo = jobDao.selectById(jobId);
			deleteJob(jobVo);
		}
	}

	@Override
	public int changeStatus(JobVo jobVo) throws SchedulerException {
		int rows = 0;
		String status = jobVo.getStatus();
		if (status.equals(ScheduleConstants.Status.NORMAL.getValue())) {
			rows = resumeJob(jobVo);
		} else if (status.equals(ScheduleConstants.Status.PAUSE.getValue())) {
			rows = pauseJob(jobVo);
		}
		return rows;
	}

	@Override
	public void run(JobVo jobVo) throws SchedulerException {
		JobVo vo = findById(jobVo.getJobId());
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put(ScheduleConstants.TASK_PROPERTIES, vo);
		scheduler.triggerJob(ScheduleUtils.getJobKey(jobVo.getJobId(), jobVo.getJobGroup()), jobDataMap);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int insertJob(JobVo jobVo) throws SchedulerException {
		if (null != loginUserService && null != loginUserService.getUserId()) {
			Long userId = loginUserService.getUserId();
			jobVo.setCreateBy(userId + "");
			jobVo.setUpdateBy(userId + "");
		}
		jobVo.setCreateTime(new Date());
		jobVo.setUpdateTime(new Date());
		jobVo.setJobId(IdUtil.fastSimpleUUID());
		jobVo.setStatus(ScheduleConstants.Status.PAUSE.getValue());
		int rows = jobDao.insert(jobVo);
		if (rows > 0) {
			ScheduleUtils.createScheduleJob(scheduler, jobVo);
		}
		return rows;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int updateJob(JobVo jobVo) throws SchedulerException {
		jobVo.setStatus(ScheduleConstants.Status.PAUSE.getValue());
		if (null != loginUserService && null != loginUserService.getUserId()) {
			Long userId = loginUserService.getUserId();
			jobVo.setUpdateBy(userId + "");
		}
		jobVo.setUpdateTime(new Date());
		int rows = jobDao.update(jobVo);
		if (rows > 0) {
			JobKey jobKey = ScheduleUtils.getJobKey(jobVo.getJobId(), jobVo.getJobGroup());
			if (scheduler.checkExists(jobKey)) {
				// 防止创建时存在数据问题,先移除,然后再执行创建
				scheduler.deleteJob(jobKey);
			}
			ScheduleUtils.createScheduleJob(scheduler, jobVo);

		}
		return rows;
	}

	@Override
	public boolean checkCronExpressionIsValid(String cronExpression) {
		return CronUtils.isValid(cronExpression);
	}
}
