package com.sandbox.authify.core.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(staticName = "of")
@Getter
public class Page {
    private Integer number;
    private Integer size;
}
