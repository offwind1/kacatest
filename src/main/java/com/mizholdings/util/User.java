package com.mizholdings.util;

import lombok.Data;

@Data
public abstract class User {
    protected String id = "0";
    protected String token;
}
