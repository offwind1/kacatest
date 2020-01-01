package com.mizholdings.util.requests;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class RequestData {
    private Object body;
    private Map<String, String> headers = new HashMap<>();

    public void setHeader(String key, String value) {
        headers.put(key, value);
    }
}
