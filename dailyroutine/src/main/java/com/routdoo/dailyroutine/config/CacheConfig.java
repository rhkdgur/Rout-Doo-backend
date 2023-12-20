package com.routdoo.dailyroutine.config;

import org.apache.tomcat.util.collections.ConcurrentCache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * packageName    : com.routdoo.dailyroutine.config
 * fileName       : CachConfig
 * author         : GAMJA
 * date           : 2023/12/19
 * description    : 캐싱 설정
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/12/19        GAMJA       최초 생성
 */
@EnableCaching
@Configuration
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager();
        cacheManager.setAllowNullValues(false);
        cacheManager.setCacheNames(List.of("public_code","daily_routine"));
        return cacheManager;
    }

}
