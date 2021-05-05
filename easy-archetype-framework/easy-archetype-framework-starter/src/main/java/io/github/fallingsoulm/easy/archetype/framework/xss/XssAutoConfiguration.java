package io.github.fallingsoulm.easy.archetype.framework.xss;

import io.github.fallingsoulm.easy.archetype.framework.xss.core.FormXssClean;
import io.github.fallingsoulm.easy.archetype.framework.xss.core.JacksonXssClean;
import io.github.fallingsoulm.easy.archetype.framework.xss.core.XssCleanInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * xss自动配置类
 *
 * @author luyanan
 * @since 2021/2/8
 **/
@ConditionalOnClass(WebMvcConfigurer.class)
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(XssProperties.class)
@ConditionalOnProperty(value = XssProperties.PREFIX + ".enable", havingValue = "true", matchIfMissing = true)
public class XssAutoConfiguration implements WebMvcConfigurer {

	private final XssProperties xssProperties;

	@Bean
	public FormXssClean formXssClean() {
		return new FormXssClean();
	}

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer xssJacksonCustomizer() {
		return builder -> builder.deserializerByType(String.class, new JacksonXssClean());
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		XssCleanInterceptor interceptor = new XssCleanInterceptor(xssProperties);
		registry.addInterceptor(interceptor).addPathPatterns(xssProperties.getPathPatterns())
				.excludePathPatterns(xssProperties.getExcludePatterns()).order(Ordered.LOWEST_PRECEDENCE);
	}

}
