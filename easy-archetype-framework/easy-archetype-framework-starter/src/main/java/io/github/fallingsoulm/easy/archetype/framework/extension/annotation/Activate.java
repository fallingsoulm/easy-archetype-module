package io.github.fallingsoulm.easy.archetype.framework.extension.annotation;

import java.lang.annotation.*;

/**
 * 自动激活扩展点
 *
 * @author luyanan
 * @since 2021/3/5
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Activate {

	/**
	 * 分组
	 *
	 * @return java.lang.String[]
	 * @since 2021/3/5
	 */
	String[] group() default {};


	/**
	 * 扩展点的名称
	 *
	 * @return java.lang.String[]
	 * @since 2021/3/5
	 */
	String[] value() default {};


	/**
	 * 排序
	 *
	 * @return int
	 * @since 2021/3/5
	 */
	int order() default 0;
}
