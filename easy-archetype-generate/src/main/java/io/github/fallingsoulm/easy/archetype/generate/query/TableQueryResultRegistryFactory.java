package io.github.fallingsoulm.easy.archetype.generate.query;

import io.github.fallingsoulm.easy.archetype.generate.core.FactoryRegistryHandler;
import io.github.fallingsoulm.easy.archetype.generate.exception.GeneratorException;

import java.sql.Driver;
import java.util.HashMap;
import java.util.Map;

/**
 * 表结果查询的注册工程类
 *
 * @author luyanan
 * @since 2021/2/1
 **/
public class TableQueryResultRegistryFactory {

	private Map<Class<? extends Driver>, ITableQueryResult> queryResultMap = new HashMap<>();

	/**
	 * 注册
	 * @param registryHandler
	 * @return void
	 * @since 2021/2/1
	 */
	public void registry(FactoryRegistryHandler<Map<Class<? extends Driver>, ITableQueryResult>> registryHandler) {

		if (null != registryHandler) {
			registryHandler.registry(queryResultMap);
		}
	}

	/**
	 * 根据不同的驱动获取不同的查询驱动
	 * @param driver
	 * @return io.github.fallingsoulm.easy.archetype.framework.core.generate.query.ITableQueryResult
	 * @since 2021/2/1
	 */
	public ITableQueryResult get(Class<? extends Driver> driver) {
		ITableQueryResult tableQueryResult = queryResultMap.get(driver);
		if (null == tableQueryResult) {
			throw new GeneratorException("未知的:[" + driver.getName() + "]ITableQueryResult");
		}
		return tableQueryResult;
	}

}
