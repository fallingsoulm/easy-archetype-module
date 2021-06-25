package io.github.fallingsoulm.easy.archetype.data.redis;

/**
 * redis key生成器
 *
 * @author luyanan
 * @since 2021/5/14
 **/
public interface RedisKeyGenerator {


    /**
     * <p>key 生成</p>
     *
     * @param param 参数
     * @return {@link String}
     * @author luyanan
     * @since 2020/2/26
     */
    String generate(IRedisKeyEnums redisKeyEnums, Object... param);


}
