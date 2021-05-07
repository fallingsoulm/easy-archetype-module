package io.github.fallingsoulm.easy.archetype.data.mybatisplus;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.fallingsoulm.easy.archetype.data.manage.IManage;
import io.github.fallingsoulm.easy.archetype.framework.page.PageInfo;
import io.github.fallingsoulm.easy.archetype.framework.page.PageRequestParams;
import io.github.fallingsoulm.easy.archetype.framework.utils.BeanUtils;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mybatis plus工具类
 *
 * @author luyanan
 * @since 2021/5/7
 **/
public class MybatisPlusUtils {


	/**
	 * <p>构造  IPage 对象</p>
	 *
	 * @param requestParams 分页请求
	 * @param queryWrapper  条件构造器
	 * @param service       service
	 * @param <T>           枚举
	 * @return {@link IPage}
	 * @author luyanan
	 * @since 2020/1/15
	 */
	public <T> IPage<T> toPage(PageRequestParams<T> requestParams, Wrapper<T> queryWrapper, ServiceImpl service) {
		IPage<T> page = new Page<T>(requestParams.getPageNum(), requestParams.getPageSize());
		page = service.page(page, queryWrapper);
		return page;
	}


	/**
	 * <p>构造  PageInfo 对象</p>
	 *
	 * @param requestParams 分页请求参数
	 * @param queryWrapper  条件构造
	 * @param service       service
	 * @param handler       分页处理handler
	 * @param <T>           枚举
	 * @return {@link PageInfo}
	 * @author luyanan
	 * @since 2020/1/15
	 */
	public <T> PageInfo<T> toPageInfo(PageRequestParams<T> requestParams,
									  Wrapper<T> queryWrapper, ServiceImpl service, PageInfoContentHandler<T> handler) {
		IPage<T> page = toPage(requestParams, queryWrapper, service);

		PageInfo<T> pageInfo = null;
		try {
			pageInfo = PageInfo.class.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		pageInfo.setTotalElements(page.getTotal());

		List<T> content = page.getRecords();
		if (null != handler) {
			handler.handler(content);
		}
		pageInfo.setPageNum(requestParams.getPageNum());
		pageInfo.setPageSize(requestParams.getPageSize());
		pageInfo.setContent(content);
		return pageInfo;
	}


	/**
	 * <p>构造  PageInfo 对象</p>
	 *
	 * @param requestParams 分页请求参数
	 * @param queryWrapper  条件构造
	 * @param service       service
	 * @param <T>           枚举
	 * @return {@link PageInfo}
	 * @author luyanan
	 * @since 2020/1/15
	 */
	public <T> PageInfo<T> toPageInfo(PageRequestParams<T> requestParams, Wrapper<T> queryWrapper, ServiceImpl service) {
		return toPageInfo(requestParams, queryWrapper, service, null);
	}


	/**
	 * <p>分页请求参数,VO->DO</p>
	 *
	 * @param requestParams 分页参数
	 * @param doClass       Do 类
	 * @return {@link PageRequestParams<DO>}
	 * @author luyanan
	 * @since 2020/5/31
	 */
	public <DO, VO> PageRequestParams<DO> convertPageRequestParams(PageRequestParams<VO> requestParams,
																   Class<? extends DO> doClass) {
		PageRequestParams<DO> params = new PageRequestParams<>();
		params.setPageSize(requestParams.getPageSize());
		params.setOffset(requestParams.getOffset());
//        params.setIsAsc(requestParams.getIsAsc());
//        params.setOrderByColumn(requestParams.getOrderByColumn());
		DO doParam = BeanUtils.copyProperties(requestParams.getParams(), doClass);
		params.setParams(doParam);
		return params;
	}

	public <DO, VO> PageInfo<VO> convertPageInfo(PageInfo<DO> pageInfo, Class<VO> voClass) {
		return convertPageInfo(pageInfo, voClass, null);
	}

	/**
	 * <p>转换PageInfo Do->Vo</p>
	 *
	 * @param pageInfo
	 * @param voClass
	 * @param handler
	 * @return {@link PageInfo<VO>}
	 * @author luyanan
	 * @since 2020/5/31
	 */
	public <DO, VO> PageInfo<VO> convertPageInfo(PageInfo<DO> pageInfo, Class<VO> voClass,
												 PageInfoContentHandler<VO> handler) {

		PageInfo<VO> voPageInfo = new PageInfo<>();
		voPageInfo.setPageNum(pageInfo.getPageNum());
		voPageInfo.setPageSize(pageInfo.getPageSize());
		voPageInfo.setTotalElements(pageInfo.getTotalElements());
		List<DO> content = pageInfo.getContent();

		List<VO> list = BeanUtils.copyList((List<Object>) content, voClass);
		if (null != handler && null != list) {
			handler.handler(list);
		}
		voPageInfo.setContent(list);
		return voPageInfo;
	}

	/**
	 * 大数据查询并处理
	 *
	 * @param params 查询参数
	 * @author luyanan
	 * @since 2020/11/30
	 */
	public <T> void bigDataList(T params,
								IManage<T> manage,
								BigDataListHandler<T> bigDataListHandler) {

		bigDataList(params, 10, manage, bigDataListHandler);
	}

	/**
	 * 大数据查询并处理
	 *
	 * @param params 查询参数
	 * @param size   每次处理的条数
	 * @author luyanan
	 * @since 2020/11/30
	 */
	public <T> void bigDataList(T params,
								Integer size,
								IManage<T> manage,
								BigDataListHandler<T> bigDataListHandler) {

		boolean hasNext = true;
		Integer index = 1;

		while (hasNext) {

			PageRequestParams<T> pageRequestParams = new PageRequestParams<>();
			pageRequestParams.setPageSize(size);
			pageRequestParams.setParams(params);
			pageRequestParams.setPageNum(index);
			PageInfo<T> pageInfo = manage.listByPage(pageRequestParams);
			List<T> content = pageInfo.getContent();
			if (CollectionUtil.isEmpty(content)) {
				hasNext = false;
			}
			index++;
			if (null != bigDataListHandler) {
				bigDataListHandler.handler(pageInfo.getContent());
			}
		}
	}

	@FunctionalInterface
	public interface BigDataListHandler<T> {

		/**
		 * 数据处理
		 *
		 * @param data
		 * @author luyanan
		 * @since 2020/11/30
		 */
		void handler(List<T> data);
	}

	public interface PageInfoHandler<T> {

		/**
		 * <p>获取总数</p>
		 *
		 * @return {@link Integer}
		 * @author luyanan
		 * @since 2020/4/20
		 */
		Long count();


		/**
		 * <p>获取列表</p>
		 *
		 * @return {@link List <T>}
		 * @author luyanan
		 * @since 2020/4/20
		 */
		List<T> contents();
	}

	public <T> PageInfo<T> pageInfo(PageRequestParams<T> pageRequestParams,

									PageInfoHandler pageInfoHandler) {


		Assert.notNull(pageInfoHandler, "PageInfoHandler 不能为null");
		List<T> contents = null;
		Long count = pageInfoHandler.count();
		if (count != null && count > 0) {
			contents = pageInfoHandler.contents();
		} else {
			count = 0L;
			contents = new ArrayList<>();
		}
		return new PageInfo<T>(contents, count, pageRequestParams);
	}


	/**
	 * <p>逻辑分页</p>
	 *
	 * @param pageRequestParams 分页参数
	 * @param allData           所有的数据
	 * @return {@link PageInfo<T>}
	 * @author luyanan
	 * @since 2020/5/9
	 */
	public <T> PageInfo<T> logicPageInfo(PageRequestParams pageRequestParams,
										 List<T> allData) {
		List<T> pagingData = allData.stream().skip(pageRequestParams.getOffset())
				.limit(pageRequestParams.getPageSize()).collect(Collectors.toList());
		return new PageInfo<T>(pagingData, (long) allData.size(), pageRequestParams);
	}


}
