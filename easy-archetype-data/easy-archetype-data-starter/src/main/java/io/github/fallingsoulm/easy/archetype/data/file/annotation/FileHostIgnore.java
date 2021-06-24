package io.github.fallingsoulm.easy.archetype.data.file.annotation;

import java.lang.annotation.*;

/**
 * 文件Host操作
 *
 * @author luyanan
 * @since 2021/6/6
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Inherited
public @interface FileHostIgnore {

	/**
	 * 添加host
	 *
	 * @author Administrator
	 * @since 2021/6/6
	 */
	boolean addHost() default true;

	/**
	 * 移除host
	 *
	 * @author Administrator
	 * @since 2021/6/6
	 */
	boolean removeHost() default true;
}
