package io.github.fallingsoulm.easy.archetype.framework.extension.annotation;

import java.lang.annotation.*;

/**
 * 自适应扩展点
 *
 * @author luyanan
 * @since 2021/3/5
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface Adaptive {
}
