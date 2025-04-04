package com.example.demo.config;
 
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import java.time.Duration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
 
@EnableCaching  // 开启缓存
@Configuration  // 配置类
public class RedisConfig {

    @Value("${spring.data.redis.password}")
    private String password;

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;

    @Value("${spring.data.redis.database}")
    private int database;

    /**
     * 配置 Redis 连接工厂
     * 意义: LettuceConnectionFactory 是连接 Redis 服务器的入口，它使用了 Lettuce 客户端，并且配置了连接池来提高性能和资源管理
     * @return LettuceConnectionFactory
     */
    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        // 配置 Redis 服务器的连接信息
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setPassword(password);
        redisStandaloneConfiguration.setDatabase(database);
 
        // 配置连接池
        GenericObjectPoolConfig<Object> poolConfig = new GenericObjectPoolConfig<>();
        poolConfig.setMaxTotal(10);       // 连接池中的最大连接数
        poolConfig.setMaxIdle(5);         // 连接池中的最大空闲连接数
        poolConfig.setMinIdle(1);         // 连接池中的最小空闲连接数
        poolConfig.setMaxWait(Duration.ofSeconds(10));  // 连接池中获取连接的最大等待时间
 
        // 创建一个带有连接池配置的 Lettuce 客户端配置
        LettucePoolingClientConfiguration lettucePoolingClientConfiguration =
                LettucePoolingClientConfiguration.builder()
                        .poolConfig(poolConfig)
                        .build();
 
        // 返回带有连接池配置的 Redis 连接工厂
        return new LettuceConnectionFactory(redisStandaloneConfiguration, lettucePoolingClientConfiguration);
    }
 
    /**
     * 配置并返回一个 RedisTemplate 实例，用于执行 Redis 操作
     * 意义: RedisTemplate 提供了一种高级抽象，使得开发者可以通过模板方法操作 Redis，而无需直接处理底层的 Redis 命令。
     * 它支持多种 Redis 操作，例如值操作、哈希操作、列表操作等
     * @return RedisTemplate
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        /*
            1.创建 RedisTemplate: 这是 Spring 用于与 Redis 交互的核心类，简化了与 Redis 的交互。
            2.设置连接工厂: 使用前面定义的 LettuceConnectionFactory。
            3.设置序列化器: 设置键和值的序列化器，这里使用 StringRedisSerializer 来将键和值序列化为字符串。
         */
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());  // 设置连接工厂
        template.setKeySerializer(new StringRedisSerializer());  // 设置键的序列化器
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

    public RedisSerializer<Object> redisSerializer() {
        //创建JSON序列化器
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        //必须设置，否则无法序列化实体类对象
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        return new GenericJackson2JsonRedisSerializer(objectMapper);
    }
}