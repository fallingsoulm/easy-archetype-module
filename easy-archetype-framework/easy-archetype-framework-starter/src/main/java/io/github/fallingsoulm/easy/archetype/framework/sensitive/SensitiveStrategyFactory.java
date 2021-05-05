package io.github.fallingsoulm.easy.archetype.framework.sensitive;

import cn.hutool.core.lang.Assert;
import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.Map;

/**
 * 脱敏策略工厂
 *
 * @author luyanan
 * @since 2021/2/8
 **/
public class SensitiveStrategyFactory {

	public static Map<Class<? extends SensitiveStrategy>, SensitiveStrategy> cache = new HashMap<>();

	@SneakyThrows
	public static SensitiveStrategy get(Class<? extends SensitiveStrategy> sensitiveStrategy) {
		Assert.isNull(sensitiveStrategy, "脱敏策略不能为空");
		SensitiveStrategy strategy = cache.get(sensitiveStrategy);
		if (null == strategy) {
			// 默认实例化一个
			strategy = sensitiveStrategy.newInstance();
			cache.put(sensitiveStrategy, strategy);
		}
		return strategy;
	}

}
