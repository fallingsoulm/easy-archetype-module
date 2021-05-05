package io.github.fallingsoulm.easy.archetype.framework.xss.annotation;

import java.lang.annotation.*;

/**
 * 忽略Xss
 *
 * @author luyanan
 * @since 2021/2/8
 **/
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface XssCleanIgnore {

}