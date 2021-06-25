package io.github.fallingsoulm.easy.archetype.data.redis;

import cn.hutool.core.lang.Assert;
import org.springframework.beans.factory.annotation.Value;

import java.text.MessageFormat;

/**
 * redis  key生成的简单实现
 *
 * @author luyanan
 * @since 2021/5/14
 **/
public class SimpleRedisKeyGenerate implements RedisKeyGenerator {

    @Value("${spring.application.name}")
    private String applicationName;

    @Override
    public String generate(IRedisKeyEnums redisKeyEnums, Object... param) {
        Assert.notNull(redisKeyEnums, "redisKeyEnums 不能为空");
        Assert.notBlank(redisKeyEnums.getKey(), "redis的key不能为空");
        return applicationName + ":" + MessageFormat.format(redisKeyEnums.getKey(), param);
    }
}
