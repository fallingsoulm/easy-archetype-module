package io.github.fallingsoulm.easy.archetype.security.validatecode;


import io.github.fallingsoulm.easy.archetype.security.validatecode.captcha.CaptchaConfig;
import io.github.fallingsoulm.easy.archetype.security.validatecode.captcha.CaptchaValidateCodeProcessor;
import io.github.fallingsoulm.easy.archetype.security.validatecode.storage.RedisValidateCodeStorage;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 验证码自动配置类
 *
 * @author luyanan
 * @since 2021/2/10
 **/
@ConditionalOnProperty(prefix = ValidateCodeProperties.PREFIX, name = "enable", havingValue = "true", matchIfMissing = false)
@EnableConfigurationProperties(ValidateCodeProperties.class)
@Configuration
@Import(CaptchaConfig.class)
public class ValidateCodeAutoConfiguration {


	@Bean
	public ValidateCodeTemplate validateCodeTemplate(ObjectProvider<ValidateCodeProcessor> validateCodeProcessors) {
		return new ValidateCodeTemplate(validateCodeProcessors, ValidateCodeConstant.TYPE_IMA);
	}

	@Bean
	public ValidateCodeProcessor captchaValidateCodeProcessor(RedisValidateCodeStorage redisValidateCodeStorage) {
		return new CaptchaValidateCodeProcessor(redisValidateCodeStorage);
	}

	@Bean
	public RedisValidateCodeStorage redisValidateCodeStorage(RedisTemplate redisTemplate, ValidateCodeProperties validateCodeProperties) {
		return new RedisValidateCodeStorage(redisTemplate, validateCodeProperties);
	}


	@Bean
	public ValidateCodeFilter validateCodeFilter() {
		return new ValidateCodeFilter();
	}
}
