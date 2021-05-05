package io.github.fallingsoulm.easy.archetype.job.service;

import io.github.fallingsoulm.easy.archetype.framework.page.PageInfo;
import io.github.fallingsoulm.easy.archetype.framework.page.PageRequestParams;
import io.github.fallingsoulm.easy.archetype.job.entity.JobLogVo;

/**
 * 任务日志存储策略
 *
 * @author luyanan
 * @since 2021/3/18
 */
public interface JobLogStorageStrategy {


	/**
	 * 分页查询
	 *
	 * @param pageRequestParams 分页参数
	 * @return io.github.fallingsoulm.easy.archetype.framework.page.PageInfo<io.github.fallingsoulm.easy.archetype.job.entity.JobLogVo>
	 * @since 2021/3/20
	 */
	PageInfo<JobLogVo> findByPage(PageRequestParams<JobLogVo> pageRequestParams);

	/**
	 * 根据日志id查询
	 *
	 * @param logId 日志id
	 * @return io.github.fallingsoulm.easy.archetype.job.entity.JobLogVo
	 * @since 2021/3/20
	 */
	JobLogVo findById(String logId);

	/**
	 * 任务存储
	 *
	 * @param jobLogVo
	 * @return void
	 * @since 2021/3/18
	 */
	void save(JobLogVo jobLogVo);


	/**
	 * 根据id集合删除
	 *
	 * @param ids id集合
	 * @return void
	 * @since 2021/3/20
	 */
	void deleteByIds(String[] ids);


	/**
	 * 根据id删除
	 *
	 * @param id
	 * @return void
	 * @since 2021/3/20
	 */
	void deleteById(String id);


	/**
	 * 清空日志
	 *
	 * @return void
	 * @since 2021/3/20
	 */
	void clearLog();
}
