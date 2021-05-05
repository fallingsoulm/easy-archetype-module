package io.github.fallingsoulm.easy.archetype.data.cache;

import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.AbstractCacheResolver;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.Collections;

/**
 * 增加默认 cache cacheNames 设置
 *
 * @author luyanan
 * @since 2021/1/21
 **/
public class ConstomerCacheResolver extends AbstractCacheResolver {

	public ConstomerCacheResolver() {
	}

	public ConstomerCacheResolver(CacheManager cacheManager) {
		super(cacheManager);
	}

	@Override
	protected Collection<String> getCacheNames(CacheOperationInvocationContext<?> context) {
		Collection<String> cacheNames = (context.getOperation().getCacheNames() == null
				|| context.getOperation().getCacheNames().size() == 0)
						? Collections.singleton(context.getTarget().getClass().getName())
						: context.getOperation().getCacheNames();
		return cacheNames;
	}

}