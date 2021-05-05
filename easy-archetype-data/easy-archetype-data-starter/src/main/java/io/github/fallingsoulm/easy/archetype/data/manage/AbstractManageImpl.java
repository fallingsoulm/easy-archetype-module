package io.github.fallingsoulm.easy.archetype.data.manage;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.fallingsoulm.easy.archetype.framework.page.PageInfo;
import io.github.fallingsoulm.easy.archetype.framework.page.PageRequestParams;
import io.github.fallingsoulm.easy.archetype.data.mybatisplus.BaseMapperPlus;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * manage的抽象类
 *
 * @author luyanan
 * @since 2021/1/20
 **/
public abstract class AbstractManageImpl<M extends BaseMapperPlus<T>, T> extends ServiceImpl<M, T>
		implements IManage<T> {

	@Autowired
	protected M baseMapper;

	/**
	 * Lambda 查询条件构造器
	 *
	 * @param entity 实体条件
	 * @return com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<T>
	 * @since 2021/1/20
	 */
	protected LambdaQueryWrapper<T> lambdaQueryWrapper(T entity) {
		return queryWrapper(entity).lambda();
	}

	/**
	 * 查询条件构造器
	 *
	 * @param entity 实体条件
	 * @return com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
	 * @since 2021/1/20
	 */
	protected QueryWrapper<T> queryWrapper(T entity) {
		QueryWrapper queryWrapper = new QueryWrapper(entity);
		return queryWrapper;
	}

	/**
	 * 修改条件构造器
	 *
	 * @param entity 实体条件
	 * @return com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<T>
	 * @since 2021/1/20
	 */
	protected UpdateWrapper<T> updateWrapper(T entity) {
		return new UpdateWrapper<>(entity);
	}

	/**
	 * 修改条件构造器
	 *
	 * @param entity 实体条件
	 * @return com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper<T>
	 * @since 2021/1/20
	 */
	protected LambdaUpdateWrapper<T> lambdaUpdateWrapper(T entity) {
		return updateWrapper(entity).lambda();
	}


	/**
	 * 获取分页IPage
	 *
	 * @param requestParams 分页请求参数
	 * @param queryWrapper  条件
	 * @return com.baomidou.mybatisplus.core.metadata.IPage
	 * @since 2021/1/21
	 */
	public <E extends IPage<T>> IPage toPage(PageRequestParams<T> requestParams, Wrapper<T> queryWrapper) {
		IPage<T> page = new Page<>(requestParams.getPageNum(), requestParams.getPageSize());
		page = this.baseMapper.selectPage(page, queryWrapper);
		return page;
	}

	/**
	 * 获取分页的结果
	 *
	 * @param pageRequestParams 分页请求参数
	 * @param queryWrapper      条件构造器
	 * @return io.github.fallingsoulm.easy.archetype.framework.core.page.PageInfo<T>
	 * @since 2021/1/21
	 */
	public <E extends IPage<T>> PageInfo<T> toPageInfo(PageRequestParams<T> pageRequestParams,
													   Wrapper<T> queryWrapper) {
		IPage page = toPage(pageRequestParams, queryWrapper);
		PageInfo<T> pageInfo = new PageInfo<T>(page.getRecords(), page.getTotal(), pageRequestParams);
		return pageInfo;
	}

}
