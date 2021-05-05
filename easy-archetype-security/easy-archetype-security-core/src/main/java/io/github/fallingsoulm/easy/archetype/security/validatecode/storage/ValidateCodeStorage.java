package io.github.fallingsoulm.easy.archetype.security.validatecode.storage;

import java.time.Duration;

/**
 * 验证码的存储
 *
 * @author luyanan
 * @since 2021/2/9
 **/
public interface ValidateCodeStorage {


	/**
	 * 存储验证码
	 *
	 * @param key     验证码的key
	 * @param code    验证码
	 * @param timeout 有效时间
	 * @return void
	 * @since 2021/2/9
	 */
	void storage(String key, String code, Duration timeout);


	/**
	 * 获取验证码
	 *
	 * @param key 验证码的key
	 * @return java.lang.String
	 * @since 2021/2/9
	 */
	String getCode(String key);
}
