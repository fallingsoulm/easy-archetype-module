package io.github.fallingsoulm.easy.archetype.security.validatecode.storage;

import io.github.fallingsoulm.easy.archetype.security.validatecode.ValidateCodeProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.data.redis.core.RedisTemplate;

import java.text.MessageFormat;
import java.time.Duration;

/**
 * 使用redis存储验证码
 *
 * @author luyanan
 * @since 2021/2/10
 **/
@ConditionalOnBean(RedisTemplate.class)
public class RedisValidateCodeStorage implements ValidateCodeStorage {


	private RedisTemplate<String, String> redisTemplate;

	private ValidateCodeProperties validateCodeProperties;

	public RedisValidateCodeStorage(RedisTemplate<String, String> redisTemplate, ValidateCodeProperties validateCodeProperties) {
		this.redisTemplate = redisTemplate;
		this.validateCodeProperties = validateCodeProperties;
	}

	@Override
	public void storage(String key, String code, Duration timeout) {
		redisTemplate.opsForValue().set(getRedis(key), code, timeout);
	}

	@Override
	public String getCode(String key) {
		String value = redisTemplate.opsForValue().get(getRedis(key));
		return value;
	}


	/**
	 * 使用redis存储的时候的key
	 *
	 * @param key
	 * @return java.lang.String
	 * @since 2021/2/18
	 */
	private String getRedis(String key) {
		return MessageFormat.format(validateCodeProperties.getRedisStorageKey(), key);
	}
}
