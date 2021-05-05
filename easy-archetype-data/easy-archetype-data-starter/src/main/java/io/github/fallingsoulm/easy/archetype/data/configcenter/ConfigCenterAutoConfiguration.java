package io.github.fallingsoulm.easy.archetype.data.configcenter;

import io.github.fallingsoulm.easy.archetype.data.configcenter.jdbc.JdbcPropertySource;
import io.github.fallingsoulm.easy.archetype.framework.config.EasyArchetypeFrameworkProperties;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

import javax.sql.DataSource;

/**
 * 配置中心自动配置类
 *
 * @author luyanan
 * @since 2021/1/29
 **/
@ConditionalOnWebApplication
@ConditionalOnClass(ContextRefresher.class)
@ConditionalOnProperty(prefix = ConfigCenterProperties.PREFIX, name = "enable", havingValue = "true",
		matchIfMissing = true)
@EnableConfigurationProperties(ConfigCenterProperties.class)
@Configuration
public class ConfigCenterAutoConfiguration {

	@Bean
	public ConfigCenter configCenter(PropertySource propertySource, ContextRefresher contextRefresher,
									 ConfigurableEnvironment configurableEnvironment) {
		ConfigCenter configCenter = new ConfigCenter(propertySource, contextRefresher, configurableEnvironment);
		configCenter.mergeProperties();
		return configCenter;
	}


	@ConditionalOnMissingBean(PropertySource.class)
	@Bean
	public PropertySource propertySource(DataSource dataSource) {
		return new JdbcPropertySource(dataSource);
	}

}
