package io.github.fallingsoulm.easy.archetype.security.validatecode;

import cn.hutool.core.util.StrUtil;
import org.springframework.util.Assert;

import java.time.Duration;
import java.util.Map;

/**
 * 验证码处理类的抽象类
 *
 * @author luyanan
 * @since 2021/2/10
 **/
public abstract class AbstractValidateCodeProcessor implements ValidateCodeProcessor {


	@Override
	public Map<String, Object> create(String key, Duration timeout) {
		String code = null;
		Map<String, Object> map = doCreate(key, code, timeout);
		if (StrUtil.isBlank(code)) {
			code = map.get("code").toString();
		}
		Assert.hasText(code, "验证码不能为空");
		validateCodeStorage().storage(key, code, timeout);
		map.put("code", null);
		return map;
	}

	/**
	 * 进行验证码的生成
	 *
	 * @param code    验证码
	 * @param key     验证码的key
	 * @param timeout 有效时间
	 * @return java.util.Map<java.lang.String, java.lang.Object>
	 * @since 2021/2/10
	 */
	protected abstract Map<String, Object> doCreate(String key, String code, Duration timeout);

	@Override
	public void validate(String key, String code) {

		String storageCode = validateCodeStorage().getCode(key);
		if (StrUtil.isBlank(storageCode)) {
			throw new ValidateCodeException("验证码不正确");
		}
		if (!code.equalsIgnoreCase(storageCode)) {
			throw new ValidateCodeException("验证码不正确");
		}
	}
}
