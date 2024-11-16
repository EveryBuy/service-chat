package ua.everybuy.buisnesslogic.service.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CacheService {
    private final RedisCacheManager redisCacheManager;

    public void clearAllCaches() {
        redisCacheManager.getCacheNames().forEach(cacheName -> {
            redisCacheManager.getCache(cacheName).clear();
        });
    }
}
