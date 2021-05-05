package io.github.fallingsoulm.easy.archetype.data.manage.impl;

import cn.hutool.core.collection.CollectionUtil;
import io.github.fallingsoulm.easy.archetype.framework.page.PageInfo;
import io.github.fallingsoulm.easy.archetype.framework.page.PageRequestParams;
import io.github.fallingsoulm.easy.archetype.data.manage.AbstractManageImpl;
import io.github.fallingsoulm.easy.archetype.data.mybatisplus.BaseMapperPlus;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 带缓存的Manage实现
 *
 * @author luyanan
 * @since 2021/1/20
 **/
@CacheConfig
public class CacheManageImpl<M extends BaseMapperPlus<T>, T> extends AbstractManageImpl<M, T> {

	@Cacheable(sync = true)
	@Override
	public T findById(Serializable id) {
		return this.getById(id);
	}

	@Cacheable(sync = true)
	@Override
	public PageInfo<T> listByPage(PageRequestParams<T> pageRequestParams) {
		return toPageInfo(pageRequestParams, queryWrapper(pageRequestParams.getParams()));
	}

	@Cacheable(sync = true)
	@Override
	public List<T> list(T entity) {
		return this.list(queryWrapper(entity));
	}

	@Cacheable(sync = true)
	@Override
	public List<T> findByIds(Collection<? extends Serializable> ids) {
		if (CollectionUtil.isEmpty(ids)) {
			return new ArrayList<>();
		}
		return this.listByIds(ids);
	}

	@Cacheable(sync = true)
	@Override
	public T findOne(T entity) {
		return this.getOne(queryWrapper(entity), false);
	}

	@CacheEvict(allEntries = true)
	@Override
	public int insert(T entity) {
		Assert.notNull(entity, "entity 不能为空");
		return this.baseMapper.insert(entity);
	}

	@CacheEvict(allEntries = true)
	@Override
	public boolean insertBatch(Collection<T> entitys) {
		if (CollectionUtil.isEmpty(entitys)) {
			return false;
		}
		return this.saveBatch(entitys);
	}

	@CacheEvict(allEntries = true)
	@Override
	public boolean update(T entity) {
		return this.updateById(entity);
	}

	@CacheEvict(allEntries = true)
	@Override
	public boolean update(T entity, T conditions) {
		return this.update(entity, updateWrapper(conditions));
	}

	@CacheEvict(allEntries = true)
	@Override
	public boolean updateBatch(Collection<T> entitys) {
		return this.updateBatchById(entitys);
	}

	@CacheEvict(allEntries = true)
	@Override
	public boolean deleteById(Serializable id) {
		return this.removeById(id);
	}

	@CacheEvict(allEntries = true)
	@Override
	public boolean deleteBatch(Collection<? extends Serializable> ids) {
		return this.removeByIds(ids);
	}

	@CacheEvict(allEntries = true)
	@Override
	public boolean delete(T entity) {
		return this.remove(updateWrapper(entity));
	}

	@CacheEvict(allEntries = true)
	@Override
	public Integer count(T entity) {
		return this.count(queryWrapper(entity));
	}

}
