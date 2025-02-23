package com.sandbox.authify.core.domain.jose;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JsonWebKey {
    private String kid;
    private String kty;
    private String use;
    private String e;
    private String n;
    private String alg;
}
