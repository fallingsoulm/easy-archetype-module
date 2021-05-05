package io.github.fallingsoulm.easy.archetype.framework.spring;

import io.github.fallingsoulm.easy.archetype.framework.config.EasyArchetypeFrameworkProperties;
import io.github.fallingsoulm.easy.archetype.framework.spring.cors.CorsAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * spring的自动配置
 *
 * @author luyanan
 * @since 2021/2/21
 **/
@ConditionalOnProperty(prefix = EasyArchetypeFrameworkProperties.PREFIX, name = "spring.enable", havingValue = "true",
		matchIfMissing = true)
@Configuration
@Import({SpringContextHolder.class,
		CorsAutoConfiguration.class})
public class SpringAutoConfiguration {
}
