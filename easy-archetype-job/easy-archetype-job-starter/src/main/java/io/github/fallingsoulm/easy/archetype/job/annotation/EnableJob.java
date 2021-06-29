package io.github.fallingsoulm.easy.archetype.job.annotation;


import io.github.fallingsoulm.easy.archetype.job.JobRegistrar;
import io.github.fallingsoulm.easy.archetype.job.config.ScheduleAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启定时任务
 *
 * @author luyanan
 * @since 2021/6/25
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({ScheduleAutoConfiguration.class, JobRegistrar.class})
public @interface EnableJob {
	/**
	 * Alias for the {@link #basePackages()} attribute. Allows for more concise annotation
	 *
	 * @author luyan
	 * @since 2021/6/25
	 */
	String[] value() default {};

	String[] basePackages() default {};

	Class<?>[] basePackageClasses() default {};
}
