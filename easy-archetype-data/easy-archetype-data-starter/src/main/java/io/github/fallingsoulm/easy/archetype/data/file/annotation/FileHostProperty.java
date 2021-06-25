package io.github.fallingsoulm.easy.archetype.data.file.annotation;

import java.lang.annotation.*;

/**
 * 添加host的注解
 *
 * @author luyanan
 * @since 2021/6/5
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
public @interface FileHostProperty {

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
