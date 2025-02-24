package com.sandbox.authify.infra.util;

import com.github.ksuid.Ksuid;
import com.sandbox.authify.core.port.util.IdGenerator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class KsuidGenerator implements IdGenerator {

    @Override
    public String generate() {
        return Ksuid.newKsuid().toString();
    }
}
