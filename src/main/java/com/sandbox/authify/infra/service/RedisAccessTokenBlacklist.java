package com.sandbox.authify.infra.service;

import com.sandbox.authify.core.application.service.KeyManager;
import com.sandbox.authify.core.port.security.JwtService;
import com.sandbox.authify.core.port.service.AccessTokenBlacklist;
import com.sandbox.authify.infra.common.AuthifyCache;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisAccessTokenBlacklist implements AccessTokenBlacklist {

    private final RedisTemplate<String, String> redisTemplate;
    private final JwtService jwtService;
    private final KeyManager keyManager;

    @Override
    public void add(String token) {
        var claims = jwtService.getClaims(token, keyManager.getRsaPublicKey());
        var expiresIn = claims.getExp() - System.currentTimeMillis();

        if (expiresIn < 0) {
            return;
        }

        var redisKey = String.format("%s:%s", AuthifyCache.ACCESS_TOKEN_BLACKLIST, claims.getJti());
        redisTemplate.opsForValue().set(redisKey, claims.getJti());
        redisTemplate.expire(redisKey, Duration.ofMillis(expiresIn));
    }

    @Override
    public Boolean contains(String token) {
        var claims = jwtService.getClaims(token, keyManager.getRsaPublicKey());
        var redisKey = String.format("%s:%s", AuthifyCache.ACCESS_TOKEN_BLACKLIST, claims.getJti());
        return redisTemplate.hasKey(redisKey);
    }
}
