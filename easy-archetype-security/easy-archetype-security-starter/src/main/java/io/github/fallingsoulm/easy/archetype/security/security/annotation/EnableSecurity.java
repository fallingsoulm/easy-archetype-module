package io.github.fallingsoulm.easy.archetype.security.security.annotation;

import io.github.fallingsoulm.easy.archetype.security.security.WebSecurityAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启安全校验
 *
 * @author luyanan
 * @since 2021/2/18
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import(WebSecurityAutoConfiguration.class)
public @interface EnableSecurity {
}
