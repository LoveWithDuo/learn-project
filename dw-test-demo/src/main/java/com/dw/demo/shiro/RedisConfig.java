//package com.dw.demo.shiro;
//
//import org.crazycake.shiro.RedisManager;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cache.annotation.CachingConfigurerSupport;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import redis.clients.jedis.JedisPool;
//import redis.clients.jedis.JedisPoolConfig;
//
///**
// *
// * @Author: zhanzhihong
// * @Date: 2021/3/5 13:30
// * @version v1.0
// */
//@Configuration
//@EnableCaching//开启换成配置
//public class RedisConfig extends CachingConfigurerSupport {
//    private static org.slf4j.Logger Logger = LoggerFactory.getLogger(RedisConfig.class);
//    @Value("${spring.redis.host}")
//    private String host;
//    @Value("${spring.redis.port}")
//    private int port;
//    @Value("${spring.redis.timeout}")
//    private int timeout;
////    @Value("${spring.redis.pool.max-idle}")
////    private int maxIdle;
////    @Value("${spring.redis.pool.max-wait}")
////    private long maxWaitMillis;
//    @Bean
//    public JedisPool redisPoolFactory() {
//        Logger.info("开启redis，redis地址：" + host + ":" + port + ",JedisPool注入成功！！");
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
////        jedisPoolConfig.setMaxIdle(maxIdle);
////        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
//        JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout);
//        return jedisPool;
//    }
//    /**
//     * 配置shiro redisManager 使用的是shiro-redis开源插件
//     *
//     * @return
//     */
//    @Bean
//    public RedisManager redisManager() {
//        RedisManager redisManager = new RedisManager();
//        redisManager.setHost(host);
//        redisManager.setPort(port);
////        redisManager.setExpire(1800);// 配置缓存过期时间
//        redisManager.setTimeout(timeout);
//        return redisManager;
//    }
//}