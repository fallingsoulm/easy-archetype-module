package io.github.fallingsoulm.easy.archetype.security.oauth.client.annotation;

import io.github.fallingsoulm.easy.archetype.security.oauth.client.OauthClientAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Oauth2.0的客户端注解
 *
 * @author luyanan
 * @since 2021/2/18
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import(OauthClientAutoConfiguration.class)
public @interface EnableOauthClient {
}
