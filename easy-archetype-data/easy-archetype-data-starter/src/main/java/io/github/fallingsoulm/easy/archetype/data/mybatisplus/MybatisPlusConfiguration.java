package io.github.fallingsoulm.easy.archetype.data.mybatisplus;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MybatisPlus 配置
 *
 * @author luyanan
 * @since 2021/1/20
 **/
//@ConditionalOnProperty(prefix = EasyArchetypeFrameworkProperties.PREFIX, name = "mybatisplus.enable",
//		havingValue = "true", matchIfMissing = true)
@ConditionalOnClass(MybatisPlusAutoConfiguration.class)
@Configuration
public class MybatisPlusConfiguration {

	/**
	 * 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false
	 * 避免缓存出现问题(该属性会在旧插件移除后一同移除)
	 */
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		return new PaginationInterceptor();
	}
//	@Bean
//	public MybatisPlusInterceptor mybatisPlusInterceptor() {
//		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
//		// <!-- 对于单一数据库类型来说,都建议配置该值,避免每次分页都去抓取数据库类型 -->
//		interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
//		return interceptor;
//	}

	/**
	 * 自定义方法
	 *
	 * @return com.baomidou.mybatisplus.core.injector.ISqlInjector
	 * @since 2021/1/20
	 */
	@Bean
	public ISqlInjector mySqlInjector() {
		return new MySqlInjector();
	}

}
