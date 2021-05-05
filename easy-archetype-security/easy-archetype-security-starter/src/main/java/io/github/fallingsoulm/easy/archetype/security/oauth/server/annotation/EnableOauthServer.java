package io.github.fallingsoulm.easy.archetype.security.oauth.server.annotation;

import io.github.fallingsoulm.easy.archetype.security.security.annotation.EnableSecurity;
import org.springframework.context.annotation.Import;
import io.github.fallingsoulm.easy.archetype.security.oauth.server.OauthServerAutoConfiguration;

import java.lang.annotation.*;

/**
 * oauth2.0的服务端注解
 *
 * @author luyanan
 * @since 2021/2/18
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import(OauthServerAutoConfiguration.class)
public @interface EnableOauthServer {
}
