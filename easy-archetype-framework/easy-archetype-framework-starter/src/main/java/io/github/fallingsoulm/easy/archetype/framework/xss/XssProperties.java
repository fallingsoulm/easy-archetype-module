package io.github.fallingsoulm.easy.archetype.framework.xss;

import cn.hutool.core.collection.CollectionUtil;
import io.github.fallingsoulm.easy.archetype.framework.config.EasyArchetypeFrameworkProperties;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * xss的配置类
 *
 * @author luyanan
 * @since 2021/2/8
 **/
@Data
@ConfigurationProperties(XssProperties.PREFIX)
public class XssProperties implements InitializingBean {

	public static final String PREFIX = EasyArchetypeFrameworkProperties.PREFIX + ".xss";

	/**
	 * 开启xss
	 *
	 * @since 2021/2/8
	 */
	private boolean enable = true;

	/**
	 * 拦截的路由,默认拦截/**
	 *
	 * @since 2021/2/8
	 */
	private List<String> pathPatterns = new ArrayList<>();

	/**
	 * 放行的规则,默认为空
	 *
	 * @since 2021/2/8
	 */
	private List<String> excludePatterns = new ArrayList<>();

	@Override
	public void afterPropertiesSet() throws Exception {
		if (CollectionUtil.isEmpty(pathPatterns)) {
			pathPatterns.add("/**");
		}
	}

}
