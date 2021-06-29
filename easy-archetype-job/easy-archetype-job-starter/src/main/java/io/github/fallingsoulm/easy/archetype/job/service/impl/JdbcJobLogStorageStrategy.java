package io.github.fallingsoulm.easy.archetype.job.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import io.github.fallingsoulm.easy.archetype.framework.page.OrderItem;
import io.github.fallingsoulm.easy.archetype.framework.page.PageInfo;
import io.github.fallingsoulm.easy.archetype.framework.page.PageRequestParams;
import io.github.fallingsoulm.easy.archetype.job.dao.JobLogDao;
import io.github.fallingsoulm.easy.archetype.job.entity.JobLogVo;
import io.github.fallingsoulm.easy.archetype.job.service.JobLogStorageStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

/**
 * 使用JDBC存储日志
 *
 * @author luyanan
 * @since 2021/3/20
 **/
public class JdbcJobLogStorageStrategy implements JobLogStorageStrategy {

	@Autowired
	private JobLogDao jobLogDao;


	@Override
	public PageInfo<JobLogVo> findByPage(PageRequestParams<JobLogVo> pageRequestParams) {
		if (CollectionUtil.isEmpty(pageRequestParams.getOrders())) {
			// 默认添加根据创建时间排序
			pageRequestParams.setOrders(Arrays.asList(OrderItem.desc("start_time")));
		}
		return jobLogDao.selectByPage(pageRequestParams);
	}

	@Override
	public JobLogVo findById(String logId) {
		return jobLogDao.selectById(logId);
	}

	@Override
	public void save(JobLogVo jobLogVo) {
		if (StrUtil.isBlank(jobLogVo.getId())) {
			jobLogVo.setId(IdUtil.simpleUUID());
		}
		jobLogDao.insert(jobLogVo);
	}

	@Override
	public void deleteByIds(String[] ids) {
		jobLogDao.deleteByIds(ids);
	}

	@Override
	public void deleteById(String id) {

		jobLogDao.deleteById(id);
	}

	@Override
	public void clearLog() {

		jobLogDao.deleteAll();
	}
}
