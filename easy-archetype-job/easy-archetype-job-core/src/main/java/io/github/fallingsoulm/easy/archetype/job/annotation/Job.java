package io.github.fallingsoulm.easy.archetype.job.annotation;


import java.lang.annotation.*;

/**
 * 定时任务
 *
 * @author luyanan
 * @since 2021/6/25
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface Job {

	/**
	 * 任务分组
	 *
	 * @author luyan
	 * @since 2021/6/25
	 */
	String group() default "DEFAULT";


	/**
	 * 任务名称
	 *
	 * @author luyan
	 * @since 2021/6/25
	 */
	String name();


	/**
	 * cron表达式,当为空的时候, 则需要启动的时候设置
	 *
	 * @author luyan
	 * @since 2021/6/25
	 */
	String cron() default "";


	/**
	 * 是否允许并发.默认不允许
	 *
	 * @author luyan
	 * @since 2021/6/25
	 */
	boolean concurrent() default true;


	/**
	 * 是否自动注册,默认为true
	 *
	 * @author luyan
	 * @since 2021/6/25
	 */
	boolean autoRegister() default true;
}
