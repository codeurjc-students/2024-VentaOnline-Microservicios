package es.webapp.webapp.service;

/*import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class TokenBlacklistService {

    private static final String TOKEN_BLACKLIST_PREFIX = "blacklisted:";

    @Autowired
    private StringRedisTemplate redisTemplate;

    public void blacklistToken(String token, long expirationMillis) {
        String key = TOKEN_BLACKLIST_PREFIX + token;
        redisTemplate.opsForValue().set(key, "true", Duration.ofMillis(expirationMillis));
    }

    public boolean isBlacklisted(String token) {
        String key = TOKEN_BLACKLIST_PREFIX + token;
        return Boolean.TRUE.toString().equals(redisTemplate.opsForValue().get(key));
    }
}
*/