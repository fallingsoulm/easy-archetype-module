package io.github.fallingsoulm.easy.archetype.security.validatecode;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;

import java.time.Duration;
import java.util.Map;

/**
 * 验证码模板类
 *
 * @author luyanan
 * @since 2021/2/9
 **/
@RequiredArgsConstructor
public class ValidateCodeTemplate {
	/**
	 * 所有注入的验证码处理类
	 *
	 * @since 2021/2/9
	 */
	private final ObjectProvider<ValidateCodeProcessor> validateCodeProcessors;
	/**
	 * 默认的类型
	 *
	 * @since 2021/2/10
	 */
	private final String defaultType;


	/**
	 * 根据类型获取验证码处理器
	 *
	 * @param type 处理器类型
	 * @return io.github.fallingsoulm.easy.archetype.system.security.validatecode.ValidateCodeProcessor
	 * @since 2021/2/9
	 */
	public ValidateCodeProcessor processor(String type) {
		return getProcessor(type);
	}


	/**
	 * 生成验证码
	 *
	 * @param type    验证码类型
	 * @param key     验证码的key
	 * @param timeout 有效时间
	 * @return void
	 * @since 2021/2/10
	 */
	public Map<String, Object> create(String type, String key, Duration timeout) {
		return getProcessor(type).create(key, timeout);
	}

	/**
	 * 验证码校验
	 *
	 * @param type 验证码类型
	 * @param key  验证码的key
	 * @param code 验证码
	 * @return void
	 * @since 2021/2/9
	 */
	public void validate(String type, String key, String code) {
		getProcessor(type).validate(key, code);
	}

	/**
	 * 生成验证码
	 *
	 * @param key     验证码的key
	 * @param timeout 有效时间
	 * @return void
	 * @since 2021/2/10
	 */
	public Map<String, Object> create(String key, Duration timeout) {
		return getProcessor(defaultType).create(key, timeout);
	}

	/**
	 * 验证码校验
	 *
	 * @param key  验证码的key
	 * @param code 验证码
	 * @return void
	 * @since 2021/2/9
	 */
	public void validate(String key, String code) {
		getProcessor(defaultType).validate(key, code);
	}


	/**
	 * 根据类型获取验证码处理器
	 *
	 * @param type 类型
	 * @return io.github.fallingsoulm.easy.archetype.system.security.validatecode.ValidateCodeProcessor
	 * @since 2021/2/9
	 */
	private ValidateCodeProcessor getProcessor(String type) {
		return validateCodeProcessors
				.stream()
				.filter(validateCodeProcessor -> validateCodeProcessor.type().equals(type))
				.findFirst().get();
	}

}
