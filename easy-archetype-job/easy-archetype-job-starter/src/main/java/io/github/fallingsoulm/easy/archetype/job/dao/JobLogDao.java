package io.github.fallingsoulm.easy.archetype.job.dao;

import io.github.fallingsoulm.easy.archetype.framework.jdbc.JdbcExecutor;
import io.github.fallingsoulm.easy.archetype.framework.page.PageInfo;
import io.github.fallingsoulm.easy.archetype.framework.page.PageRequestParams;
import io.github.fallingsoulm.easy.archetype.job.entity.JobLogVo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

/**
 * 任务日志
 *
 * @author luyanan
 * @since 2021/3/20
 **/
public class JobLogDao {
	@Autowired
	private JdbcExecutor jdbcExecutor;

	/**
	 * 分页查询
	 *
	 * @param pageRequestParams 分页参数
	 * @return io.github.fallingsoulm.easy.archetype.framework.page.PageInfo<io.github.fallingsoulm.easy.archetype.job.entity.JobLogVo>
	 * @since 2021/3/20
	 */
	public PageInfo<JobLogVo> selectByPage(PageRequestParams<JobLogVo> pageRequestParams) {
		return jdbcExecutor.selectByPage(pageRequestParams, JobLogVo.class);
	}

	/**
	 * 根据日志id查询
	 *
	 * @param logId 日志id
	 * @return io.github.fallingsoulm.easy.archetype.job.entity.JobLogVo
	 * @since 2021/3/20
	 */
	public JobLogVo selectById(String logId) {
		return jdbcExecutor.selectById(logId, JobLogVo.class);
	}

	/**
	 * 插入
	 *
	 * @param jobLogVo
	 * @return void
	 * @since 2021/3/20
	 */
	public void insert(JobLogVo jobLogVo) {
		jdbcExecutor.insert(jobLogVo);
	}

	/**
	 * 根据id集合删除
	 *
	 * @param ids id集合
	 * @return void
	 * @since 2021/3/20
	 */
	public void deleteByIds(String[] ids) {
		jdbcExecutor.deleteBatchIds(Arrays.asList(ids), JobLogVo.class);
	}

	/**
	 * 删除全部
	 *
	 * @return void
	 * @since 2021/3/20
	 */
	public void deleteAll() {
		jdbcExecutor.update("delete  from  ?", jdbcExecutor.getEntityWrappers(JobLogVo.class).getEntityInfo().getTableName());
	}

	/**
	 * 根据id删除
	 *
	 * @param id
	 * @return void
	 * @since 2021/3/20
	 */
	public void deleteById(String id) {
		jdbcExecutor.deleteById(id, JobLogVo.class);
	}
}
