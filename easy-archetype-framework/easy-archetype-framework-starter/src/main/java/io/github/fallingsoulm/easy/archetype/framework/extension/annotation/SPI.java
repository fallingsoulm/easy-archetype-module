package io.github.fallingsoulm.easy.archetype.framework.extension.annotation;

import java.lang.annotation.*;

/**
 * SPI注解
 *
 * @author luyanan
 * @since 2021/3/4
 **/
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.TYPE)
public @interface SPI {

	/**
	 * 默认的扩展点的名称
	 *
	 * @return java.lang.String
	 * @since 2021/3/4
	 */
	String value();
}
