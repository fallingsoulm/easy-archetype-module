package io.github.fallingsoulm.easy.archetype.generate.conver;

import io.github.fallingsoulm.easy.archetype.generate.core.FactoryRegistryHandler;
import io.github.fallingsoulm.easy.archetype.generate.exception.GeneratorException;

import java.sql.Driver;
import java.util.HashMap;
import java.util.Map;

/**
 * 类型转换的工程
 *
 * @author luyanan
 * @since 2021/1/31
 **/
public class ColumnTypeConverFactory {

	/**
	 * 类型转换存储
	 *
	 * @since 2021/1/31
	 */
	private Map<Class<? extends Driver>, IColumnTypeConver> typeConverMap = new HashMap<>();

	/**
	 * 类型转换注册
	 * @param factoryRegistryHandler 注册处理类
	 * @return void
	 * @since 2021/1/31
	 */
	public void registry(
			FactoryRegistryHandler<Map<Class<? extends Driver>, IColumnTypeConver>> factoryRegistryHandler) {
		if (null != factoryRegistryHandler) {
			factoryRegistryHandler.registry(this.typeConverMap);
		}
	}

	/**
	 * 根据数据库驱动获取类型转换器
	 * @param type 数据库驱动
	 * @return io.github.fallingsoulm.easy.archetype.framework.core.generate.conver.IColumnTypeConver
	 * @since 2021/1/31
	 */
	public IColumnTypeConver getConver(Class<? extends Driver> type) {
		if (!typeConverMap.containsKey(type)) {
			throw new GeneratorException("未注册的数据库类型:" + type);
		}
		return typeConverMap.get(type);
	}

}
