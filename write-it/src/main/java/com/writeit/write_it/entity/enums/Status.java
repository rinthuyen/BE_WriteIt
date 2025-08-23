package com.writeit.write_it.entity.enums;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;

public enum Status {
    ACTIVE, DEACTIVATED,
    @JsonEnumDefaultValue UNKNOWN
}
