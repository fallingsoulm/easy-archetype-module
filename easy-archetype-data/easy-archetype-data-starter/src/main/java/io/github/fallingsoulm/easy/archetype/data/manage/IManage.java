package io.github.fallingsoulm.easy.archetype.data.manage;


import io.github.fallingsoulm.easy.archetype.framework.page.PageInfo;
import io.github.fallingsoulm.easy.archetype.framework.page.PageRequestParams;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 抽象类manage方法
 *
 * @author luyanan
 * @since 2021/1/20
 **/
public interface IManage<T> {

	/**
	 * 根据id查询
	 * @param id id
	 * @return T 返回的实体
	 * @since 2021/1/20
	 */
	T findById(Serializable id);

	/**
	 * 分页查询
	 * @param pageRequestParams 分页参数
	 * @return io.github.fallingsoulm.easy.archetype.framework.core.page.PageInfo<T> 分页
	 * @since 2021/1/20
	 */
	PageInfo<T> listByPage(PageRequestParams<T> pageRequestParams);

	/**
	 * 根据条件查询
	 * @param entity 实体条件
	 * @return java.util.List<T> 实体集合
	 * @since 2021/1/20
	 */
	List<T> list(T entity);

	/**
	 * 根据id集合查询
	 * @param ids id集合
	 * @return java.util.List<T> 实体集合
	 * @since 2021/1/20
	 */
	List<T> findByIds(Collection<? extends Serializable> ids);

	/**
	 * 根据条件查询单条数据
	 * @param entity 实体条件
	 * @return T 返回的实体
	 * @since 2021/1/20
	 */
	T findOne(T entity);

	/**
	 * 插入实体
	 * @param entity 实体
	 * @return java.io.Serializable
	 * @since 2021/1/20
	 */
	int insert(T entity);

	/**
	 * 批量插入
	 * @param entitys 实体集合
	 * @return int
	 * @since 2021/1/20
	 */
	boolean insertBatch(Collection<T> entitys);

	/**
	 * 根据id进行修改
	 * @param entity 实体
	 * @return void
	 * @since 2021/1/20
	 */
	boolean update(T entity);

	/**
	 * 根据实体条件进行修改
	 * @param entity 修改的参数
	 * @param conditions 修改的条件
	 * @return boolean
	 * @since 2021/1/20
	 */
	boolean update(T entity, T conditions);

	/**
	 * 批量修改
	 * @param entitys 实体集合
	 * @return boolean
	 * @since 2021/1/20
	 */
	boolean updateBatch(Collection<T> entitys);

	/**
	 * 根据id进行删除
	 * @param id id
	 * @return boolean
	 * @since 2021/1/20
	 */
	boolean deleteById(Serializable id);

	/**
	 * 根据id集合 批量删除
	 * @param ids id集合
	 * @return boolean
	 * @since 2021/1/20
	 */
	boolean deleteBatch(Collection<? extends Serializable> ids);

	/**
	 * 根据实体条件不能删除
	 * @param entity 实体条件
	 * @return boolean
	 * @since 2021/1/20
	 */
	boolean delete(T entity);

	/**
	 * 根据实体条件统计
	 * @param entity
	 * @return java.lang.Integer
	 * @since 2021/1/20
	 */
	Integer count(T entity);

}
