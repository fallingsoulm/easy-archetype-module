package io.github.fallingsoulm.easy.archetype.framework.spring.cors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跨域配置
 *
 * @author luyanan
 * @since 2021/2/10
 **/
@Configuration
public class CorsConfig implements WebMvcConfigurer {
	@Autowired
	private CorsProperties corsProperties;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("*")
				.allowCredentials(true)
				.allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH")
				.maxAge(3600);
//		registry.addMapping(corsProperties.getPathPattern()).
//				//允许跨域的域名，可以用*表示允许任何域名使用
//						allowedOrigins(corsProperties.getOrigins()).
//				//允许任何方法（post、get等）
//						allowedMethods(corsProperties.getMethods()).
//				//允许任何请求头
//						allowedHeaders(corsProperties.getHeaders()).
//				//带上cookie信息
//						allowCredentials(corsProperties.isAllowCredentials()).
//				//maxAge(3600)表明在3600秒内，不需要再发送预检验请求，可以缓存该结果
//						exposedHeaders(HttpHeaders.SET_COOKIE).maxAge(corsProperties.getMaxAge());
	}

//	@Bean
//	public WebMvcConfigurer corsConfigurer(CorsProperties corsProperties) {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//
//			}
//		};
//	}
}
