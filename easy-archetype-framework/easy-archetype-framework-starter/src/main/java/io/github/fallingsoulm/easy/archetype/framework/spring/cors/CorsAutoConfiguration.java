package io.github.fallingsoulm.easy.archetype.framework.spring.cors;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 跨域自动配置中心
 *
 * @author luyanan
 * @since 2021/2/10
 **/
@Configuration
@ConditionalOnProperty(prefix = CorsProperties.PREFIX, name = "enable", havingValue = "true")
@EnableConfigurationProperties(CorsProperties.class)
@Import(CorsConfig.class)
public class CorsAutoConfiguration {



}
