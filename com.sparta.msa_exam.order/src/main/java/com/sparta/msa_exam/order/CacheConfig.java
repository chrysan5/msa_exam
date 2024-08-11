package com.sparta.msa_exam.order;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;

import static org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

//스프링에서 어노테이션으로 선언형 캐싱 기능 구현을 하는 것이다

@Configuration
@EnableCaching //캐싱 어노테이션
public class CacheConfig {

    //RedisCacheManager 구현체를 사용하여 캐싱 기능을 사용할 수 있음
    @Bean
    // CacheManager로 진행해도 정상 동작
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        // 설정 구성을 먼저 진행한다.
        // Redis를 이용해서 Spring Cache를 사용할 때 Redis 관련 설정을 모아두는 클래스
        RedisCacheConfiguration configuration = RedisCacheConfiguration
                .defaultCacheConfig() //스프링부트 기본 설정 사용
                // null을 캐싱 할것인지 - diable이므로 결과가 null이면 캐싱하지 않겠다는 의미임
                .disableCachingNullValues()
                // 기본 캐시 유지 시간 (Time To Live)
                .entryTtl(Duration.ofSeconds(3600)) //1시간
                // 캐시를 구분하는 접두사 설정
                .computePrefixWith(CacheKeyPrefix.simple())
                // 캐시에 저장할 값을 어떻게 직렬화 / 역직렬화 할것인지
                .serializeValuesWith(
                        SerializationPair.fromSerializer(RedisSerializer.java())
                );

        //위의 설정을 사용하는 캐시메니저를 리턴
        return RedisCacheManager
                .builder(redisConnectionFactory)
                .cacheDefaults(configuration)
                .build();
    }
}