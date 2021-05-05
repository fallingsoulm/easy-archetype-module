package io.github.fallingsoulm.easy.archetype.job.service;

import io.github.fallingsoulm.easy.archetype.framework.page.PageInfo;
import io.github.fallingsoulm.easy.archetype.framework.page.PageRequestParams;
import io.github.fallingsoulm.easy.archetype.job.entity.JobVo;
import org.quartz.SchedulerException;

import java.util.List;

/**
 * 任务处理器
 *
 * @author luyanan
 * @since 2021/3/20
 **/
public interface JobService {

	/**
	 * 分页
	 *
	 * @param pageRequestParams 分页参数
	 * @return io.github.fallingsoulm.easy.archetype.framework.page.PageInfo<io.github.fallingsoulm.easy.archetype.job.entity.JobVo>
	 * @since 2021/3/20
	 */
	PageInfo<JobVo> page(PageRequestParams<JobVo> pageRequestParams);

	/**
	 * 任务列表
	 *
	 * @param jobVo
	 * @return java.util.List<io.github.fallingsoulm.easy.archetype.job.entity.JobVo>
	 * @since 2021/3/20
	 */
	List<JobVo> list(JobVo jobVo);


	/**
	 * 根据任务id查询
	 *
	 * @param jobId 任务id
	 * @return io.github.fallingsoulm.easy.archetype.job.entity.JobVo
	 * @since 2021/3/20
	 */
	JobVo findById(String jobId);


	/**
	 * 暂停任务
	 *
	 * @param jobVo 任务信息
	 * @return int
	 * @since 2021/3/20
	 */
	int pauseJob(JobVo jobVo) throws SchedulerException;


	/**
	 * 恢复任务
	 *
	 * @param jobVo 任务信息
	 * @return int
	 * @since 2021/3/20
	 */
	int resumeJob(JobVo jobVo) throws SchedulerException;


	/**
	 * 删除任务
	 *
	 * @param jobVo 任务信息
	 * @return int
	 * @since 2021/3/20
	 */
	int deleteJob(JobVo jobVo) throws SchedulerException;

	/**
	 * 批量删除任务
	 *
	 * @param jobs 任务id集合
	 * @return void
	 * @since 2021/3/20
	 */
	void deleteJobByIds(String[] jobs) throws SchedulerException;


	/**
	 * 修改任务状态
	 *
	 * @param jobVo 任务信息
	 * @return int
	 * @since 2021/3/20
	 */
	int changeStatus(JobVo jobVo) throws SchedulerException;


	/**
	 * 立即运行任务
	 *
	 * @param jobVo 任务信息
	 * @return int
	 * @since 2021/3/20
	 */
	void run(JobVo jobVo) throws SchedulerException;


	/**
	 * 插入任务
	 *
	 * @param jobVo 任务信息
	 * @return int
	 * @since 2021/3/20
	 */
	int insertJob(JobVo jobVo) throws SchedulerException;


	/**
	 * 修改任务
	 *
	 * @param jobVo 任务信息
	 * @return int
	 * @since 2021/3/20
	 */
	int updateJob(JobVo jobVo) throws SchedulerException;

	/**
	 * 校验cron表达式是否有效
	 *
	 * @param cronExpression cron表达式
	 * @return boolean
	 * @since 2021/3/20
	 */
	boolean checkCronExpressionIsValid(String cronExpression);

}
