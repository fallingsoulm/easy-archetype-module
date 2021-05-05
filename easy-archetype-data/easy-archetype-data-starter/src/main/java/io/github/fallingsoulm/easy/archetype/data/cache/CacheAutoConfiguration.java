package io.github.fallingsoulm.easy.archetype.data.cache;

import io.github.fallingsoulm.easy.archetype.framework.config.EasyArchetypeFrameworkProperties;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 使用redis作为缓存的自动配置文件
 *
 * @author luyanan
 * @since 2021/1/21
 **/
@ConditionalOnBean(annotation = EnableCaching.class)
@ConditionalOnProperty(prefix = EasyArchetypeFrameworkProperties.PREFIX, name = "cache.enable", havingValue = "true",
		matchIfMissing = true)
@Configuration
@EnableConfigurationProperties(CacheProperties.class)
@Import({ io.github.fallingsoulm.easy.archetype.data.cache.CacheConfig.class, io.github.fallingsoulm.easy.archetype.data.cache.RedisCacheAutoConfiguration.class })
public class CacheAutoConfiguration {

}
