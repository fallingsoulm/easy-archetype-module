package io.github.fallingsoulm.easy.archetype.data.cache;

import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis作为缓存的配置
 *
 * @author luyanan
 * @since 2021/1/24
 **/
@ConditionalOnClass(RedisCacheConfiguration.class)
@ConditionalOnProperty(value = "spring.cache.type", havingValue = "redis", matchIfMissing = false)
public class RedisCacheAutoConfiguration {

	@Bean
	public RedisCacheConfiguration redisCacheConfiguration(CacheProperties cacheProperties) {

		RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
		config = config.serializeKeysWith(
				RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()));
		config = config.serializeValuesWith(
				RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json()));

		// 将配置文件中的配置生效
		CacheProperties.Redis redisProperties = cacheProperties.getRedis();
		if (redisProperties.getTimeToLive() != null) {
			config = config.entryTtl(redisProperties.getTimeToLive());
		}

		if (redisProperties.getKeyPrefix() != null) {
			config = config.prefixKeysWith(redisProperties.getKeyPrefix());
		}

		if (!redisProperties.isCacheNullValues()) {
			config = config.disableCachingNullValues();
		}

		if (!redisProperties.isUseKeyPrefix()) {
			config = config.disableKeyPrefix();
		}

		return config;
	}

}
