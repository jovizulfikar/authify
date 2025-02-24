package com.sandbox.authify.infra.security;

import com.sandbox.authify.core.port.security.Hashing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BcryptHash implements Hashing {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public String hash(String src) {
        return encoder.encode(src);
    }

    @Override
    public boolean verify(String src, String hash) {
        return encoder.matches(src, hash);
    }
}
