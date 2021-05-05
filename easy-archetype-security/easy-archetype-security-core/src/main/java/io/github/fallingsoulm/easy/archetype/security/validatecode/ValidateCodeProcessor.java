package io.github.fallingsoulm.easy.archetype.security.validatecode;


import io.github.fallingsoulm.easy.archetype.security.validatecode.storage.ValidateCodeStorage;

import java.time.Duration;
import java.util.Map;

/**
 * 验证码处理逻辑
 *
 * @author luyanan
 * @since 2021/2/9
 **/
public interface ValidateCodeProcessor {

	/**
	 * 验证码处理器类型
	 *
	 * @return java.lang.String 验证码处理器类型
	 * @since 2021/2/9
	 */
	String type();


	/**
	 * 设置验证码存储气
	 *
	 * @return io.github.fallingsoulm.easy.archetype.system.security.validatecode.storage.ValidateCodeStorage
	 * @since 2021/2/10
	 */
	ValidateCodeStorage validateCodeStorage();


	/**
	 * 验证码创建
	 *
	 * @param key     验证码的key
	 * @param timeout 有效时间
	 * @return java.util.Map<java.lang.String, java.lang.Object>
	 * @since 2021/2/10
	 */
	Map<String, Object> create(String key, Duration timeout);


	/**
	 * 验证码校验
	 *
	 * @param key  验证码的key
	 * @param code 验证码
	 * @return void
	 * @since 2021/2/9
	 */
	void validate(String key, String code);
}
