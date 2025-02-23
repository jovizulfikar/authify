package com.sandbox.authify.core.port.service;

public interface AccessTokenBlacklist {
    void add(String token);
    Boolean contains(String token);
}
