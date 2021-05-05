package io.github.fallingsoulm.easy.archetype.framework.extension.annotation;

import java.lang.annotation.*;

/**
 * 扩展点依赖注入
 *
 * @author luyanan
 * @since 2021/3/5
 **/
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Inject {


	/**
	 * 被注入扩展点的名称,默认情况下注入默认扩展点
	 *
	 * @return java.lang.String
	 * @since 2021/3/5
	 */
	String value() default "";
}
