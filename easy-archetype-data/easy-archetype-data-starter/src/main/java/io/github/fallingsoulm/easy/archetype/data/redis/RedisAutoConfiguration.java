package io.github.fallingsoulm.easy.archetype.data.redis;

import io.github.fallingsoulm.easy.archetype.framework.config.EasyArchetypeFrameworkProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis的相关配置
 *
 * @author luyanan
 * @since 2021/1/21
 **/
@ConditionalOnProperty(prefix = EasyArchetypeFrameworkProperties.PREFIX, name = "redis.enable", havingValue = "true",
		matchIfMissing = true)
@ConditionalOnClass(RedisConnectionFactory.class)
@ConditionalOnBean({ RedisConnectionFactory.class, RedisAutoConfiguration.class })
@Configuration
public class RedisAutoConfiguration {

	@Bean
	public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {

		RedisTemplate redisTemplate = new RedisTemplate();
		redisTemplate.setConnectionFactory(redisConnectionFactory);

		// 解决key的序列化方式，使用String。
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		redisTemplate.setKeySerializer(stringRedisSerializer);

		// 解决value的序列化方式，使用Json。其中的日期再另外处理。
		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
		// 如果序列化对象中有日期格式，可以自定义日期的序列化类，处理日期的序列化方式。其他需要序列化的类型，也可以按此处理。
		redisTemplate.setHashKeySerializer(stringRedisSerializer);
		redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
		redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
		redisTemplate.setDefaultSerializer(jackson2JsonRedisSerializer);
		return redisTemplate;
	}

}
