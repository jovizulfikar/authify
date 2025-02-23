package com.sandbox.authify.core.domain.jose;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class JsonWebKeySet {
    private List<JsonWebKey> keys;
}
