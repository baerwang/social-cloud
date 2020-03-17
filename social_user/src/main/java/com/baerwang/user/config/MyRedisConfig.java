package com.baerwang.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * redis 配置类
 *
 * @author baerwang
 * @version 1.0
 * @date 2020/2/21 12:09
 */
@Configuration
public class MyRedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        /*创建RedisTemplate对象*/
        RedisTemplate<String, Object> template = new RedisTemplate<>();

        /*设置开启事务支持*/
        template.setEnableTransactionSupport(true);

        /*设置RedisConnection工厂。它就是实现多种Java Redis客户端接入的秘密工厂*/
        template.setConnectionFactory(factory);
        /*使用String序列化方式，序列化key*/
        template.setKeySerializer(RedisSerializer.string());
        /*使用JSON序列化(库是Jackson)，序列化VALUE*/
        template.setValueSerializer(RedisSerializer.json());
        return template;
    }
}
